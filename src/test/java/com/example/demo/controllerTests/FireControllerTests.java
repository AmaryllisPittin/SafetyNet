package com.example.demo.controllerTests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;

import com.safety.safetynet.config.LoggingFilter;
import com.safety.safetynet.controller.FireController;
import com.safety.safetynet.dto.FireDTO;
import com.safety.safetynet.service.FireStationService;

@WebMvcTest(controllers = FireController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LoggingFilter.class)
})
@ContextConfiguration(classes = com.safety.safetynet.MainSafetyNet.class)
public class FireControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Test
    void shouldReturnPersonsAndFireStations() throws Exception {

        String address = "1509 Culver St";

        FireDTO person1 = new FireDTO("John", "Boyd", "841-874-6512", 42,
                List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan"), "3");

        FireDTO person2 = new FireDTO("Jacob", "Boyd", "841-874-6513", 37,
                List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), List.of(), "3");

        List<FireDTO> expectedList = List.of(person1, person2);

        when(fireStationService.getPersonsByAddress(address)).thenReturn(expectedList);

        mockMvc.perform(get("/fire").param("address", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jacob"));
    }
}
