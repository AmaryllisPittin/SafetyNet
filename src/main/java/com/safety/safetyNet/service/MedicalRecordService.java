package com.safety.safetynet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.utils.JsonReader;

@Service
public class MedicalRecordService {

    public List<MedicalRecord> getAllMedicalRecords() throws Exception {
        return JsonReader.readMedicalRecord();
    }

    // Récupération des données médicales d'une personne par son prénom
    public Optional<MedicalRecord> getMedicalRecordByFirstName(String firstName) throws Exception {
        return getAllMedicalRecords().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName))
                .findFirst();
    }

    // Ajouter pour une personne
    public void addMedicalRecord(MedicalRecord medicalRecord) throws Exception {
        List<MedicalRecord> medicalRecords = getAllMedicalRecords();
        medicalRecords.add(medicalRecord);
        JsonReader.writeMedicalRecord(medicalRecords);
    }

    // Modifier pour une personne
    public boolean updateMedicalRecord(String firstName, MedicalRecord updatedMedicalRecord) throws Exception {
        List<MedicalRecord> medicalRecords = getAllMedicalRecords();
        for (int i = 0; i < medicalRecords.size(); i++) {
            if (medicalRecords.get(i).getFirstName().equalsIgnoreCase(firstName)) {
                medicalRecords.set(i, updatedMedicalRecord);
                JsonReader.writeMedicalRecord(medicalRecords);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMedicalRecord(String firstName) throws Exception {
        List<MedicalRecord> medicalRecords = getAllMedicalRecords();
        boolean removed = medicalRecords.removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName));
        if (removed) {
            JsonReader.writeMedicalRecord(medicalRecords);
        }
        return removed;
    }
}
