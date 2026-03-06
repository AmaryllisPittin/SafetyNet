package com.safety.safetynet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safety.safetynet.dto.ChildAlertDTO;
import com.safety.safetynet.service.FireStationService;

@RestController
public class ChildAlertController {

    private final FireStationService fireStationService;

    public ChildAlertController(FireStationService fireStationsService) {
        this.fireStationService = fireStationsService;
    }

    // URL: http://localhost:8080/childAlert?address=<address>
    @GetMapping("/childAlert")
    public List<ChildAlertDTO> getChildrenByAddress(@RequestParam String address) throws Exception {
        return fireStationService.getChildrenByAddress(address);
    }

}
