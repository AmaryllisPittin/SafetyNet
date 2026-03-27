package com.example.demo.serviceTests;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.safety.safetynet.dto.ChildAlertDTO;
import com.safety.safetynet.dto.FireDTO;
import com.safety.safetynet.dto.PersonDTO;
import com.safety.safetynet.model.FireStation;
import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.model.Person;
import com.safety.safetynet.service.FireStationService;
import com.safety.safetynet.utils.JsonReader;

public class FireStationsServiceTests {

    @Test
    void shouldReturnAllFireStations() throws Exception {

        FireStationService service = new FireStationService();

        List<FireStation> fireStations = service.getAllFireStations();

        assertNotNull(fireStations);
        assertFalse(fireStations.isEmpty());

    }

    @Test
    void shouldLoadFireStationsFromJsonFile() throws Exception {

        FireStationService service = new FireStationService();

        List<FireStation> fireStations = service.getAllFireStations();

        assertNotNull(fireStations, "La liste ne doit pas être null");
        assertFalse(fireStations.isEmpty(), "La liste ne doit pas être vide");

    }

    // Vérifie le filtrage par station
    @Test
    void shouldReturnPersonsCoveredByStations() throws Exception {

        FireStationService service = new FireStationService();

        List<PersonDTO> result = service.getPersonByStation(1);

        assertFalse(result.isEmpty());
        assertTrue(
                result.stream().allMatch(p -> p.getAddress() != null));

    }

    // Dans le cas où la station n'existe pas
    @Test
    void shouldReturnEmptyListWhenStationDoesNotExist() throws Exception {

        FireStationService service = new FireStationService();

        List<PersonDTO> result = service.getPersonByStation(997);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void personDTOShouldContainRequiredFields() throws Exception {

        FireStationService service = new FireStationService();

        List<PersonDTO> result = service.getPersonByStation(1);
        PersonDTO person = result.get(0);

        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getPhone());
    }

    @Test
    void shouldReturnFireStationWhenAddressExists() throws Exception {
        FireStationService service = new FireStationService();
        Optional<FireStation> station = service.getFireStationByAddress("1509 Culver St");
        assertTrue(station.isPresent());
        assertEquals("3", station.get().getStation());
    }

    @Test
    void shouldReturnEmptyWhenAddressDoesNotExist() throws Exception {
        FireStationService service = new FireStationService();
        Optional<FireStation> station = service.getFireStationByAddress("Unknown address");
        assertTrue(station.isEmpty());
    }

    @Test
    void shouldAddFireStation() throws Exception {

        FireStationService fireStationService = new FireStationService();
        FireStation fs = new FireStation("10 Rue du Lièvre", "4");

        FireStationService spyService = Mockito.spy(fireStationService);
        Mockito.doReturn(new ArrayList<>()).when(spyService).getAllFireStations();

        try (MockedStatic<JsonReader> mocked = Mockito.mockStatic(JsonReader.class)) {
            mocked.when(() -> JsonReader.writeFireStation(Mockito.anyList())).thenAnswer(invocation -> null);

            spyService.addFireStation(fs);

            mocked.verify(() -> JsonReader.writeFireStation(Mockito.anyList()));
        }
    }

    @Test
    void shouldUpdateFireStation() throws Exception {
        FireStation fs = new FireStation("1509 Culver St", "3");
        FireStation updatedFs = new FireStation("1509 Culver St", "2");

        FireStationService fireStationService = new FireStationService();
        FireStationService spyService = Mockito.spy(fireStationService);

        Mockito.doReturn(new ArrayList<>(List.of(fs))).when(spyService).getAllFireStations();

        try (MockedStatic<JsonReader> mocked = Mockito.mockStatic(JsonReader.class)) {
            mocked.when(() -> JsonReader.writeFireStation(Mockito.anyList())).thenAnswer(invocation -> null);

            boolean result = spyService.updateFireStation("1509 Culver St", updatedFs);

            assertTrue(result);
            mocked.verify(() -> JsonReader.writeFireStation(Mockito.anyList()));
        }
    }

    @Test
    void shouldDeleteFireStation() throws Exception {
        FireStation fs = new FireStation("1509 Culver St", "3");

        FireStationService fireStationService = new FireStationService();
        FireStationService spyService = Mockito.spy(fireStationService);

        Mockito.doReturn(new ArrayList<>(List.of(fs))).when(spyService).getAllFireStations();

        try (MockedStatic<JsonReader> mocked = Mockito.mockStatic(JsonReader.class)) {
            mocked.when(() -> JsonReader.writeFireStation(Mockito.anyList())).thenAnswer(invocation -> null);

            boolean deleted = spyService.deleteFireStation("1509 Culver St");

            assertTrue(deleted);
            mocked.verify(() -> JsonReader.writeFireStation(Mockito.anyList()));
        }

    }

