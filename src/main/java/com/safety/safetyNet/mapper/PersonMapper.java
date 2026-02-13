package com.safety.safetynet.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import com.safety.safetynet.dto.PersonDTO;
import com.safety.safetynet.model.MedicalRecords;
import com.safety.safetynet.model.Person;


public class PersonMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static PersonDTO toDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAddress());
        dto.setPhone(person.getPhone());
        return dto;
    }


public static boolean isMinor(MedicalRecords medicalRecord) {
    
    LocalDate birthDate = LocalDate.parse(medicalRecord.getBirthdate(), FORMATTER);
    return Period.between(birthDate, LocalDate.now()).getYears() <= 18;
}
}
