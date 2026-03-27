package com.example.demo.controllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.safety.safetynet.controller.FloodController;
import com.safety.safetynet.service.FloodService;

public class FloodControllerTests {

    private FloodController floodController;
    private FloodService floodService;

    @BeforeEach
    void setUp() {
        floodService = mock(FloodService.class);
        floodController = new FloodController(floodService);
    }

    @Test
    void shouldReturnFloodResidentForStations() throws Exception {

        when(floodService.getResidentsByStation(List.of("1"))).thenReturn(List.of());

        var response = floodController.getFloodByStations(List.of("1"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(floodService).getResidentsByStation(List.of("1"));
    }

}
