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
import com.safety.safetynet.dto.PersonInfolastNameDTO;
import com.safety.safetynet.service.PersonInfolastNameService;

@WebMvcTest(controllers = PersonInfolastNameController.class, excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LoggingFilter.class)
})
@ContextConfiguration(classes = com.safety.safetynet.MainSafetyNet.class)
public class PersonInfolastNameControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PersonInfolastNameService personInfolastNameService;

        @Test
        void shouldReturnListOfPersonsByLastName() throws Exception {

                PersonInfolastNameDTO p1 = new PersonInfolastNameDTO();
                PersonInfolastNameDTO p2 = new PersonInfolastNameDTO();

                p1.setLastName("Boyd");
                p2.setLastName("Boyd");

                Mockito.when(personInfolastNameService.getPersonByLastName("Boyd"))
                                .thenReturn(List.of(p1, p2));

                mockMvc.perform(get("/personInfolastName").param("lastName", "Boyd"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(2));

        }
}
