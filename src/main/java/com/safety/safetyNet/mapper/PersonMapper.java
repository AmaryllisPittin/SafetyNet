package com.safety.safetynet.mapper;

import java.time.LocalDate;
import java.time.Period;

import com.safety.safetynet.dto.PersonDTO;
import com.safety.safetynet.model.Person;
import com.safety.safetynet.model.MedicalRecords;


public class PersonMapper {

    public static PersonDTO toDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAddress());
        dto.setPhone(person.getPhone());
        return dto;
    }

public static boolean isMinor(MedicalRecords medicalRecord) {
    LocalDate birthDate = LocalDate.parse(medicalRecord.getBirthdate());
    return Period.between(birthDate, LocalDate.now()).getYears() <= 18;
}
}
