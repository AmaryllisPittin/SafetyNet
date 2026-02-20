package com.safety.safetynet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safety.safetynet.model.Person;
import com.safety.safetynet.utils.JsonReader;

@Service
public class PersonService {
    
    public List<Person> getAllPersons() throws Exception {
        return JsonReader.readPersons();
    }

    //Récupération d'une personne par son prénom
    public Optional<Person> getPersonByFirstName(String firstName) throws Exception {
        return getAllPersons().stream()
        .filter(p -> p.getFirstName().equalsIgnoreCase(firstName))
        .findFirst();
    }

    //Ajouter une personne
    public void addPerson(Person person) throws Exception {
        List<Person> persons = getAllPersons();
        persons.add(person);
        JsonReader.writePersons(persons);
    }

    //Modifier une personne
    public boolean updatePerson(String firstName, Person updatedPerson) throws Exception {
        List<Person> persons = getAllPersons();
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getFirstName().equalsIgnoreCase(firstName)) {
                persons.set(i, updatedPerson);
                JsonReader.writePersons(persons);
                return true;
            }
        }
        return false;
    }

    public boolean deletePerson(String firstName) throws Exception {
        List<Person> persons = getAllPersons();
        boolean removed = persons.removeIf(p -> p.getFirstName().equalsIgnoreCase(firstName));
        if (removed) {
            JsonReader.writePersons(persons);
        }
        return removed;
    }
}
