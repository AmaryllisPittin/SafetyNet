package com.safety.safetynet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.safety.safetynet.dto.PersonDTO;
import com.safety.safetynet.mapper.PersonMapper;
import com.safety.safetynet.model.FireStations;
import com.safety.safetynet.model.MedicalRecords;
import com.safety.safetynet.service.FireStationsService;
import com.safety.safetynet.utils.JsonReader;


public class FireStationsController {
    
    private final FireStationsService fireStationsService;

    public FireStationsController(FireStationsService fireStationsService) {
        this.fireStationsService = fireStationsService;
    }

    @GetMapping("/firestations")
    public List<FireStations> getAllMedicalRecords() throws Exception {
        return fireStationsService.getAllFireStations();
    }
    @GetMapping("/firestation?stationNumber=<station_number>")
    public Map<String, Object> getPersonByStation(@RequestParam String station) throws Exception {
        int stationNumber = Integer.parseInt(station);
        List<PersonDTO> persons =  fireStationsService.getPersonByStation(stationNumber);
        List<MedicalRecords> medicalRecords = JsonReader.readMedicalRecords();

        long minors = persons.stream()
        .filter(p -> {
            MedicalRecords mr = medicalRecords.stream()
            .filter(m -> m.getFirstName().equals(p.getFirstName())
                    && m.getLastName().equals(p.getLastName()))
            .findFirst()
            .orElse(null);
            return mr != null && PersonMapper.isMinor(mr);
        })
        .count();

        long adults = persons.size() - minors;

        Map<String, Object> response = new HashMap<>();
        response.put("persons", persons);
        response.put("minors", minors);
        response.put("adults", adults);

        return response;
    }
}
