package com.safety.safetynet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safety.safetynet.dto.PersonDTO;
import com.safety.safetynet.mapper.PersonMapper;
import com.safety.safetynet.model.FireStation;
import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.service.FireStationService;
import com.safety.safetynet.utils.JsonReader;

@RestController
@RequestMapping("/firestations")
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationsService) {
        this.fireStationService = fireStationsService;
    }

    @GetMapping("/medicalRecord")
    public List<FireStation> getAllMedicalRecords() throws Exception {
        return fireStationService.getAllFireStations();
    }

    @GetMapping
    public List<FireStation> getAllFireStations() throws Exception {
        return fireStationService.getAllFireStations();
    }

    // Méthode GET
    @GetMapping("/{address}")
    public ResponseEntity<FireStation> getFireStationByAddress(@PathVariable String address) {
        try {
            return fireStationService.getFireStationByAddress(address)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("?stationNumber=<station_number>")
    public Map<String, Object> getPersonByStation(@RequestParam String station) throws Exception {
        int stationNumber = Integer.parseInt(station);
        List<PersonDTO> persons = fireStationService.getPersonByStation(stationNumber);
        List<MedicalRecord> medicalRecords = JsonReader.readMedicalRecord();

        long minors = persons.stream()
                .filter(p -> {
                    MedicalRecord mr = medicalRecords.stream()
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

    @PostMapping
    public ResponseEntity<Void> addFireStation(@RequestBody FireStation fireStation) throws Exception {
        fireStationService.addFireStation(fireStation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{address}")
    public ResponseEntity<Void> updateFireStation(@PathVariable String address,
            @RequestBody FireStation updatedFireStation)
            throws Exception {
        boolean updated = fireStationService.updateFireStation(address, updatedFireStation);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{address}")
    public ResponseEntity<Void> deleteFireStation(@PathVariable String address) throws Exception {
        boolean deleted = fireStationService.deleteFireStation(address);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
