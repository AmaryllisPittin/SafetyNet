package com.example.demo.mapperTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.safety.safetynet.mapper.PersonMapper;
import com.safety.safetynet.model.MedicalRecord;

public class PersonMapperTests {

    @Test
    void isMinorTest() {
        MedicalRecord mr = new MedicalRecord();
        mr.setBirthdate("01/03/2010");

        boolean isMinor = PersonMapper.isMinor(mr);

        assertTrue(isMinor, "La personne est mineure");

    }

}
