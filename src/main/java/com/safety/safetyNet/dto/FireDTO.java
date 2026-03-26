package com.safety.safetynet.dto;

import java.util.List;

public class FireDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medication;
    private List<String> allergie;
    private String station;

    public FireDTO() {
    };

    public FireDTO(String firstName, String lastName, String phone, int age,
            List<String> medication, List<String> allergie, String station) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medication = medication;
        this.allergie = allergie;
        this.station = station;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getMedications() {
        return medication;
    }

    public void setMedications(List<String> medication) {
        this.medication = medication;
    }

    public List<String> getAllergies() {
        return allergie;
    }

    public void setAllergies(List<String> allergie) {
        this.allergie = allergie;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

}
