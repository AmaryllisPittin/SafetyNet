package com.example.demo.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.model.FireStation;
import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.model.Person;
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

    @Test
    void getResidentsByStations_AddressButNoResidents() throws Exception {
        when(fireStationService.getAllFireStations())
                .thenReturn(List.of(new FireStation("123 Rue du Marais", "1")));

        when(personService.getAllPersons()).thenReturn(List.of());

        var response = floodService.getResidentsByStation(List.of("1"));

        assertEquals(1, response.size());
        assertEquals("123 Rue du Marais", response.get(0).getAddress());
        assertTrue(response.get(0).getResidents().isEmpty());
    }

    @Test
    void getResidentsByStations_widthMedicalRecord() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "3");

        Person person = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
                "tenz@email.com");

        MedicalRecord med = new MedicalRecord("John", "Boyd", "03/06/1984",
                List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"));

        when(fireStationService.getAllFireStations()).thenReturn(List.of(fireStation));
        when(personService.getAllPersons()).thenReturn(List.of(person));
        when(medicalRecordService.getMedicalRecordByFirstName("John")).thenReturn(Optional.of(med));

        var response = floodService.getResidentsByStation(List.of("3"));

        assertEquals(1, response.size());
        assertEquals(1, response.get(0).getResidents().size());
    }

    @Test
    void getResidentsByStations_widthoutMedicalRecord() throws Exception {
        FireStation fireStation = new FireStation("1509 Culver St", "3");

        Person person = new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
                "tenz@email.com");

        when(fireStationService.getAllFireStations()).thenReturn(List.of(fireStation));
        when(personService.getAllPersons()).thenReturn(List.of(person));

        var response = floodService.getResidentsByStation(List.of("3"));
        var resident = response.get(0).getResidents().get(0);

        assertEquals(0, resident.getAge());
        assertTrue(resident.getMedications().isEmpty());
        assertTrue(resident.getAllergies().isEmpty());
    }

    @Test
    void getResidentsByStations_multipleAddresses() throws Exception {
        when(fireStationService.getAllFireStations()).thenReturn(List.of(
                new FireStation("29 15th St", "2"),
                new FireStation("30 15th St", "2")));

        when(personService.getAllPersons()).thenReturn(List.of());
        var response = floodService.getResidentsByStation(List.of("2"));

        assertEquals(2, response.size());
    }

}
