package com.safety.safetynet.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safety.safetynet.service.FireStationService;

@RestController
public class PhoneAlertController {

    private final FireStationService fireStationService;

    public PhoneAlertController(FireStationService fireStationsService) {
        this.fireStationService = fireStationsService;
    }

    // URL: http://localhost:8080/phoneAlert?firestation=%3Cfirestation_number
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneNumbersByStation(@RequestParam("firestation") String fireStation) {
        try {
            List<String> phones = fireStationService.getPhoneNumbersByStation(fireStation);

            return ResponseEntity.ok(phones);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

}