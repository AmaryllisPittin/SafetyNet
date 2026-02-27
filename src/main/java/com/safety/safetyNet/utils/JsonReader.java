package com.safety.safetynet.utils;

import com.safety.safetynet.model.Person;
import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.model.FireStation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonReader {

    private static final String FILE_PATH = "/data.json";
    private static final String EXTERNAL_FILE_PATH = "target/data/data.json";

    public static void initDataFile() throws IOException {
        File file = new File(EXTERNAL_FILE_PATH);
        if (!file.exists()) {
            try (InputStream is = JsonReader.class.getResourceAsStream(FILE_PATH)) {
                Files.createDirectories(file.getParentFile().toPath());
                Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static List<Person> readPersons() throws IOException {
        initDataFile();
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(EXTERNAL_FILE_PATH);
        Map<String, Object> data = mapper.readValue(file, new TypeReference<Map<String, Object>>() {
        });

        List<Person> persons = mapper.convertValue(
                data.get("persons"),
                mapper.getTypeFactory().constructCollectionType(List.class, Person.class));
        return persons;
    }

    public static void writePersons(List<Person> persons) throws IOException {
        initDataFile();
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(EXTERNAL_FILE_PATH);
        Map<String, Object> data = mapper.readValue(file, new TypeReference<Map<String, Object>>() {
        });
        data.put("persons", persons);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
    }

    public static List<MedicalRecord> readMedicalRecord() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        InputStream is = JsonReader.class.getResourceAsStream(FILE_PATH);
        Map<String, Object> data = mapper.readValue(is, new TypeReference<Map<String, Object>>() {
        });

        List<MedicalRecord> medicalRecords = mapper.convertValue(
                data.get("medicalrecords"),
                mapper.getTypeFactory().constructCollectionType(List.class, MedicalRecord.class));
        return medicalRecords;

    }

    public static void writeMedicalRecord(List<MedicalRecord> medicalRecords) throws IOException {
        initDataFile();
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(EXTERNAL_FILE_PATH);
        Map<String, Object> data = mapper.readValue(file, new TypeReference<Map<String, Object>>() {
        });
        data.put("medicalrecords", medicalRecords);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
    }

    public static List<FireStation> readFireStations() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        InputStream is = JsonReader.class.getResourceAsStream(FILE_PATH);
        Map<String, Object> data = mapper.readValue(is, new TypeReference<Map<String, Object>>() {
        });

        List<FireStation> fireStations = mapper.convertValue(
                data.get("firestations"),
                mapper.getTypeFactory().constructCollectionType(List.class, FireStation.class));
        return fireStations;
    }

    public static void writeFireStation(List<FireStation> fireStations) throws IOException {
        initDataFile();
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(EXTERNAL_FILE_PATH);
        Map<String, Object> data = mapper.readValue(file, new TypeReference<Map<String, Object>>() {
        });
        data.put("firestations", fireStations);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
    }

}
