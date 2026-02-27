package com.example.demo.modelTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.model.FireStation;

public class FireStationsTests {
    @Test
    void TestGettersAndSetters() {

        FireStation fireStations = new FireStation();
        fireStations.setAddress("1509 Culver St");
        fireStations.setStation("3");

        assertEquals("1509 Culver St", fireStations.getAddress());
        assertEquals("3", fireStations.getStation());

    }
}
