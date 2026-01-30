package com.example.demo.modelTests;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.model.MedicalRecords;

public class MedicalRecordsTests {
    @Test
    void TestGettersAndSetters() {

        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setFirstName("John");
        medicalRecords.setLastName("Boyd");
        medicalRecords.setBirthdate("03/06/1984");
        medicalRecords.setMedications(List.of("aznol:350mg", "hydrapermazol:100mg"));
        medicalRecords.setAllergies(List.of("nillacilan"));

        assertEquals("John", medicalRecords.getFirstName());
        assertEquals("Boyd", medicalRecords.getLastName());
        assertEquals("03/06/1984", medicalRecords.getBirthdate());
        assertEquals(2, medicalRecords.getMedications().size());
        assertEquals(1, medicalRecords.getAllergies().size());

    }
}

