package com.example.demo.modelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.model.FireStations;

public class FireStationsTests {
    @Test
    void TestGettersAndSetters() {

        FireStations fireStations = new FireStations();
        fireStations.setAddress("1509 Culver St");
        fireStations.setStation("3");

        assertEquals("1509 Culver St", fireStations.getAddress());
        assertEquals("3", fireStations.getStation());

    }
}

