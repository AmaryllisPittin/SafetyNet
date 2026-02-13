package com.example.demo.controllerTests;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.safetynet.controller.PersonController;
import com.safety.safetynet.model.Person;
import com.safety.safetynet.service.PersonService;



@WebMvcTest(PersonController.class)
@ContextConfiguration(classes = com.safety.safetynet.MainSafetyNet.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnListOfPersons() throws Exception {

        Person p1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
        Person p2 = new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com");
        List<Person> persons = Arrays.asList(p1, p2);

        //Mock du service
        when(personService.getAllPersons()).thenReturn(persons);

        //Test sur l'endpoint
        mockMvc.perform(get("/persons")
                            .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(objectMapper.writeValueAsString(persons)));

        //Vérification que le service n'est appelé qu'une fois
        verify(personService, times(1)).getAllPersons();
        verifyNoMoreInteractions(personService);
    }

    @Test
    void shouldGetAllPersonsReturnEmptyList() throws Exception {
    
        when(personService.getAllPersons()).thenReturn(Collections.emptyList());

                mockMvc.perform(get("/persons")
                            .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json("[]"));

                verify(personService, times(1)).getAllPersons();
                verifyNoMoreInteractions(personService);
    }

    @Test
    void GetAllPersonsThrowException() throws Exception {
        when(personService.getAllPersons()).thenThrow(new RuntimeException("Service failure"));

        mockMvc.perform(get("/persons")
                    .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());

        verify(personService, times(1)).getAllPersons();
        verifyNoMoreInteractions(personService);
        
    }

}