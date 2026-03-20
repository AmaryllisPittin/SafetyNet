package com.safety.safetynet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safety.safetynet.model.Person;
import com.safety.safetynet.service.PersonService;

@RestController
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
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

    @RequestMapping("/persons")
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() throws Exception {
        try {
            List<Person> persons = personService.getAllPersons();
            return ResponseEntity.ok(persons);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Méthode GET
    @GetMapping("/{firstName}/{lastName}")
    public ResponseEntity<List<Person>> getPersonByName(@PathVariable String firstName, @PathVariable String lastName) {
        try {
            List<Person> persons = personService.getPersonByName(firstName, lastName);
            if (persons.isEmpty())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok(persons);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> addPerson(@RequestBody Person person) throws Exception {
        personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> updatePerson(@PathVariable String firstName, @PathVariable String lastName,
            @RequestBody Person updatedPerson)
            throws Exception {
        boolean updated = personService.updatePerson(firstName, lastName, updatedPerson);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable String lastName)
            throws Exception {
        boolean deleted = personService.deletePerson(firstName, lastName);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
