package com.example.demo.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safety.safetynet.dto.PersonInfolastNameDTO;
import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.model.Person;
import com.safety.safetynet.service.PersonInfolastNameService;
import com.safety.safetynet.utils.JsonReader;

@ExtendWith(MockitoExtension.class)
public class PersonInfolastNameServiceTests {

    @InjectMocks
    private PersonInfolastNameService personInfolastNameService;

    @Test
    void shouldReturnPersonsWithMedicalInfo() throws Exception {

        Person p = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
                "jaboyd@email.com");

        MedicalRecord johnMR = new MedicalRecord();
        johnMR.setFirstName("John");
        johnMR.setLastName("Boyd");
        johnMR.setBirthdate("03/06/1984");
        johnMR.setMedications(List.of("aznol:350mg", "hydrapermazol:100mg"));
        johnMR.setAllergies(List.of("nillacilan"));

        try (MockedStatic<JsonReader> mocked = Mockito.mockStatic(JsonReader.class)) {

            mocked.when(JsonReader::readPersons).thenReturn(List.of(p));
            mocked.when(JsonReader::readMedicalRecord).thenReturn(List.of(johnMR));

            List<PersonInfolastNameDTO> result = personInfolastNameService.getPersonByLastName("Boyd");

            assertEquals(1, result.size());
            assertEquals("John", result.get(0).getFirstName());
            assertEquals("Boyd", result.get(0).getLastName());
            assertEquals(List.of("aznol:350mg", "hydrapermazol:100mg"), result.get(0).getMedications());
        }

    }
}
