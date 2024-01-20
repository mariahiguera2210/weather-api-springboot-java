package org.adaschool.Weather.service;
import static org.mockito.Mockito.*;

import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherReportServiceTest {


    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherReportService weatherReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherReport() {

        WeatherApiResponse mockApiResponse = new WeatherApiResponse();
        WeatherApiResponse.Main mockMain = new WeatherApiResponse.Main();
        mockMain.setTemperature(25.5);
        mockMain.setHumidity(70.0);
        mockApiResponse.setMain(mockMain);


        String expectedUrl = "https://api.openweathermap.org/data/2.5/weather?lat=37.8267&lon=-122.4233&appid=88bd058daef08ae2c2a0b29d6a5a3c26";

        // Performing the service method call
        when(restTemplate.getForObject(eq(expectedUrl), eq(WeatherApiResponse.class))).thenReturn(mockApiResponse);

        WeatherReport weatherReport = weatherReportService.getWeatherReport(37.8267, -122.4233);

        // Verifying that the restTemplate was called with the correct URL
        verify(restTemplate, times(1)).getForObject(eq(expectedUrl), eq(WeatherApiResponse.class));

        // Verifying that the WeatherReport object has the expected values
        assertEquals(25.5, weatherReport.getTemperature());
        assertEquals(70.0, weatherReport.getHumidity());
    }

}