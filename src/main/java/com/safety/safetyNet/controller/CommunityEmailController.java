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
public class CommunityEmailController {

    private final PersonService personService;

    public CommunityEmailController(PersonService personService) {
        this.personService = personService;
    }

    // http://localhost:8080/communityEmail?city=%3Ccity
    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmailByCity(@RequestParam("city") String city)
            throws Exception {
        try {
            List<String> emails = personService.getAllPersons().stream()
                    .filter(p -> p.getCity().equalsIgnoreCase(city))
                    .map(Person::getEmail)
                    .toList();

            if (emails.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(emails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}