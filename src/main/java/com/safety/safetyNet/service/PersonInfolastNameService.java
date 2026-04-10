package com.safety.safetynet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.safety.safetynet.dto.PersonInfolastNameDTO;
import com.safety.safetynet.mapper.PersonMapper;
import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.model.Person;
import com.safety.safetynet.utils.JsonReader;

@Service
public class PersonInfolastNameService {

    public List<PersonInfolastNameDTO> getPersonByLastName(String lastName) throws Exception {

        List<Person> persons = JsonReader.readPersons().stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .toList();

        List<MedicalRecord> mds = JsonReader.readMedicalRecord();

        return persons.stream().map(p -> {
            MedicalRecord md = mds.stream()
                    .filter(m -> m.getFirstName().equals(p.getFirstName())
                            && m.getLastName().equals(p.getLastName()))
                    .findFirst()
                    .orElse(null);

            PersonInfolastNameDTO dto = new PersonInfolastNameDTO();
            dto.setFirstName(p.getFirstName());
            dto.setLastName(p.getLastName());
            dto.setAddress(p.getAddress());
            dto.setEmail(p.getEmail());

            if (mds != null) {
                dto.setMedications(md.getMedications());
                dto.setAllergies(md.getAllergies());
                dto.setAge(PersonMapper.calculateAge(md));
            }

            return dto;
        }).toList();

    }

}
