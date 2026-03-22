package com.safety.safetynet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safety.safetynet.model.Person;
import com.safety.safetynet.service.PersonService;

@RestController
public class PersonInfolastNameController {

    private final PersonService personService;

    public PersonInfolastNameController(PersonService personService) {
        this.personService = personService;
    }

    // http://localhost:8080/personInfolastName=%3ClastName
    @GetMapping("/personInfolastName")
    public ResponseEntity<List<Person>> getPersonByLastName(@RequestParam("lastName") String lastName)
            throws Exception {
        try {
            List<Person> persons = personService.getPersonByLastName(lastName);
            if (persons.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(persons);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}