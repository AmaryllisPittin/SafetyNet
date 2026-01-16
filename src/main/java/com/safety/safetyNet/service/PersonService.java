package com.safety.safetynet.service;

import com.safety.safetynet.model.Person;
import com.safety.safetynet.utils.JsonReader;

import java.util.List;

public class PersonService {
    
    public List<Person> getAllPersons() throws Exception {
        return JsonReader.readPersons();
    }

}
