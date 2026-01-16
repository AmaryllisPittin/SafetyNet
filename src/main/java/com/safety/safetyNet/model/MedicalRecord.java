package com.safety.safetynet.model;

import java.util.List;

public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

public MedicalRecord() {}

public String getFirstName() { return firstName; }
public String getLastName() { return lastName; }
public String getBirthdate() { return birthdate; }
public List<String> getMedications() { return medications; }
public List<String> getAllergies() { return allergies; }

public void setFirstName(String firstName) { this.firstName = firstName; }
public void setLastName(String lastName) { this.lastName = lastName; }
public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
public void setMedications(List<String> medications) { this.medications = medications; }

};
