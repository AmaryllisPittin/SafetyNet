package com.safety.safetynet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.service.MedicalRecordService;

@RestController
@RequestMapping("/medicalrecords")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() throws Exception {
        try {
            List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
            return ResponseEntity.ok(medicalRecords);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Méthode GET
    @GetMapping("/{firstName}")
    public ResponseEntity<MedicalRecord> getMedicalRecordByFirstName(@PathVariable String firstName) {
        try {
            return medicalRecordService.getMedicalRecordByFirstName(firstName)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws Exception {
        medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{firstName}")
    public ResponseEntity<Void> updateMedicalRecord(@PathVariable String firstName,
            @RequestBody MedicalRecord updatedMedicalRecord) throws Exception {
        boolean updated = medicalRecordService.updateMedicalRecord(firstName, updatedMedicalRecord);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{firstName}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String firstName) throws Exception {
        boolean deleted = medicalRecordService.deleteMedicalRecord(firstName);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
