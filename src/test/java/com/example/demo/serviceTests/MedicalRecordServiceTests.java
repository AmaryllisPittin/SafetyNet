package com.example.demo.serviceTests;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.service.MedicalRecordService;

public class MedicalRecordServiceTests {

    @Test
    void shouldReturnAllMedicalRecords() throws Exception {

        MedicalRecordService service = new MedicalRecordService();

        List<MedicalRecord> medicalRecord = service.getAllMedicalRecords();

        assertNotNull(medicalRecord);
        assertFalse(medicalRecord.isEmpty());

    }

    @Test
    void shouldLoadPersonsFromJsonFile() throws Exception {

        MedicalRecordService service = new MedicalRecordService();

        List<MedicalRecord> persons = service.getAllMedicalRecords();

        assertNotNull(persons, "La liste ne doit pas être null");
        assertFalse(persons.isEmpty(), "La liste ne doit pas être vide");

    }

}