    @Test
    void shouldReturnChildByAddress() throws Exception {

        FireStationService fireStationService = new FireStationService();

        Person child = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
                "tenz@email.com");
        Person adult = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
                "jaboyd@email.com");

        MedicalRecord childMR = new MedicalRecord("Tenley", "Boyd", "02/18/2012",
                List.of(), List.of("peanut"));
        MedicalRecord adultMR = new MedicalRecord("John", "Boyd", "03/06/1984", List.of(), List.of());

        try (MockedStatic<JsonReader> mocked = Mockito.mockStatic(JsonReader.class)) {
            mocked.when(JsonReader::readPersons).thenReturn(List.of(child, adult));
            mocked.when(JsonReader::readMedicalRecord).thenReturn(List.of(childMR, adultMR));

            List<ChildAlertDTO> result = fireStationService.getChildrenByAddress("1509 Culver St");

            assertEquals(1, result.size());
            ChildAlertDTO dto = result.get(0);
            assertEquals("Tenley", dto.getFirstName());
            assertEquals("Boyd", dto.getLastName());
            assertTrue(dto.getAge() < 18);

            assertTrue(result.stream()
                    .noneMatch(c -> c.getFirstName().equals("John")
                            && c.getLastName().equals("Boyd")));
        }
    }

    @Test
    void shouldReturnPhoneNumbersByStations() throws Exception {

        FireStationService fireStationService = new FireStationService();

        Person john = new Person();
        john.setFirstName("John");
        john.setLastName("Boyd");
        john.setAddress("1509 Culver St");
        john.setPhone("841-874-6512");

        Person eric = new Person();
        eric.setFirstName("Eric");
        eric.setLastName("Cadigan");
        eric.setAddress("951 LoneTree Rd");
        eric.setPhone("841-874-7458");

        // FireStations
        FireStation fs1 = new FireStation();
        fs1.setAddress("1509 Culver St");
        fs1.setStation("3");

        FireStation fs2 = new FireStation();
        fs2.setAddress("951 LoneTree Rd");
        fs2.setStation("2");

        try (MockedStatic<JsonReader> mocked = Mockito.mockStatic(JsonReader.class)) {

            mocked.when(JsonReader::readPersons).thenReturn(List.of(john, eric));
            mocked.when(JsonReader::readFireStations).thenReturn(List.of(fs1, fs2));

            List<String> phonesStation3 = fireStationService.getPhoneNumbersByStation("3");
            List<String> phonesStation2 = fireStationService.getPhoneNumbersByStation("2");

            assertEquals(1, phonesStation3.size());
            assertTrue(phonesStation3.contains("841-874-6512"));
            assertTrue(phonesStation2.contains("841-874-7458"));
        }
    }

    @Test
    void shouldReturnFireDTOByAddress() throws Exception {

        FireStationService fireStationService = new FireStationService();

        Person john = new Person();
        john.setFirstName("John");
        john.setLastName("Boyd");
        john.setAddress("1509 Culver St");
        john.setPhone("841-874-6512");

        Person jacob = new Person();
        jacob.setFirstName("Jacob");
        jacob.setLastName("Boyd");
        jacob.setAddress("1509 Culver St");
        jacob.setPhone("841-874-6513");

        FireStation fs1 = new FireStation();
        fs1.setAddress("1509 Culver St");
        fs1.setStation("3");

        // MedicalRecords
        MedicalRecord johnMR = new MedicalRecord();
        johnMR.setFirstName("John");
        johnMR.setLastName("Boyd");
        johnMR.setBirthdate("03/06/1984");
        johnMR.setMedications(List.of("aznol:350mg", "hydrapermazol:100mg"));
        johnMR.setAllergies(List.of("nillacilan"));

        MedicalRecord jacobMR = new MedicalRecord();
        jacobMR.setFirstName("Jacob");
        jacobMR.setLastName("Boyd");
        jacobMR.setBirthdate("03/06/1989");
        jacobMR.setMedications(List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"));
        jacobMR.setAllergies(List.of());

        try (MockedStatic<JsonReader> mocked = Mockito.mockStatic(JsonReader.class)) {

            mocked.when(JsonReader::readPersons).thenReturn(List.of(john, jacob));
            mocked.when(JsonReader::readFireStations).thenReturn(List.of(fs1));
            mocked.when(JsonReader::readMedicalRecord).thenReturn(List.of(johnMR, jacobMR));

            List<FireDTO> dtos = fireStationService.getPersonsByAddress("1509 Culver St");

            assertEquals(2, dtos.size());

            FireDTO dtoJohn = dtos.stream()
                    .filter(d -> d.getFirstName().equals("John"))
                    .findFirst()
                    .orElseThrow();

            assertEquals("Boyd", dtoJohn.getLastName());
            assertEquals("841-874-6512", dtoJohn.getPhone());
            assertEquals(42, dtoJohn.getAge());
            assertTrue(dtoJohn.getMedications().containsAll(List.of("aznol:350mg", "hydrapermazol:100mg")));
            assertTrue(dtoJohn.getAllergies().containsAll(List.of("nillacilan")));
            assertEquals("3", dtoJohn.getStation());

            FireDTO dtoJacob = dtos.stream()
                    .filter(d -> d.getFirstName().equals("Jacob"))
                    .findFirst()
                    .orElseThrow();

            assertEquals("Boyd", dtoJacob.getLastName());
            assertEquals("841-874-6513", dtoJacob.getPhone());
            assertEquals(37, dtoJacob.getAge());
            assertTrue(dtoJacob.getMedications()
                    .containsAll(List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg")));
            assertTrue(dtoJacob.getAllergies().containsAll(List.of()));
            assertEquals("3", dtoJacob.getStation());

        }

    }

}