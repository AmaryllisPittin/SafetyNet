package com.example.demo.controllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;

import com.safety.safetynet.config.LoggingFilter;
import com.safety.safetynet.controller.CommunityEmailController;
import com.safety.safetynet.model.Person;
import com.safety.safetynet.service.PersonService;

@WebMvcTest(controllers = CommunityEmailController.class, excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LoggingFilter.class)
})
@ContextConfiguration(classes = com.safety.safetynet.MainSafetyNet.class)
public class CommunityEmailControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PersonService personService;

        @Test
        void shouldReturnEmailForCity() throws Exception {

                Person p1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
                                "jaboyd@email.com");
                Person p2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513",
                                "drk@email.com");
                Mockito.when(personService.getAllPersons()).thenReturn(List.of(p1, p2));

                mockMvc.perform(get("/communityEmail").param("city", "Culver"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0]").value("jaboyd@email.com"))
                                .andExpect(jsonPath("$[1]").value("drk@email.com"));
        }

        @Test
        void shouldReturnEmptyMailIfPresent() throws Exception {
                Person p1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
                                "");
                Mockito.when(personService.getAllPersons()).thenReturn(List.of(p1));

                mockMvc.perform(get("/communityEmail").param("city", "Culver"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0]").value(""));
        }

}
