package com.safety.safetynet.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safety.safetynet.dto.PersonDTO;
import com.safety.safetynet.mapper.PersonMapper;
import com.safety.safetynet.model.FireStation;
import com.safety.safetynet.model.Person;
import com.safety.safetynet.utils.JsonReader;

@Service
public class FireStationService {

    public List<FireStation> getAllFireStations() throws Exception {
        return JsonReader.readFireStations();
    }

    public List<PersonDTO> getPersonByStation(int stationNumber) throws Exception {
        List<FireStation> fireStations = JsonReader.readFireStations();
        List<Person> persons = JsonReader.readPersons();

        List<String> addresses = fireStations.stream()
                .filter(fs -> Integer.parseInt(fs.getStation()) == stationNumber)
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        List<PersonDTO> personDTO = persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(PersonMapper::toDTO)
                .collect(Collectors.toList());

        return personDTO;
    }

    // Récupération d'une caserne par son addresse
    public Optional<FireStation> getFireStationByAddress(String address) throws Exception {
        return getAllFireStations().stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .findFirst();
    }

    // Ajouter une caserne
    public void addFireStation(FireStation fireStation) throws Exception {
        List<FireStation> fireStations = getAllFireStations();
        fireStations.add(fireStation);
        JsonReader.writeFireStation(fireStations);
    }

    // Modifier une caserne
    public boolean updateFireStation(String address, FireStation updatedFireStation) throws Exception {
        List<FireStation> fireStations = getAllFireStations();
        for (int i = 0; i < fireStations.size(); i++) {
            if (fireStations.get(i).getAddress().equalsIgnoreCase(address)) {
                fireStations.set(i, updatedFireStation);
                JsonReader.writeFireStation(fireStations);
                return true;
            }
        }
        return false;
    }

    public boolean deleteFireStation(String address) throws Exception {
        List<FireStation> fireStations = getAllFireStations();
        boolean removed = fireStations.removeIf(p -> p.getAddress().equalsIgnoreCase(address));
        if (removed) {
            JsonReader.writeFireStation(fireStations);
        }
        return removed;
    }

}