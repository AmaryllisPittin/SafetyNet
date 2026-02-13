package com.example.demo.serviceTests;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.dto.PersonDTO;
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

    //Vérifie le filtrage par station
    @Test
    void shouldReturnPersonsCoveredByStations() throws Exception {

        FireStationsService service = new FireStationsService();

        List<PersonDTO> result = service.getPersonByStation(1);

        assertFalse(result.isEmpty());
        assertTrue(
            result.stream().allMatch(p -> p.getAddress() != null)
        );

    }

    //Dans le cas où la station n'existe pas
    @Test
    void shouldReturnEmptyListWhenStationDoesNotExist() throws Exception {

        FireStationsService service = new FireStationsService();

        List<PersonDTO> result = service.getPersonByStation(997);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
   
    @Test
    void personDTOShouldContainRequiredFields() throws Exception {

        FireStationsService service = new FireStationsService();

        List<PersonDTO> result = service.getPersonByStation(1);
        PersonDTO person = result.get(0);

        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getPhone());
    }

}