package com.safety.safetynet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safety.safetynet.dto.PersonInfolastNameDTO;
import com.safety.safetynet.service.PersonInfolastNameService;

@RestController
public class PersonInfolastNameController {

    private final PersonInfolastNameService personInfolastNameService;

    public PersonInfolastNameController(PersonInfolastNameService personInfolastNameService) {
        this.personInfolastNameService = personInfolastNameService;
    }

    // http://localhost:8080/personInfolastName=%3ClastName
    @GetMapping("/personInfolastName")
    public ResponseEntity<List<PersonInfolastNameDTO>> getPersonByLastName(@RequestParam("lastName") String lastName)
            throws Exception {
        try {
            List<PersonInfolastNameDTO> persons = personInfolastNameService.getPersonByLastName(lastName);
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