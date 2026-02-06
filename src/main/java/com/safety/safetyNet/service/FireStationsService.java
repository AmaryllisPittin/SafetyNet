package com.safety.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import com.safety.safetynet.dto.PersonDTO;
import com.safety.safetynet.mapper.PersonMapper;
import com.safety.safetynet.model.FireStations;
import com.safety.safetynet.model.Person;
import com.safety.safetynet.utils.JsonReader;

public class FireStationsService {
    
    public List<FireStations> getAllFireStations() throws Exception {
        return JsonReader.readFireStations();
    }

    public List<PersonDTO> getPersonByStation(int stationNumber) throws Exception {
        List<FireStations> fireStations = JsonReader.readFireStations();
        List<Person> persons = JsonReader.readPersons();

        List<String> addresses = fireStations.stream()
            .filter(fs -> Integer.parseInt(fs.getStation()) == stationNumber)
            .map(FireStations::getAddress)
            .collect(Collectors.toList());


        List<PersonDTO> personDTO = persons.stream()
            .filter(p -> addresses.contains(p.getAddress()))
            .map(PersonMapper::toDTO)
            .collect(Collectors.toList());

        return personDTO;
    }

}