package com.example.demo.controllerTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.safetynet.config.LoggingFilter;
import com.safety.safetynet.controller.MedicalRecordController;
import com.safety.safetynet.model.MedicalRecord;
import com.safety.safetynet.service.MedicalRecordService;

@WebMvcTest(controllers = MedicalRecordController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LoggingFilter.class)
})
@ContextConfiguration(classes = com.safety.safetynet.MainSafetyNet.class)
public class MedicalRecordControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnListOfMedicalRecords() throws Exception {

        MedicalRecord m1 = new MedicalRecord();
        m1.setFirstName("John");
        m1.setLastName("Boyd");
        m1.setBirthdate("03/06/1984");
        m1.setMedications(List.of("aznol:350mg", "hydrapermazol:100mg"));
        m1.setAllergies(List.of("nillacilan"));

        MedicalRecord m2 = new MedicalRecord();
        m2.setFirstName("Jacob");
        m2.setLastName("Boyd");
        m2.setBirthdate("03/06/1989");
        m2.setMedications(List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"));
        m2.setAllergies(List.of(""));

        List<MedicalRecord> medicalRecords = Arrays.asList(m1, m2);

        // Mock du service
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecords);

        // Test sur l'endpoint
        mockMvc.perform(get("/medicalrecords")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(medicalRecords)));

        // Vérification que le service n'est appelé qu'une fois
        verify(medicalRecordService, times(1)).getAllMedicalRecords();
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    void shouldGetAllMedicalRecordsReturnEmptyList() throws Exception {

        when(medicalRecordService.getAllMedicalRecords()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/medicalrecords")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(medicalRecordService, times(1)).getAllMedicalRecords();
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    void GetAllMedicalRecordsThrowException() throws Exception {
        when(medicalRecordService.getAllMedicalRecords()).thenThrow(new RuntimeException("Service failure"));

        mockMvc.perform(get("/medicalrecords")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(medicalRecordService, times(1)).getAllMedicalRecords();
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    void GetMedicalRecordByPerson_NotFound() throws Exception {
        Mockito.when(medicalRecordService.getMedicalRecordByFirstName("Jojo"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/medicalrecords/Jojo/Joker")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(medicalRecordService, times(1)).getMedicalRecordByFirstName("Jojo");
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    void UpdateMedicalRecordTest() throws Exception {
        String updatedJson = """
                {
                    "firstName":"John",
                    "lastName":"Boyd",
                    "birthdate":"03/06/1984",
                    "medications":["aznol:350mg",
                    "hydrapermazol:100mg"],
                    "allergies":["nillacilan"]
                }
                """;

        when(medicalRecordService.updateMedicalRecord(eq("John"), any(MedicalRecord.class))).thenReturn(true);

        mockMvc.perform(put("/medicalrecords/John/Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1)).updateMedicalRecord(eq("John"), any(MedicalRecord.class));
        verifyNoMoreInteractions(medicalRecordService);

    }

    @Test
    void UpdatedMedicalRecord_ShouldReturnNotFound() throws Exception {

        String updatedJson = """
                {
                    "firstName":"John",
                    "lastName":"Boyd",
                    "birthdate":"03/06/1984",
                    "medications":["aznol:350mg",
                    "hydrapermazol:100mg"],
                    "allergies":["nillacilan"]
                }
                """;

        when(medicalRecordService.updateMedicalRecord(eq("John"), any(MedicalRecord.class))).thenReturn(false);

        mockMvc.perform(put("/medicalrecords/John/Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
                .andExpect(status().isNotFound());

        verify(medicalRecordService, times(1)).updateMedicalRecord(eq("John"), any(MedicalRecord.class));
        verifyNoMoreInteractions(medicalRecordService);

    }

    @Test
    void DeleteMedicalRecord_ShouldReturnOk() throws Exception {

        when(medicalRecordService.deleteMedicalRecord("John", "Boyd")).thenReturn(true);

        mockMvc.perform(delete("/medicalrecords/John/Boyd"))
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1)).deleteMedicalRecord("John", "Boyd");
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    void DeleteMedicalRecord_ShouldReturnNotFound() throws Exception {

        when(medicalRecordService.deleteMedicalRecord("John", "Boyd")).thenReturn(false);

        mockMvc.perform(delete("/medicalrecords/John/Boyd"))
                .andExpect(status().isNotFound());

        verify(medicalRecordService, times(1)).deleteMedicalRecord("John", "Boyd");
        verifyNoMoreInteractions(medicalRecordService);
    }

}
