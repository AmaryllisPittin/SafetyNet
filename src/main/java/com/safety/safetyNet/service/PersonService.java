package com.safety.safetyNet.service;

import com.safety.safetyNet.model.Person;
import com.safety.safetyNet.utils.JsonReader;

import java.util.List;

public class PersonService {
    
    public List<Person> getAllPersons() throws Exception {
        return JsonReader.readPersons();
    }

}
