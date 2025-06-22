package SmartHome.controller.rest_controllers;

import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.sensors.implementation.HumiditySensor;
import SmartHome.domain.valueObjects.*;
import SmartHome.service.SensorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
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

@WebMvcTest(SensorController.class)
@AutoConfigureMockMvc
public class SensorControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    SensorService sensorService;

    private SensorModel sensorModel;

    @BeforeEach
    void setUp() {
        sensorModel = new SensorModel("HumiditySensor");
    }

    @Test
    void getSensors() throws Exception {

        SensorName sensorName = new SensorName("SensorName");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("STypeId");

        Sensor sensor = new HumiditySensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        String jsonResponse = """
                [
                     {
                         "name": "SensorName",
                         "model": "HumiditySensor",
                         "sensorId": 1,
                         "deviceId": 1,
                         "sensorType": "STypeId",
                         "links": [
                             {
                                 "rel": "self",
                                 "href": "http://localhost/api/v1/sensors/1",
                                 "type": "GET"
                             },
                             {
                                 "rel": "device",
                                 "href": "http://localhost/api/v1/devices/1",
                                 "type": "GET"
                             },
                             {
                                 "rel": "activityLogs",
                                 "href": "http://localhost/api/v1/sensor-activity-logs?sensor-id=1",
                                 "type": "GET"
                             }
                         ]
                     }
                 ]
                """;

        when(sensorService.findAll()).thenReturn(List.of(sensor));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensors"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getSensorById() throws Exception {

        SensorName sensorName = new SensorName("SensorName");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("STypeId");

        Sensor sensor = new HumiditySensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        String jsonResponse = """
                {
                    "name": "SensorName",
                    "model": "HumiditySensor",
                    "sensorId": 1,
                    "deviceId": 1,
                    "sensorType": "STypeId",
                    "_links": {
                        "self": {
                            "href": "http://localhost/api/v1/sensors",
                            "type": "GET"
                        },
                        "device": {
                            "href": "http://localhost/api/v1/devices/1",
                            "type": "GET"
                        },
                        "activityLogs": {
                            "href": "http://localhost/api/v1/sensor-activity-logs?sensor-id=1",
                            "type": "GET"
                        }
                    }
                }
                """;

        when(sensorService.findById(1L)).thenReturn(sensor);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensors/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getSensorById_failBadIdInput() throws Exception {
        String jsonResponse = """
                    {
                        "error": "Bad id input",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensors"
                            }
                        }
                    }
                """;

        when(sensorService.findById(-1L)).thenThrow(new IllegalArgumentException("Bad id input"));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensors/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getSensorById_failNotFound() throws Exception {
        String jsonResponse = """
                    {
                        "error": "Not found by id.",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensors"
                            }
                        }
                    }
                """;

        when(sensorService.findById(1L)).thenThrow(new EntityNotFoundException("Not found by id."));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensors/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addSensor() throws Exception {

        SensorName sensorName = new SensorName("SensorName");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("STypeId");

        Sensor sensor = new HumiditySensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        when(sensorService.add(any(), any(), any(), any(), any())).thenReturn(sensor);

        String jsonRequest = """
                {
                    "name": "SensorName",
                        "model": "HumiditySensor",
                        "sensorId": 1,
                        "deviceId": 1,
                        "sensorType": "STypeId"
                }
                """;

        String jsonResponse = """
                    {
                        "name": "SensorName",
                        "model": "HumiditySensor",
                        "sensorId": 1,
                        "deviceId": 1,
                        "sensorType": "STypeId",
                        "_links": {
                            "self": 
                                {
                                    "href": "http://localhost/api/v1/sensors/1"
                                },
                             "device":   
                                {
                                    "href": "http://localhost/api/v1/devices/1"
                                }
                            
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensors")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addSensor_failBadSensorModel() throws Exception {
        when(sensorService.add(any(), any(), any(), any(), any())).thenThrow(new NullPointerException("Cannot invoke \"SmartHome.domain.sensors.Sensor.getName()\" because \"sensor\" is null"));

        String jsonRequest = """
                {
                    "name": "SensorName",
                        "model": "badModel",
                        "sensorId": 1,
                        "deviceId": 1,
                        "sensorType": "STypeId"
                }
                """;

        String jsonResponse = """
                    {
                      "error":"Cannot invoke \\"SmartHome.domain.sensors.Sensor.getName()\\" because \\"sensor\\" is null",
                      "_links":
                        {
                          "self":
                            {
                              "href":"http://localhost/api/v1/sensors"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensors")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addSensor_failDeviceIdDontExist() throws Exception {
        when(sensorService.add(any(), any(), any(), any(), any()))
                .thenThrow(new EntityNotFoundException("DeviceId don't exist"));

        String jsonRequest = """
                {
                    "name": "SensorName",
                        "model": "HumiditySensor",
                        "sensorId": 1,
                        "deviceId": 1,
                        "sensorType": "STypeId"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "DeviceId don't exist",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensors"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensors")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addSensor_failDuplicatedSensorId() throws Exception {
        when(sensorService.add(any(), any(), any(), any(), any()))
                .thenThrow(new DuplicateKeyException("Sensor id already exists"));

        String jsonRequest = """
                {
                    "name": "SensorName",
                        "model": "HumiditySensor",
                        "sensorId": 1,
                        "deviceId": 1,
                        "sensorType": "STypeId"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "Sensor id already exists",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensors"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensors")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addSensors_failInvalidIdField() throws Exception {
        when(sensorService.add(any(), any(), any(), any(), any()))
                .thenThrow(new DuplicateKeyException("Sensor id already exists"));

        String jsonRequest = """
                {
                    "name": "SensorName",
                        "model": "HumiditySensor",
                        "sensorId": -1,
                        "deviceId": 1,
                        "sensorType": "STypeId"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "Sensor ID cannot be smaller than 0",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensors"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensors")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void findAllSensorsByDevice() throws Exception {
        // Arrange
        SensorName sensorName = new SensorName("SensorName");
        SensorId sensorId = new SensorId(1);
        DeviceID deviceID = new DeviceID(1);
        SensorTypeId sensorTypeId = new SensorTypeId("STypeId");

        Sensor sensor = new HumiditySensor(deviceID, sensorName, sensorId, sensorTypeId, sensorModel);

        when(sensorService.findAllByDeviceId(anyLong())).thenReturn(List.of(sensor));

        String jsonResponse = """
            
                [
                 {
                     "name": "SensorName",
                     "model": "HumiditySensor",
                     "sensorId": 1,
                     "deviceId": 1,
                     "sensorType": "STypeId",
                     "links": [
                         {
                             "rel": "self",
                             "href": "http://localhost/api/v1/sensors/1",
                             "type": "GET"
                         },
                         {
                             "rel": "device",
                             "href": "http://localhost/api/v1/devices/1",
                             "type": "GET"
                         },
                         {
                             "rel": "activityLogs",
                             "href": "http://localhost/api/v1/sensor-activity-logs?sensor-id=1",
                             "type": "GET"
                         }
                     ]
                 }
             ]
            """;

        // Act and Assert
        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensors?device-id=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }
}
