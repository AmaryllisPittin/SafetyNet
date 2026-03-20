package com.safety.safetynet.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.model.Person;

@Service
public class PersonMedicalProfileService {

    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    public PersonMedicalProfileService(PersonService personService, MedicalRecordService medicalRecordService) {
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }

    // GET Combiné
    public Optional<Person> getPersonWithMedicalRecord(String firstName) throws Exception {
        Optional<Person> personOpt = personService.getPersonByFirstName(firstName);
        if (personOpt.isPresent()) {
            Optional<MedicalRecord> medOpt = medicalRecordService.getMedicalRecordByFirstName(firstName);
            medOpt.ifPresent(personOpt.get()::setMedicalRecord); /// Ajout du medicalRecord à la personne
        }
        return personOpt;
    }

    // POST
    public void addPersonWithMedicalRecord(Person person) throws Exception {
        personService.addPerson(person);
        if (person.getMedicalRecord() != null) {
            medicalRecordService.addMedicalRecord(person.getMedicalRecord());
        }
    }

    // PUT
    public boolean updatePersonWithMedicalRecord(String firstName, Person updatedPerson) throws Exception {
        boolean personUpdated = personService.updatePerson(firstName, updatedPerson);
        boolean medUpdated = true;
        if (updatedPerson.getMedicalRecord() != null) {
            medUpdated = medicalRecordService.updateMedicalRecord(firstName, updatedPerson.getMedicalRecord());
        }
        return personUpdated && medUpdated;
    }

    // DELETE
    public boolean deletePersonWithMedicalRecord(String firstName) throws Exception {
        boolean personDeleted = personService.deletePerson(firstName);
        boolean medDeleted = medicalRecordService.deleteMedicalRecord(firstName);
        return personDeleted || medDeleted;
    }
}
