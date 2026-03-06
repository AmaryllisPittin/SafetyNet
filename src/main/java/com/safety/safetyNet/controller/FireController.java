package com.safety.safetynet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safety.safetynet.dto.FireDTO;
import com.safety.safetynet.service.FireStationService;

@RestController
public class FireController {

    private final FireStationService fireStationService;

    public FireController(FireStationService fireStationsService) {
        this.fireStationService = fireStationsService;
    }

    // URL: http://localhost:8080/fire?address=<address>
    @GetMapping("/fire")
    public List<FireDTO> getPersonsByAddress(@RequestParam String address) throws Exception {
        return fireStationService.getPersonsByAddress(address);
    }

}
