package SmartHome.controller.rest_controllers;

import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.valueObjects.ActivityLogId;
import SmartHome.domain.valueObjects.ActuatorId;
import SmartHome.domain.valueObjects.Measurement;
import SmartHome.domain.valueObjects.TimeStamp;
import SmartHome.service.ActuatorActivityLogService;
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

@WebMvcTest(ActuatorActivityLogController.class)
@AutoConfigureMockMvc
class ActuatorActivityLogControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ActuatorActivityLogService actuatorActivityLogService;

    @Test
    void getActuatorActivityLogs() throws Exception
    {
        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse("2024-05-04T23:44:28.513805300Z"));
        ActuatorId actuatorId = new ActuatorId(1L);
        Measurement measurement = new Measurement("1.0");
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, measurement);

        String jsonResponse = """
                [
                    {
                        "actuatorActivityLogId": 1,
                        "actuatorId": 1,
                        "timeStamp": "2024-05-04T23:44:28.513805300Z",
                        "measurement": "1.0",
                        "links": [
                                    {
                                        "rel": "self",
                                        "href": "http://localhost/api/v1/actuator-activity-logs/1"
                                    }
                                ]
                    }
                ]
                """;

        when(actuatorActivityLogService.findAll()).thenReturn(List.of(actuatorActivityLog));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuator-activity-logs"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getActuatorActivityLogsById_findsOne() throws Exception
    {
        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse("2024-05-04T23:44:28.513805300Z"));
        ActuatorId actuatorId = new ActuatorId(1L);
        Measurement measurement = new Measurement("1.0");
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, measurement);

        String jsonResponse = """
                    {
                        "actuatorActivityLogId": 1,
                        "actuatorId": 1,
                        "timeStamp": "2024-05-04T23:44:28.513805300Z",
                        "measurement": "1.0",
                        "_links": {
                                "self": {
                                    "href": "http://localhost/api/v1/actuator-activity-logs"
                                }
                            }
                    }
                """;

        when(actuatorActivityLogService.findById(1L)).thenReturn(actuatorActivityLog);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuator-activity-logs/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getActuatorActivityLogsById_failBadIdInput() throws Exception
    {
        String jsonResponse = """
                    {
                        "error": "Bad id input",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuator-activity-logs"
                            }
                        }
                    }
                """;

        when(actuatorActivityLogService.findById(-1L)).thenThrow(new IllegalArgumentException("Bad id input"));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuator-activity-logs/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getActuatorActivityLogsById_failNotFound() throws Exception
    {
        String jsonResponse = """
                    {
                        "error": "Not found by id.",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuator-activity-logs"
                            }
                        }
                    }
                """;

        when(actuatorActivityLogService.findById(1L)).thenThrow(new EntityNotFoundException("Not found by id."));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuator-activity-logs/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addActuatorActivityLog() throws Exception
    {
        ActivityLogId activityLogId = new ActivityLogId(1L);
        TimeStamp timeStamp = new TimeStamp(ZonedDateTime.parse("2024-05-04T23:44:28.513805300Z"));
        ActuatorId actuatorId = new ActuatorId(1L);
        Measurement measurement = new Measurement("1.0");
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(activityLogId, actuatorId, timeStamp, measurement);

        when(actuatorActivityLogService.add(any(), any(), any(), any())).thenReturn(actuatorActivityLog);

        String jsonRequest = """
                {
                    "actuatorActivityLogId": 1,
                    "actuatorId": 1,
                    "timeStamp": "2024-05-04T23:44:28.513805300Z",
                    "measurement": "1.0"
                }
                """;

        String jsonResponse = """
                    {
                        "actuatorActivityLogId": 1,
                        "actuatorId": 1,
                        "timeStamp": "2024-05-04T23:44:28.513805300Z",
                        "measurement": "1.0",
                        "_links": {
                                "self": {
                                    "href": "http://localhost/api/v1/actuator-activity-logs/1"
                                }
                            }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/actuator-activity-logs")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addActuatorActivityLog_failActuatorIdDontExist() throws Exception
    {
        when(actuatorActivityLogService.add(any(), any(), any(), any()))
                .thenThrow(new EntityNotFoundException("ActuatorId don't exist"));

        String jsonRequest = """
                {
                    "actuatorActivityLogId": 1,
                    "actuatorId": 1,
                    "timeStamp": "2024-05-04T23:44:28.513805300Z",
                    "measurement": "1.0"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "ActuatorId don't exist",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuator-activity-logs"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/actuator-activity-logs")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addActuatorActivityLog_failDuplicatedLogId() throws Exception
    {
        when(actuatorActivityLogService.add(any(), any(), any(), any()))
                .thenThrow(new DuplicateKeyException("Log id already exists"));

        String jsonRequest = """
                {
                    "actuatorActivityLogId": 1,
                    "actuatorId": 1,
                    "timeStamp": "2024-05-04T23:44:28.513805300Z",
                    "measurement": "1.0"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "Log id already exists",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuator-activity-logs"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/actuator-activity-logs")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addActuatorActivityLog_failMissingJsonInputField() throws Exception
    {
        when(actuatorActivityLogService.add(any(), any(), any(), any()))
              .thenThrow(new EntityNotFoundException("ActuatorId doesn't exist"));

        String jsonRequest = """
                {
                    "actuatorActivityLogId": 1,
                    "timeStamp": "2024-05-04T23:44:28.513805300Z",
                    "measurement": "1.0"
                }
                """;

        String jsonResponse = """
                    {
                      "error": "ActuatorId doesn't exist",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuator-activity-logs"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/actuator-activity-logs")
                        .contentType("application/json")
                        .content(jsonRequest))
              .andExpect(status().isNotFound())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addActuatorActivityLog_failInvalidIdField() throws Exception
    {
        when(actuatorActivityLogService.add(any(), any(), any(), any()))
                .thenThrow(new DuplicateKeyException("Log id already exists"));

        String jsonRequest = """
                {
                    "actuatorActivityLogId": -1,
                    "actuatorId": 1,
                    "timeStamp": "2024-05-04T23:44:28.513805300Z",
                    "measurement": "1.0"
                }
                """;

        String jsonResponse = """
                    {
                        "error": "Id cannot be smaller than 1",
                        "_links": {
                            "self": {
                                "href": "http://localhost/api/v1/actuator-activity-logs"
                            }
                        }
                    }
                """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/actuator-activity-logs")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void findAllActuatorActivityLogsBySensorId() throws Exception {
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(new ActivityLogId(1), new ActuatorId(1),
                new TimeStamp(ZonedDateTime.parse("2024-04-24T17:00:00.00Z")), new Measurement("1.0"));
        when(actuatorActivityLogService.findAllByActuatorId(1L))
                .thenReturn(List.of(actuatorActivityLog));

        String jsonResponse = """
                    [
                      {
                        "actuatorActivityLogId":1,
                        "actuatorId":1,
                        "timeStamp":"2024-04-24T17:00Z",
                        "measurement":"1.0",
                        "links":
                          [
                            {
                              "rel":"self",
                              "href":"http://localhost/api/v1/actuator-activity-logs/1"
                            }
                          ]
                      }
                    ]
                """;

        ResultActions resultActions = mockMvc.perform(get("/api/v1/actuator-activity-logs?actuator-id=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }
}