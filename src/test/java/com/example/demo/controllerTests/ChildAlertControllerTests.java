package com.example.demo.controllerTests;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safety.safetynet.config.LoggingFilter;
import com.safety.safetynet.controller.ChildAlertController;
import com.safety.safetynet.dto.ChildAlertDTO;
import com.safety.safetynet.service.FireStationService;

@WebMvcTest(controllers = ChildAlertController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LoggingFilter.class)
})
@ContextConfiguration(classes = com.safety.safetynet.MainSafetyNet.class)
public class ChildAlertControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Test
    void shouldReturnChildrenByAddress() throws Exception {

        ChildAlertDTO child = new ChildAlertDTO();
        child.setFirstName("Tenley");
        child.setLastName("Boyd");
        child.setAge(10);

        Mockito.when(fireStationService.getChildrenByAddress("1509 Culver St"))
                .thenReturn(List.of(child));

        mockMvc.perform(get("/childAlert").param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Tenley"))
                .andExpect(jsonPath("$.[0].age").value(10));

    }
}
