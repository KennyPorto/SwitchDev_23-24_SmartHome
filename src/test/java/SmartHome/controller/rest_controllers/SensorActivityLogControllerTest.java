package SmartHome.controller.rest_controllers;

import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.SensorId;
import SmartHome.domain.valueObjects.TimeStamp;
import SmartHome.service.SensorActivityLogService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorActivityLogController.class)
@AutoConfigureMockMvc
class SensorActivityLogControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    SensorActivityLogService sensorActivityLogService;

    @Test
    void getSensorActivityLogs() throws Exception {
        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse("2024-05-04T23:44:28.513805300Z"));
        SensorId sensorId = new SensorId(1L);
        Measurement measurement = new Measurement("1.0");
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, measurement);

        String jsonResponse = """
                [
                    {
                        "sensorActivityLogId": 1,
                        "sensorId": 1,
                        "timeStamp": "2024-05-04T23:44:28.513805300Z",
                        "measurement": "1.0",
                        "links": [
                                    {
                                        "rel": "self",
                                        "href": "http://localhost/api/v1/sensor-activity-logs/1"
                                    },
                                    {
                                        "rel": "sensor",
                                        "href": "http://localhost/api/v1/sensors/1"
                                    }
                                ]
                    }
                ]
                """;

        when(sensorActivityLogService.findAll()).thenReturn(List.of(sensorActivityLog));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensor-activity-logs"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getSensorActivityLogsById_findsOne() throws Exception {
        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse("2024-05-04T23:44:28.513805300Z"));
        SensorId sensorId = new SensorId(1L);
        Measurement measurement = new Measurement("1.0");
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, measurement);

        String jsonResponse = """
                    {
                        "sensorActivityLogId": 1,
                        "sensorId": 1,
                        "timeStamp": "2024-05-04T23:44:28.513805300Z",
                        "measurement": "1.0",
                        "_links": {
                                "self": {
                                    "href": "http://localhost/api/v1/sensor-activity-logs"
                                }
                            }
                    }
                """;

        when(sensorActivityLogService.findById(1L)).thenReturn(sensorActivityLog);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensor-activity-logs/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getSensorActivityLogsById_failBadIdInput() throws Exception {
        String jsonResponse = """
                    {
                        "error": "Bad id input",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensor-activity-logs"
                            }
                        }
                    }
                """;

        when(sensorActivityLogService.findById(-1L)).thenThrow(new IllegalArgumentException("Bad id input"));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensor-activity-logs/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getSensorActivityLogsById_failNotFound() throws Exception {
        String jsonResponse = """
                    {
                        "error": "Not found by id.",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensor-activity-logs"
                            }
                        }
                    }
                """;

        when(sensorActivityLogService.findById(1L)).thenThrow(new EntityNotFoundException("Not found by id."));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensor-activity-logs/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addSensorActivityLog() throws Exception {
        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse("2024-05-04T23:44:28.513805300Z"));
        SensorId sensorId = new SensorId(1L);
        Measurement measurement = new Measurement("1.0");
        SensorActivityLog sensorActivityLog = new SensorActivityLog(activityLogId, timeStamp, sensorId, measurement);

        when(sensorActivityLogService.add(any(), any(), any(), any())).thenReturn(sensorActivityLog);

        String jsonRequest = """
                {
                    "sensorActivityLogId": 1,
                    "sensorId": 1,
                    "timeStamp": "2024-05-04T23:44:28.513805300Z",
                    "measurement": "1.0"
                }
                """;

        String jsonResponse = """
                    {
                        "sensorActivityLogId": 1,
                        "sensorId": 1,
                        "timeStamp": "2024-05-04T23:44:28.513805300Z",
                        "measurement": "1.0",
                        "_links": {
                                "self": {
                                    "href": "http://localhost/api/v1/sensor-activity-logs/1"
                                }
                            }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensor-activity-logs")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addSensorActivityLog_failSensorIdDontExist() throws Exception {
        when(sensorActivityLogService.add(any(), any(), any(), any()))
                .thenThrow(new EntityNotFoundException("SensorId don't exist"));

        String jsonRequest = """
                {
                    "sensorActivityLogId": 1,
                    "sensorId": 1,
                    "timeStamp": "2024-05-04T23:44:28.513805300Z",
                    "measurement": "1.0"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "SensorId don't exist",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensor-activity-logs"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensor-activity-logs")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addSensorActivityLog_failDuplicatedLogId() throws Exception {
        when(sensorActivityLogService.add(any(), any(), any(), any()))
                .thenThrow(new DuplicateKeyException("Log id already exists"));

        String jsonRequest = """
                {
                    "sensorActivityLogId": 1,
                    "sensorId": 1,
                    "timeStamp": "2024-05-04T23:44:28.513805300Z",
                    "measurement": "1.0"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "Log id already exists",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensor-activity-logs"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensor-activity-logs")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addSensorActivityLog_failMissingJsonInputField() throws Exception {
        when(sensorActivityLogService.add(any(), any(), any(), any()))
                .thenThrow(new DuplicateKeyException("Log id already exists"));

        String jsonRequest = """
                {
                    "sensorActivityLogId": 1,
                    "timeStamp": "2024-05-04T23:44:28.513805300Z",
                    "measurement": "1.0"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "Sensor ID cannot be smaller than 0",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensor-activity-logs"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensor-activity-logs")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addSensorActivityLog_failInvalidIdField() throws Exception {
        when(sensorActivityLogService.add(any(), any(), any(), any()))
                .thenThrow(new DuplicateKeyException("Log id already exists"));

        String jsonRequest = """
                {
                    "sensorActivityLogId": -1,
                    "sensorId": 1,
                    "timeStamp": "2024-05-04T23:44:28.513805300Z",
                    "measurement": "1.0"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "Id cannot be smaller than 1",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/sensor-activity-logs"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/sensor-activity-logs")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void findAllSensorActivityLogsBySensorId() throws Exception {
        SensorActivityLog sensorActivityLog = new SensorActivityLog(new ActivityLogId(1),
                new TimeStamp(ZonedDateTime.parse("2024-04-25T17:00:00.00Z")), new SensorId(1),
                new Measurement("1.0"));

        when(sensorActivityLogService.findAllBySensorId(1)).thenReturn(List.of(sensorActivityLog));

        String jsonResponse = """
                [
                  {
                    "sensorActivityLogId":1,
                    "sensorId":1,
                    "timeStamp":"2024-04-25T17:00Z",
                    "measurement":"1.0",
                    "links":
                      [
                        {
                          "rel":"self",
                          "href":"http://localhost/api/v1/sensor-activity-logs/1",
                          "type":"GET"
                        },
                        {
                          "rel":"sensor",
                          "href":"http://localhost/api/v1/sensors/1"
                        }
                      ]
                  }
                ]
                """;

        ResultActions resultActions = mockMvc.perform(get("/api/v1/sensor-activity-logs?sensor-id=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), true);
    }
}