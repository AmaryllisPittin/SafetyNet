package com.example.demo.controllerTests;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.safetynet.config.LoggingFilter;
import com.safety.safetynet.controller.FireStationController;
import com.safety.safetynet.model.FireStation;
import com.safety.safetynet.service.FireStationService;

@WebMvcTest(controllers = FireStationController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LoggingFilter.class)
})
@ContextConfiguration(classes = com.safety.safetynet.MainSafetyNet.class)
public class FireStationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnListOfFireStations() throws Exception {

        FireStation s1 = new FireStation();
        s1.setAddress("1509 Culver St");
        s1.setStation("3");

        FireStation s2 = new FireStation();
        s2.setAddress("29 15th St");
        s2.setStation("2");

        List<FireStation> fireStations = Arrays.asList(s1, s2);

        // Mock du service
        when(fireStationService.getAllFireStations()).thenReturn(fireStations);

        // Test sur l'endpoint
        mockMvc.perform(get("/firestation")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fireStations)));

        // Vérification que le service n'est appelé qu'une fois
        verify(fireStationService, times(1)).getAllFireStations();
        verifyNoMoreInteractions(fireStationService);
    }

    @Test
    void shouldGetAllFireStationsReturnEmptyList() throws Exception {

        when(fireStationService.getAllFireStations()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/firestation")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(fireStationService, times(1)).getAllFireStations();
        verifyNoMoreInteractions(fireStationService);
    }

    @Test
    void GetAllFireStationsThrowException() throws Exception {
        when(fireStationService.getAllFireStations()).thenThrow(new RuntimeException("Service failure"));

        mockMvc.perform(get("/firestation")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(fireStationService, times(1)).getAllFireStations();
        verifyNoMoreInteractions(fireStationService);
    }

    @Test
    void getFireStationByAddress_shouldReturn200() throws Exception {
        FireStation station = new FireStation();
        station.setAddress("29 15th St");
        station.setStation("2");
        Mockito.when(fireStationService.getFireStationByAddress("29 15th St"))
                .thenReturn(Optional.of(station));

        mockMvc.perform(
                get("/firestation/{address}", "29 15th St")
                        .param("address", "29 15th St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("29 15th St"))
                .andExpect(jsonPath("$.station").value("2"));
    }

    @Test
    void deleteFireStation_shouldReturnOk() throws Exception {
        Mockito.when(fireStationService.deleteFireStation("29 15th St")).thenReturn(true);

        mockMvc.perform(delete("/firestation/29 15th St"))
                .andExpect(status().isOk());

        Mockito.verify(fireStationService).deleteFireStation("29 15th St");
    }

}
