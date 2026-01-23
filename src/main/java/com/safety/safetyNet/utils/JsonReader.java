package com.safety.safetynet.utils;
import com.safety.safetynet.model.Person;
import com.safety.safetynet.model.MedicalRecords;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JsonReader {

    private static final String FILE_PATH = "/data.json";

    public static List<Person> readPersons() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        InputStream is = JsonReader.class.getResourceAsStream(FILE_PATH);
        Map<String, Object> data = mapper.readValue(is, new TypeReference<Map<String, Object>>() {});

        List<Person> persons = mapper.convertValue(
            data.get("persons"),
            mapper.getTypeFactory().constructCollectionType(List.class, Person.class));
        return persons;

    }

        public static List<MedicalRecords> readMedicalRecords() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        InputStream is = JsonReader.class.getResourceAsStream(FILE_PATH);
        Map<String, Object> data = mapper.readValue(is, new TypeReference<Map<String, Object>>() {});

        List<MedicalRecords> medicalRecords = mapper.convertValue(
            data.get("medicalrecords"),
            mapper.getTypeFactory().constructCollectionType(List.class, MedicalRecords.class));
        return medicalRecords;

    }
    
}
