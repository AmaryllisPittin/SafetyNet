package com.example.demo.modelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.model.Person;

public class PersonTests {
    @Test
    void TestGettersAndSetters() {

        Person person = new Person(
            "John",
            "Boyd",
            "1509 Culver St",
            "Culver",
            "97451",
            "841-874-6512",
            "jaboyd@email.com"
        );

        assertEquals("John", person.getFirstName());
        assertEquals("Boyd", person.getLastName());
        assertEquals("1509 Culver St", person.getAddress());
        assertEquals("Culver", person.getCity());
        assertEquals("97451", person.getZip());
        assertEquals("841-874-6512", person.getPhone());
        assertEquals("jaboyd@email.com", person.getEmail());
    }
}
