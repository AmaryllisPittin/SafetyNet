package com.example.demo;

import org.junit.jupiter.api.Test;
import com.safety.safetyNet.service.PersonService;
import java.util.List;
import com.safety.safetyNet.model.Person;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

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