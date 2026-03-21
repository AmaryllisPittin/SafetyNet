package com.example.demo.serviceTests;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safety.safetynet.model.Person;
import com.safety.safetynet.service.MedicalRecordService;
import com.safety.safetynet.service.PersonService;
import com.safety.safetynet.utils.JsonReader;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTests {

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private PersonService personService;

    @Test
    void shouldReturnAllPersons() throws Exception {

        PersonService service = new PersonService(medicalRecordService);

        List<Person> persons = service.getAllPersons();

        assertNotNull(persons);
        assertFalse(persons.isEmpty());

    }

    @Test
    void shouldLoadPersonsFromJsonFile() throws Exception {

        PersonService service = new PersonService(medicalRecordService);

        List<Person> persons = service.getAllPersons();

        assertNotNull(persons, "La liste ne doit pas être null");
        assertFalse(persons.isEmpty(), "La liste ne doit pas être vide");
    }

    @Test
    void shouldReturnPersonByFullName() throws Exception {
        List<Person> mockList = List.of(
                new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512",
                        "tenz@email.com"),
                new Person("Jamie", "Peters", "908 73rd St", "Culver", "97451", "841-874-7462",
                        "jpeter@email.com"));

        PersonService service = new PersonService(null) {
            @Override
            public List<Person> getAllPersons() {
                return mockList;
            }
        };

        List<Person> result = service.getPersonByName("Tessa", "Carman");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Tessa", result.get(0).getFirstName());
        assertEquals("Carman", result.get(0).getLastName());
    }

    @Test
    void shouldReturnPersonByLastName() throws Exception {
        Person tessa = new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512",
                "tenz@email.com");
        Person jamie = new Person("Jamie", "Peters", "908 73rd St", "Culver", "97451", "841-874-7462",
                "jpeter@email.com");

        PersonService spyService = Mockito.spy(personService);
        Mockito.doReturn(List.of(tessa, jamie)).when(spyService).getAllPersons();

        List<Person> result = spyService.getPersonByLastName("Carman");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(p -> p.getLastName().equalsIgnoreCase("Carman")));
    }

    @Test
    void shouldAddPerson() throws Exception {
        Person tessa = new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512",
                "tenz@email.com");

        PersonService spyService = Mockito.spy(personService);
        Mockito.doReturn(new ArrayList<>()).when(spyService).getAllPersons();

        try (MockedStatic<JsonReader> mocked = Mockito.mockStatic(JsonReader.class)) {
            mocked.when(() -> JsonReader.writePersons(Mockito.anyList())).thenAnswer(invocation -> null);

            spyService.addPerson(tessa);

            mocked.verify(() -> JsonReader.writePersons(Mockito.anyList()));
        }
    }

    @Test
    void shouldUpdatePerson() throws Exception {
        Person tessa = new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512",
                "tenz@email.com");
        Person updatedTessa = new Person("Tessa", "Carman", "40 Rue des Peupliers", "Paris", "97451", "841-874-6512",
                "tenz@email.com");

        PersonService spyService = Mockito.spy(personService);
        Mockito.doReturn(new ArrayList<>(List.of(tessa))).when(spyService).getAllPersons();

        try (MockedStatic<JsonReader> mocked = Mockito.mockStatic(JsonReader.class)) {
            mocked.when(() -> JsonReader.writePersons(Mockito.anyList())).thenAnswer(invocation -> null);

            boolean result = spyService.updatePerson("Tessa", "Carman", updatedTessa);

            assertTrue(result);
            mocked.verify(() -> JsonReader.writePersons(Mockito.anyList()));
        }
    }

    @Test
    void shouldDeletePersonAndCascadeMedicalRecord() throws Exception {
        Person tessa = new Person("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451", "841-874-6512",
                "tenz@email.com");

        PersonService spyService = Mockito.spy(personService);
        Mockito.doReturn(new ArrayList<>(List.of(tessa))).when(spyService).getAllPersons();

        try (MockedStatic<JsonReader> mocked = Mockito.mockStatic(JsonReader.class)) {
            mocked.when(() -> JsonReader.writePersons(Mockito.anyList())).thenAnswer(invocation -> null);

            Mockito.when(medicalRecordService.deleteMedicalRecord("Tessa", "Carman")).thenReturn(true);

            boolean deleted = spyService.deletePerson("Tessa", "Carman");

            assertTrue(deleted);
            mocked.verify(() -> JsonReader.writePersons(Mockito.anyList()));
            Mockito.verify(medicalRecordService).deleteMedicalRecord("Tessa", "Carman");
        }

    }

}