package com.safety.safetynet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safety.safetynet.dto.FloodStationDTO;
import com.safety.safetynet.service.FloodService;

// Pour URL: http://localhost:8080/flood/stations?stations=<a list of station_numbers>

@RestController
@RequestMapping("/flood")
public class FloodController {
    private final FloodService floodService;

    public FloodController(FloodService floodService) {
        this.floodService = floodService;
    }

    @GetMapping("/stations")
    public ResponseEntity<List<FloodStationDTO>> getFloodByStations(@RequestParam List<String> stations) {
        try {
            List<FloodStationDTO> result = floodService.getResidentsByStation(stations);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
