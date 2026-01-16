package com.example.demo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.model.Person;
import com.safety.safetynet.service.PersonService;

public class PersonServiceTests {
    
    @Test
    void shouldReturnAllPersons() throws Exception {

        PersonService service = new PersonService();

        List<Person> persons = service.getAllPersons();

        assertNotNull(persons);
        assertFalse(persons.isEmpty());

    }

    @Test
    void shouldLoadPersonsFromJsonFile() throws Exception {

        PersonService service = new PersonService();

        List<Person> persons = service.getAllPersons();

        assertNotNull(persons, "La liste ne doit pas être null");
        assertFalse(persons.isEmpty(), "La liste ne doit pas être vide");

    }

}