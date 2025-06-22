package SmartHome.controller.rest_controllers;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.service.ActuatorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActuatorController.class)
@AutoConfigureMockMvc
class ActuatorControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ActuatorService actuatorService;
    Actuator actuator;

    @BeforeEach
    void setUp()
    {
        DeviceID deviceId = new DeviceID(1L);
        ActuatorModel actuatorModel = new ActuatorModel("BlindRollerActuator");
        ActuatorName actuatorName = new ActuatorName("name");
        ActuatorId actuatorId = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("type");
        actuator = new ActuatorFactory().createActuator(deviceId, actuatorModel, actuatorName, actuatorId, actuatorTypeId);
    }


    @Test
    void addActuator() throws Exception
    {
        // Arrange
        String jsonResponse = """
                {
                    "name": "name",
                    "model": "BlindRollerActuator",
                    "actuatorId": 1,
                    "deviceId": 1,
                    "actuatorType": "type",
                    "lowerLimit": 0,
                    "upperLimit": 0,
                    "lowerLimitFractional": 0.0,
                    "upperLimitFractional": 0.0,
                    "_links": {
                        "self": {
                            "href": "http://localhost/api/v1/actuators/1"
                        }
                    }
                }
                """;

        String request = """
                {
                    "name": "name",
                    "model": "BlindRollerActuator",
                    "deviceId": 1,
                    "actuatorType": "type"
                }
                """;

        when(actuatorService.add(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(actuator);

        // Act
        ResultActions result = mockMvc.perform(post("/api/v1/actuators").contentType("application/json").content(request))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, result.andReturn().getResponse().getContentAsString(), false);
    }


    @Test
    void findAllActuators() throws Exception
    {
        // Arrange

        String jsonResponse = """
                [
                         {
                             "name": "name",
                             "model": "BlindRollerActuator",
                             "actuatorId": 1,
                             "deviceId": 1,
                             "actuatorType": "type",
                             "lowerLimit": 0,
                             "upperLimit": 0,
                             "lowerLimitFractional": 0.0,
                             "upperLimitFractional": 0.0,
                             "currentValue": 100,
                             "links": [
                                 {
                                     "rel": "self",
                                     "href": "http://localhost/api/v1/actuators/1"
                                 },
                                 {
                                     "rel": "activityLogs",
                                     "href": "http://localhost/api/v1/actuator-activity-logs?actuator-id=1",
                                     "type": "GET"
                                 },
                                 {
                                     "rel": "device",
                                     "href": "http://localhost/api/v1/devices/1",
                                     "type": "GET"
                                 }
                             ]
                         }
                     ]
                """;

        when(actuatorService.findAll()).thenReturn(List.of(actuator));

        // Act
        ResultActions result = mockMvc.perform(get("/api/v1/actuators"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, result.andReturn().getResponse().getContentAsString(), false);

    }


    @Test
    void findActuatorById() throws Exception
    {
        // Arrange
        String jsonResponse = """
                {
                     "name": "name",
                     "model": "BlindRollerActuator",
                     "actuatorId": 1,
                     "deviceId": 1,
                     "actuatorType": "type",
                     "lowerLimit": 0,
                     "upperLimit": 0,
                     "lowerLimitFractional": 0.0,
                     "upperLimitFractional": 0.0,
                     "_links": {
                         "self": {
                             "href": "http://localhost/api/v1/actuators"
                         }
                     }
                 }
                """;

        when(actuatorService.findById(1L)).thenReturn(actuator);

        // Act
        ResultActions result = mockMvc.perform(get("/api/v1/actuators/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, result.andReturn().getResponse().getContentAsString(), false);
    }


    @Test
    void addActuator_failDeviceIdDontExist() throws Exception
    {
        when(actuatorService.add(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenThrow(new EntityNotFoundException("Device id doesn't exist"));

        String jsonRequest = """
                {
                                 "name": "name",
                                 "model": "BlindRollerActuator",
                                 "deviceId": 1000,
                                 "actuatorType": "BlindRoller"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "Device id doesn't exist",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuators"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/actuators")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addActuator_failInvalidActuatorAttributes() throws Exception
    {
        when(actuatorService.add(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenThrow(new IllegalArgumentException("Invalid actuator attributes"));

        String jsonRequest = """
                {
                                 "name": "name",
                                 "model": "BlindRollerActuator",
                                 "deviceId": 1,
                                 "actuatorType": "BlindRoller"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "Invalid actuator attributes",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuators"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/actuators")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addActuator_failDataIntegrityViolation() throws Exception
    {
        when(actuatorService.add(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenThrow(new DataIntegrityViolationException("Data integrity violation"));

        String jsonRequest = """
                {
                                 "name": "name",
                                 "model": "BlindRollerActuator",
                                 "deviceId": 1,
                                 "actuatorType": "BlindRoller"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "Data integrity violation",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuators"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/actuators")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void findActuatorById_failActuatorIdDoesNotExist() throws Exception
    {
        when(actuatorService.findById(1L))
                .thenThrow(new EntityNotFoundException("Actuator id doesn't exist"));

        String jsonResponse = """
                {
                    "error": "Actuator id doesn't exist",
                    "_links": {
                        "self": {
                            "href": "http://localhost/api/v1/actuators"
                        }
                    }
                }
                """;

        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuators/1"))
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }


    @Test
    void findActuatorById_failInvalid() throws Exception
    {
        when(actuatorService.findById(1L))
                .thenThrow(new IllegalArgumentException("Invalid actuator id"));

        String jsonResponse = """
                {
                    "error": "Invalid actuator id",
                    "_links": {
                        "self": {
                            "href": "http://localhost/api/v1/actuators"
                        }
                    }
                }
                """;

        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuators/1"))
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }


    @Test
    void findAllActuatorsByDevice() throws Exception {
        // Arrange
        DeviceID deviceId = new DeviceID(1L);
        ActuatorModel actuatorModel = new ActuatorModel("BlindRollerActuator");
        ActuatorName actuatorName = new ActuatorName("name");
        ActuatorId actuatorId = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId = new ActuatorTypeId("type");
        Actuator actuator = new ActuatorFactory().createActuator(deviceId, actuatorModel, actuatorName, actuatorId, actuatorTypeId);

        when(actuatorService.findAllByDeviceId(anyLong())).thenReturn(List.of(actuator));

        String jsonResponse = """
                [
                    {
                        "name": "name",
                        "model": "BlindRollerActuator",
                        "actuatorId": 1,
                        "deviceId": 1,
                        "actuatorType": "type",
                        "lowerLimit": 0,
                        "upperLimit": 0,
                        "lowerLimitFractional": 0.0,
                        "upperLimitFractional": 0.0,
                        "currentValue": 100,
                        "links": [
                            {
                                "rel": "self",
                                "href": "http://localhost/api/v1/actuators/1"
                            },
                            {
                                "rel": "activityLogs",
                                "href": "http://localhost/api/v1/actuator-activity-logs?actuator-id=1",
                                "type": "GET"
                            },
                            {
                                "rel": "device",
                                "href": "http://localhost/api/v1/devices/1",
                                "type": "GET"
                            }
                        ]
                    }
                ]
                """;

        // Act and Assert
        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuators?device-id=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }
}
