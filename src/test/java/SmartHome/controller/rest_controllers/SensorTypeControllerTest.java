package SmartHome.controller.rest_controllers;

import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.sensortype.SensorTypeFactory;
import SmartHome.domain.valueObjects.MeasurementUnit;
import SmartHome.domain.valueObjects.SensorTypeId;
import SmartHome.service.SensorTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorTypeController.class)
@AutoConfigureMockMvc
public class SensorTypeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    SensorTypeService sensorTypeService;

    @MockBean
    SensorTypeFactory sensorTypeFactory;

    @MockBean
    SensorType sensorType;

    @BeforeEach
    void setUp() {
        SensorTypeId sensorTypeId = new SensorTypeId("ST1");
        MeasurementUnit measurementUnit = MeasurementUnit.valueOf("Percentage");

        when(sensorTypeFactory.createSensorType(sensorTypeId, measurementUnit))
                .thenReturn(sensorType);
        when(sensorType.identity()).thenReturn(sensorTypeId);
        when(sensorType.getMeasurementUnit()).thenReturn(measurementUnit);
    }

    @Test
    void getSensorTypes() throws Exception {
        // Arrange
        String jsonResponse = """
                 [
                    {
                        "sensorTypeId": "ST1",
                        "measurementUnit": "Percentage",
                        "links": [
                                    {
                                        "rel": "self",
                                        "href": "http://localhost/api/v1/sensor-types/ST1"
                                    }
                        ]
                    }
                ]
                """;

        when(sensorTypeService.findAll()).thenReturn(List.of(sensorType));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensor-types"))
                .andExpect(status().isOk());

        //Assert
        resultActions.andExpect(content().json(jsonResponse));
    }

    @Test
    void getSensorTypes_emptyList() throws Exception {
        //Arrange
        String jsonResponse = """
                []
                """;

        when(sensorTypeService.findAll()).thenReturn(List.of());

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensor-types"))
                .andExpect(status().isOk());

        //Assert
        resultActions.andExpect(content().json(jsonResponse));
    }

    @Test
    void getSensorTypeById() throws Exception {
        //Arrange
        String jsonResponse = """
                       {
                           "sensorTypeId": "ST1",
                           "measurementUnit": "Percentage",
                           "_links": {
                                 "self": {
                                         "href": "http://localhost/api/v1/sensor-types"
                                     }
                             }
                       }
                """;

        when(sensorTypeService.findById("ST1")).thenReturn(sensorType);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensor-types/ST1"))
                .andExpect(status().isOk());

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
                .getContentAsString(), false);
    }

    @Test
    void getSensorTypeWithInvalidId() throws Exception {
        //Arrange
        String jsonResponse = """
                    {
                        "error": "Bad id input",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensor-types"
                            }
                        }
                    }
                """;

        when(sensorTypeService.findById("abc")).thenThrow(new IllegalArgumentException("Bad id input"));

        //Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensor-types/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        //Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse()
                .getContentAsString(), false);
    }

    @Test
    void addSensorType() throws Exception {
        //Arrange
        String jsonRequest = """
                {
                    "sensorTypeId": "ST1",
                    "measurementUnit": "Percentage"
                }
                """;

        String jsonResponse = """
                {
                    "sensorTypeId": "ST1",
                    "measurementUnit": "Percentage",
                    "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensor-types/ST1"
                                }
                            }
                }
                """;

        when(sensorTypeService.add(any(), any())).thenReturn(sensorType);

        //Act
        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensor-types")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));

        //Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);

    }

    @Test
    void addSensorType_failSensorTypeIdAlreadyExists() throws Exception {

        //Arrange
        when(sensorTypeService.add(any(), any()))
                .thenThrow(new EntityNotFoundException("Sensor type id already exist"));

        String jsonRequest = """
                {
                    "sensorTypeId": "ST1",
                    "measurementUnit": "Percentage"
                }
                """;

        String jsonResponse = """
                {
                        "error": "Sensor type id already exist",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensor-types"
                            }
                        }
                    }
                """;

        //Act
        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensor-types")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonResponse));

        //Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }
}
