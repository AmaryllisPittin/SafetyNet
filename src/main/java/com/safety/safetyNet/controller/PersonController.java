package com.safety.safetynet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safety.safetynet.model.Person;
import com.safety.safetynet.service.PersonService;

@RestController
public class PersonController {

    private final  PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons() throws Exception {
        try {
            List<Person> persons = personService.getAllPersons();
            return ResponseEntity.ok(persons);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    }
    

