package com.safety.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safety.safetynet.model.Person;
import com.safety.safetynet.utils.JsonReader;

@Service
public class PersonService {

    public List<Person> getAllPersons() throws Exception {
        return JsonReader.readPersons();
    }

    private boolean matchFullName(Person p, String firstName, String lastName) {
        return p.getFirstName().trim().equalsIgnoreCase(firstName.trim())
                && p.getLastName().trim().equalsIgnoreCase(lastName.trim());
    }

    // Récupération d'une personne par son prénom et nom
    public List<Person> getPersonByName(String firstName, String lastName) throws Exception {
        return getAllPersons().stream()
                .filter(p -> matchFullName(p, firstName, lastName))
                .collect(Collectors.toList());
    }

    // Récupération d'une ou des personnes par le nom de famille
    public List<Person> getPersonByLastName(String lastName) throws Exception {
        return getAllPersons().stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .toList();
    }

    // Ajouter une personne
    public void addPerson(Person person) throws Exception {
        List<Person> persons = getAllPersons();
        persons.add(person);
        JsonReader.writePersons(persons);
    }

    // Modifier une personne
    public boolean updatePerson(String firstName, String lastName, Person updatedPerson) throws Exception {
        List<Person> persons = getAllPersons();
        for (int i = 0; i < persons.size(); i++) {
            if (matchFullName(persons.get(i), firstName, lastName)) {
                persons.set(i, updatedPerson);
                JsonReader.writePersons(persons);
                return true;
            }
        }
        return false;
    }

    public boolean deletePerson(String firstName, String lastName) throws Exception {
        List<Person> persons = getAllPersons();
        boolean removed = persons.removeIf(p -> matchFullName(p, firstName, lastName));
        if (removed) {
            JsonReader.writePersons(persons);
        }
        return removed;
    }
}
