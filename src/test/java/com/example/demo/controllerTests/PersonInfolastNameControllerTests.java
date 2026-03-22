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
import com.safety.safetynet.controller.PersonInfolastNameController;
import com.safety.safetynet.model.Person;
import com.safety.safetynet.service.PersonService;

@WebMvcTest(controllers = PersonInfolastNameController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LoggingFilter.class)
})
@ContextConfiguration(classes = com.safety.safetynet.MainSafetyNet.class)
public class PersonInfolastNameControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void shouldReturnListOfPersonsByLastName() throws Exception {

        Person p1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
                "jaboyd@email.com");
        Person p2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513",
                "drk@email.com");

        Mockito.when(personService.getPersonByLastName("Boyd"))
                .thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/personInfolastName").param("lastName", "Boyd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

    }
}
