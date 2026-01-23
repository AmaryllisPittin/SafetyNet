package com.safety.safetynet.service;

import java.util.List;

import com.safety.safetynet.model.Person;
import com.safety.safetynet.utils.JsonReader;

public class PersonService {
    
    public List<Person> getAllPersons() throws Exception {
        return JsonReader.readPersons();
    }

}
