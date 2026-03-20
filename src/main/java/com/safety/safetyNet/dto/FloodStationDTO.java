package com.safety.safetynet.dto;

import java.util.ArrayList;
import java.util.List;

public class FloodStationDTO {
    private List<PersonInfo> residents = new ArrayList<>();
    private String address;

    // getters - setters

    public List<PersonInfo> getResidents() {
        return residents;
    }

    public void setResidents(List<PersonInfo> residents) {
        this.residents = residents;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static class PersonInfo {
        private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private List<String> medications = new ArrayList<>();
        private List<String> allergies = new ArrayList<>();

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
            return medications;
        }

        public void setMedications(List<String> medications) {
            this.medications = medications;
        }

        public List<String> getAllergies() {
            return allergies;
        }

        public void setAllergies(List<String> allergies) {
            this.allergies = allergies;
        }
    }
}
