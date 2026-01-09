package com.safety.safetyNet.utils;
import com.safety.safetyNet.model.Person;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

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
    
}
