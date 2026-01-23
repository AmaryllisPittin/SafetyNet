package com.safety.safetynet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import com.safety.safetynet.model.FireStations;
import com.safety.safetynet.service.FireStationsService;

public class FireStationsController {
    
    private final FireStationsService fireStationsService;

    public FireStationsController(FireStationsService fireStationsService) {
        this.fireStationsService = fireStationsService;
    }

    @GetMapping("/firestations")
    public List<FireStations> getAllMedicalRecords() throws Exception {
        return fireStationsService.getAllFireStations();
    }

}
