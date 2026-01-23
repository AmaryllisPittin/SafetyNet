package com.safety.safetynet.service;

import java.util.List;

import com.safety.safetynet.utils.JsonReader;
import com.safety.safetynet.model.MedicalRecords;

public class MedicalRecordService {
    public List<MedicalRecords> getAllMedicalRecords() throws Exception {
        return JsonReader.readMedicalRecords();
    }
}
