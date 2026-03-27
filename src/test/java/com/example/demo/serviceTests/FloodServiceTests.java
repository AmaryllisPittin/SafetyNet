package com.example.demo.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.service.FireStationService;
import com.safety.safetynet.service.FloodService;
import com.safety.safetynet.service.MedicalRecordService;
import com.safety.safetynet.service.PersonService;

public class FloodServiceTests {

    private PersonService personService;
    private MedicalRecordService medicalRecordService;
    private FireStationService fireStationService;
    private FloodService floodService;

    @BeforeEach
    void setUp() {
        personService = mock(PersonService.class);
        medicalRecordService = mock(MedicalRecordService.class);
        fireStationService = mock(FireStationService.class);

        floodService = new FloodService(personService, medicalRecordService, fireStationService);
    }

    @Test
    void getResidentsByStations_returnEmpty() throws Exception {

        when(fireStationService.getAllFireStations()).thenReturn(List.of());
        when(personService.getAllPersons()).thenReturn(List.of());

        var response = floodService.getResidentsByStation(List.of("1"));

        assertEquals(0, response.size());
    }

}
