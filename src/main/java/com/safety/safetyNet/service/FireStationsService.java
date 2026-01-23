package com.safety.safetynet.service;

import java.util.List;

import com.safety.safetynet.model.FireStations;
import com.safety.safetynet.utils.JsonReader;

public class FireStationsService {
    
    public List<FireStations> getAllFireStations() throws Exception {
        return JsonReader.readFireStations();
    }

}