package com.safety.safetynet.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safety.safetynet.dto.FloodStationDTO;
import com.safety.safetynet.mapper.PersonMapper;
import com.safety.safetynet.model.FireStation;
import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.model.Person;

@Service
public class FloodService {
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;
    private final FireStationService fireStationService;

    public FloodService(PersonService personService, MedicalRecordService medicalRecordService,
            FireStationService fireStationService) {
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
        this.fireStationService = fireStationService;
    }

    public List<FloodStationDTO> getResidentsByStation(List<String> stationNumbers) throws Exception {
        List<FloodStationDTO> result = new ArrayList<>();

        // Etape 1 : Récupération de toutes les addresses desservies par les stations
        List<String> addresses = fireStationService.getAllFireStations().stream()
                .filter(f -> stationNumbers.contains(f.getStation()))
                .map(FireStation::getAddress)
                .distinct()
                .toList();

        // Etape 2 : Récupération des résidents pour chaque addresse
        for (String address : addresses) {
            FloodStationDTO dto = new FloodStationDTO();
            dto.setAddress(address);

            List<Person> residents = personService.getAllPersons().stream()
                    .filter(p -> p.getAddress().equalsIgnoreCase(address))
                    .collect(Collectors.toList());
            if (residents == null)
                residents = new ArrayList<>();

            // Etape 3 : Récupération des infos médicales pour chaque résident
            for (Person p : residents) {
                FloodStationDTO.PersonInfo info = new FloodStationDTO.PersonInfo();
                info.setFirstName(p.getFirstName());
                info.setLastName(p.getLastName());
                info.setPhone(p.getPhone());

                MedicalRecord med = medicalRecordService.getMedicalRecordByFirstName(p.getFirstName())
                        .orElse(null);

                if (med != null) {
                    info.setMedications(med.getMedications());
                    info.setAllergies(med.getAllergies());
                    info.setAge(PersonMapper.calculateAge(med));
                } else {
                    info.setMedications(Collections.emptyList());
                    info.setAllergies(Collections.emptyList());
                    info.setAge(0);
                }

                dto.getResidents().add(info);
            }

            result.add(dto);

        }

        return result;
    }

}
