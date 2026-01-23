package com.safety.safetynet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import com.safety.safetynet.model.MedicalRecords;
import com.safety.safetynet.service.MedicalRecordService;

public class MedicalRecordController {
    
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/medicalrecords")
    public List<MedicalRecords> getAllMedicalRecords() throws Exception {
        return medicalRecordService.getAllMedicalRecords();
    }

}
