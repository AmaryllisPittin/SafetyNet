package com.example.demo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.model.FireStations;
import com.safety.safetynet.service.FireStationsService;

public class FireStationsServiceTests {
    
    @Test
    void shouldReturnAllFireStations() throws Exception {

        FireStationsService service = new FireStationsService();

        List<FireStations> fireStations = service.getAllFireStations();

        assertNotNull(fireStations);
        assertFalse(fireStations.isEmpty());

    }

    @Test
    void shouldLoadFireStationsFromJsonFile() throws Exception {

        FireStationsService service = new FireStationsService();

        List<FireStations> fireStations = service.getAllFireStations();

        assertNotNull(fireStations, "La liste ne doit pas être null");
        assertFalse(fireStations.isEmpty(), "La liste ne doit pas être vide");

    }

}