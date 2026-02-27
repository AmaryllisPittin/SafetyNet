package com.example.demo.modelTests;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.model.MedicalRecord;

public class MedicalRecordsTests {
    @Test
    void TestGettersAndSetters() {

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Boyd");
        medicalRecord.setBirthdate("03/06/1984");
        medicalRecord.setMedications(List.of("aznol:350mg", "hydrapermazol:100mg"));
        medicalRecord.setAllergies(List.of("nillacilan"));

        assertEquals("John", medicalRecord.getFirstName());
        assertEquals("Boyd", medicalRecord.getLastName());
        assertEquals("03/06/1984", medicalRecord.getBirthdate());
        assertEquals(2, medicalRecord.getMedications().size());
        assertEquals(1, medicalRecord.getAllergies().size());

    }
}
