package com.safety.safetynet.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safety.safetynet.dto.ChildAlertDTO;
import com.safety.safetynet.dto.FireDTO;
import com.safety.safetynet.dto.PersonDTO;
import com.safety.safetynet.mapper.PersonMapper;
import com.safety.safetynet.model.FireStation;
import com.safety.safetynet.model.MedicalRecord;
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

    // URL: http://localhost:8080/childAlert?address=<address>
    public List<ChildAlertDTO> getChildrenByAddress(String address) throws Exception {
        List<Person> persons = JsonReader.readPersons();
        List<MedicalRecord> medicalRecords = JsonReader.readMedicalRecord();

        // Les personnes qui vivent à cette addresse
        List<Person> household = persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .toList();

        List<ChildAlertDTO> children = new ArrayList<>();

        for (Person person : household) {

            MedicalRecord mr = medicalRecords.stream()
                    .filter(m -> m.getFirstName().equals(person.getFirstName())
                            && m.getLastName().equals(person.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (mr != null && PersonMapper.isMinor(mr)) {

                ChildAlertDTO dto = new ChildAlertDTO();
                dto.setFirstName(person.getFirstName());
                dto.setLastName(person.getLastName());
                dto.setAge(PersonMapper.calculateAge(mr));

                List<String> otherMembers = household.stream()
                        .filter(p -> !(p.getFirstName().equals(person.getFirstName())
                                && p.getLastName().equals(person.getLastName())))
                        .map(p -> p.getFirstName() + " " + p.getLastName())
                        .toList();

                dto.setHouseholdMembers(otherMembers);

                children.add(dto);
            }
        }

        return children;
    }

    // URL: http://localhost:8080/phoneAlert?firestation=%3Cfirestation_number
    public List<String> getPhoneNumbersByStation(String stationNumber) throws Exception {
        List<Person> persons = JsonReader.readPersons();
        List<FireStation> fireStations = JsonReader.readFireStations();

        // Récupération des adresses des personnes couvertes par la station
        List<String> addresses = fireStations.stream()
                .filter(fs -> fs.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .toList();

        List<String> phoneNumbers = persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(Person::getPhone)
                .distinct()
                .toList();

        return phoneNumbers;
    }

    // URL: http://localhost:8080/fire?address=<address>
    public List<FireDTO> getPersonsByAddress(String address) throws Exception {

        // Etape 1: Lecture des données
        List<Person> persons = JsonReader.readPersons();
        List<FireStation> fireStations = JsonReader.readFireStations();
        List<MedicalRecord> medicalRecords = JsonReader.readMedicalRecord();

        // Etape 2: Trouver le numéro de la caserne pour l'adresse
        String stationNumber = fireStations.stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(address))
                .map(FireStation::getStation)
                .findFirst()
                .orElse("Inconnu");

        // Etape 3: Filtre des habitants qui vivent à cette adresse
        List<Person> residents = persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .toList();

        // Création DTO
        List<FireDTO> fireDTOs = residents.stream().map(p -> {
            FireDTO dto = new FireDTO();
            dto.setFirstName(p.getFirstName());
            dto.setLastName(p.getLastName());
            dto.setPhone(p.getPhone());

            // Trouver le medicalRecord qui correspond
            MedicalRecord mr = medicalRecords.stream()
                    .filter(m -> m.getFirstName().equals(p.getFirstName())
                            && m.getLastName().equals(p.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (mr != null) {
                dto.setAge(PersonMapper.calculateAge(mr));
                dto.setMedications(mr.getMedications());
                dto.setAllergies(mr.getAllergies());
            } else {
                dto.setAge(0);
                dto.setMedications(Collections.emptyList());
                dto.setAllergies(Collections.emptyList());
            }

            dto.setStation(stationNumber);

            return dto;
        }).toList();

        return fireDTOs;
    }

}