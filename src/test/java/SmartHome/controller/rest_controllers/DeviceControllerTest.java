package SmartHome.controller.rest_controllers;

import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.device.Device;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.DeviceDTO;
import SmartHome.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc
class DeviceControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    DeviceService deviceService;

    @MockBean
    DeviceFactory deviceFactory;

    @MockBean
    DeviceRepository repository;

    @MockBean
    DeviceLogService deviceLogService;

    @MockBean
    EnvironmentalTemperatureService environmentalTemperatureService;

    @MockBean
    DevFuncService devFuncService;

    @MockBean
    ActuatorActService actuatorActService;

    @Test
    void getDevices() throws Exception
    {
        // Arrange
        DeviceName deviceName = new DeviceName("Heater");
        DeviceID deviceID = new DeviceID(1L);
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        RoomId roomId = new RoomId(1L);
        DeviceStatus deviceStatus = new DeviceStatus(true);

        Device device = mock(Device.class);
        when(device.getDeviceStatus()).thenReturn(deviceStatus);
        when(device.getRoomId()).thenReturn(roomId);
        when(device.identity()).thenReturn(deviceID);
        when(device.getName()).thenReturn(deviceName);
        when(device.getModel()).thenReturn(deviceModel);

        String jsonResponse = """
              [
                  {
                      "roomId": 1,
                      "deviceModel": "Xiaomi",
                      "deviceID": 1,
                      "deviceName": "Heater",
                      "links": [
                                  {
                                      "rel": "self",
                                      "href": "http://localhost/api/v1/devices/1"
                                  }
                              ]
                  }
              ]
              """;

        when(deviceService.findAll()).thenReturn(List.of(device));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/devices"))
              .andExpect(status().isOk());

        // Assert
        resultActions.andExpect(content().json(jsonResponse));
    }

    @Test
    void getDeviceId_findsOne() throws Exception
    {
        // Arrange
        DeviceName deviceName = new DeviceName("Heater");
        DeviceID deviceID = new DeviceID(1L);
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        RoomId roomId = new RoomId(1L);
        DeviceStatus deviceStatus = new DeviceStatus(true);

        Device device = mock(Device.class);
        when(device.getDeviceStatus()).thenReturn(deviceStatus);
        when(device.getRoomId()).thenReturn(roomId);
        when(device.identity()).thenReturn(deviceID);
        when(device.getName()).thenReturn(deviceName);
        when(device.getModel()).thenReturn(deviceModel);

        String jsonResponse = """
              {
                  "roomId": 1,
                  "deviceModel": "Xiaomi",
                  "deviceID": 1,
                  "deviceName": "Heater",
                  "_links": {
                              "self": {
                                  "href": "http://localhost/api/v1/devices"
                              }
                          }
              }
              """;

        when(deviceService.findById(anyLong())).thenReturn(device);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/devices/1"))
              .andExpect(status().isOk());

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getDeviceById_failBadIdInput() throws Exception
    {
        String jsonResponse = """
                  {
                      "error": "Bad id input",
                      "_links": {
                          "self": {
                              "href": "http://localhost/api/v1/devices"
                          }
                      }
                  }
              """;

        when(deviceService.findById(-1L)).thenThrow(new IllegalArgumentException("Bad id input"));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/devices/-1"))
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getDeviceById_failNotFound() throws Exception
    {
        String jsonResponse = """
                  {
                      "error": "Not found by id.",
                      "_links": {
                          "self": {
                              "href": "http://localhost/api/v1/devices"
                          }
                      }
                  }
              """;

        when(deviceService.findById(1L)).thenThrow(new EntityNotFoundException("Not found by id."));

        ResultActions resultActions = mockMvc.perform(get("/api/v1/devices/1"))
              .andExpect(status().isNotFound())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addDevice() throws Exception
    {
        // Arrange
        DeviceName deviceName = new DeviceName("Heater");
        DeviceID deviceID = new DeviceID(1L);
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        RoomId roomId = new RoomId(1L);
        DeviceStatus deviceStatus = new DeviceStatus(true);

        Device device = mock(Device.class);
        when(device.getDeviceStatus()).thenReturn(deviceStatus);
        when(device.getRoomId()).thenReturn(roomId);
        when(device.identity()).thenReturn(deviceID);
        when(device.getName()).thenReturn(deviceName);
        when(device.getModel()).thenReturn(deviceModel);
        when(deviceService.add(any(), any(), any(), any())).thenReturn(device);

        String jsonRequest = """
              {
                   "deviceID": 1,
                   "roomId": 1,
                   "deviceName": "Heater",
                   "deviceModel": "Xiaomi"
                 }
              """;

        String jsonResponse = """
              {
                  "roomId": 1,
                  "deviceModel": "Xiaomi",
                  "deviceID": 1,
                  "deviceName": "Heater",
                  "_links": {
                               "self": {
                                  "href": "http://localhost/api/v1/devices/1"
                              }
                          }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(
                    post("/api/v1/devices")
                          .contentType("application/json")
                          .content(jsonRequest)
              )
              .andExpect(status().isCreated())
              .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void findAllDevicesByRoom() throws Exception
    {
        // Arrange
        DeviceName deviceName = new DeviceName("Heater");
        DeviceID deviceID = new DeviceID(1L);
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        RoomId roomId = new RoomId(1L);
        DeviceStatus deviceStatus = new DeviceStatus(true);

        Device device = mock(Device.class);
        when(device.getDeviceStatus()).thenReturn(deviceStatus);
        when(device.getRoomId()).thenReturn(roomId);
        when(device.identity()).thenReturn(deviceID);
        when(device.getName()).thenReturn(deviceName);
        when(device.getModel()).thenReturn(deviceModel);

        String jsonResponse = """
              [
                  {
                      "roomId": 1,
                      "deviceModel": "Xiaomi",
                      "deviceID": 1,
                      "deviceName": "Heater"
                  }
              ]
              """;

        when(deviceService.findAllByRoomId(anyLong())).thenReturn(List.of(device));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/devices?room-id=1"))
              .andExpect(status().isOk());

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addDevice_failDeviceIdDontExist() throws Exception
    {
        when(deviceService.add(any(), any(), any(), any()))
              .thenThrow(new EntityNotFoundException("DeviceId don't exist"));

        String jsonRequest = """
              {
                  "deviceID": 1,
                  "roomId": 1,
                  "deviceName": "Heater",
                  "deviceModel": "Xiaomi"
              }
              """;

        String jsonResponse = """
              {
                  "error": "DeviceId don't exist",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/devices")
                    .contentType("application/json")
                    .content(jsonRequest))
              .andExpect(status().isNotFound())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addDevice_failDuplicatedDeviceId() throws Exception
    {
        when(deviceService.add(any(), any(), any(), any()))
              .thenThrow(new DuplicateKeyException("Device id already exists"));

        String jsonRequest = """
              {
                  "deviceID": 1,
                  "roomId": 1,
                  "deviceName": "Heater",
                  "deviceModel": "Xiaomi"
              }
              """;

        String jsonResponse = """
              {
                  "error": "Device id already exists",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/devices")
                    .contentType("application/json")
                    .content(jsonRequest))
              .andExpect(status().isConflict())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addDevice_failMissingJsonInputField() throws Exception
    {
        when(deviceService.add(any(), any(), any(), any()))
              .thenThrow(new IllegalArgumentException("Device ID cannot be smaller than 1"));

        String jsonRequest = """
              {
                  "deviceID": 1,
                  "deviceName": "Heater",
                  "deviceModel": "Xiaomi"
              }
              """;

        String jsonResponse = """
              {
                  "error": "Device ID cannot be smaller than 1",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/devices")
                    .contentType("application/json")
                    .content(jsonRequest))
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void addDevice_failInvalidIdField() throws Exception
    {
        when(deviceService.add(any(), any(), any(), any()))
              .thenThrow(new IllegalArgumentException("Id cannot be smaller than 1"));

        String jsonRequest = """
              {
                  "deviceID": -1,
                  "roomId": 1,
                  "deviceName": "Heater",
                  "deviceModel": "Xiaomi"
              }
              """;

        String jsonResponse = """
              {
                  "error": "Device ID cannot be smaller than 0",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        ResultActions resultActions = mockMvc.perform(post("/api/v1/devices")
                    .contentType("application/json")
                    .content(jsonRequest))
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void shouldDeactivateDevice() throws Exception
    {
        // Arrange
        DeviceName deviceName = new DeviceName("Heater");
        DeviceID deviceID = new DeviceID(1L);
        DeviceModel deviceModel = new DeviceModel("Xiaomi");
        RoomId roomId = new RoomId(1L);
        DeviceStatus deviceStatus = new DeviceStatus(false);

        Device device = mock(Device.class);
        when(device.getDeviceStatus()).thenReturn(deviceStatus);
        when(device.getRoomId()).thenReturn(roomId);
        when(device.identity()).thenReturn(deviceID);
        when(device.getName()).thenReturn(deviceName);
        when(device.getModel()).thenReturn(deviceModel);
        long deviceId = 1L;
        when(deviceService.deactivateDevice(deviceId)).thenReturn(device);

        String jsonResponse = """
              {
                  "roomId": 1,
                  "deviceModel": "Xiaomi",
                  "deviceID": 1,
                  "deviceName": "Heater",
                  "isActive": false,
                  "_links": {
                               "self": {
                                  "href": "http://localhost/api/v1/devices/1"
                              }
                          }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(patch("/api/v1/devices/1/deactivate"))
              .andExpect(status().isOk())
              .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void shouldNotDeactivateDevice() throws Exception
    {
        // Arrange
        long deviceId = 1L;
        when(deviceService.deactivateDevice(deviceId)).thenThrow(new IllegalArgumentException("Device is not activated"));

        String jsonResponse = """
              {
                  "error": "Device is not activated",
                  "_links": {
                               "self": {
                                  "href": "http://localhost/api/v1/devices"
                              }
                          }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(patch("/api/v1/devices/1/deactivate"))
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionDeactivateDevice() throws Exception
    {
        // Arrange
        long deviceId = 1L;
        String errorMessage = "Invalid device id";

        when(deviceService.deactivateDevice(deviceId)).thenThrow(new IllegalArgumentException(errorMessage));

        String jsonResponse = """
              {
                  "error": "Invalid device id",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(patch("/api/v1/devices/1/deactivate"));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void shouldThrowDataIntegrityViolationExceptionDeactivateDevice() throws Exception
    {
        // Arrange
        long deviceId = 1L;
        String errorMessage = "Data integrity violation";

        when(deviceService.deactivateDevice(deviceId)).thenThrow(new DataIntegrityViolationException(errorMessage));

        String jsonResponse = """
              {
                  "error": "Data integrity violation",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(patch("/api/v1/devices/1/deactivate"));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getAllLogsFromDevice() throws Exception
    {
        // Arrange
        long deviceId = 1L;
        DeviceID deviceIdObj = new DeviceID(deviceId);
        String start = "2024-05-03T00:00:00Z";
        String end = "2024-05-05T00:00:00Z";
        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(new ActivityLogId(1L), new ActuatorId(1L),
              new TimeStamp(ZonedDateTime.parse("2024-05-04T23:44:28.513805300Z")), new Measurement("1.0"));
        SensorActivityLog sensorActivityLog = new SensorActivityLog(new ActivityLogId(1L),
              new TimeStamp(ZonedDateTime.parse("2024-05-04T23:44:28.513805300Z")), new SensorId(1L),
              new Measurement("1.0"));

        Iterable<ActuatorActivityLog> actuatorActivityLogs = List.of(actuatorActivityLog);
        Iterable<SensorActivityLog> sensorActivityLogs = List.of(sensorActivityLog);

        when(deviceLogService.getAllLogsFromDevice(deviceIdObj, ZonedDateTime.parse(start),
              ZonedDateTime.parse(end))).thenReturn(Pair.of(actuatorActivityLogs, sensorActivityLogs));

        String jsonResponse = """
              {
                "actuatorLogs":
                  [
                    {
                      "actuatorActivityLogId":1,
                      "actuatorId":1,
                      "timeStamp":"2024-05-04T23:44:28.513805300Z",
                      "measurement":"1.0",
                      "links":
                        [
                          {
                            "rel":"self",
                            "href":"http://localhost/api/v1/actuator-activity-logs/1"
                          }
                        ]
                    }
                  ],
                "sensorLogs":
                  [
                    {
                      "sensorActivityLogId":1,
                      "sensorId":1,
                      "timeStamp":"2024-05-04T23:44:28.513805300Z",
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
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/devices/1/logs" +
                    "?start=2024-05-03T00:00:00Z" +
                    "&end=2024-05-05T00:00:00Z"))
              .andExpect(status().isOk())
              .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), true);
    }

    @Test
    void getAllLogsFromDevice_nonExistingRoomId() throws Exception
    {
        // Arrange
        long deviceId = 1L;
        DeviceID deviceIdObj = new DeviceID(deviceId);
        String start = "2024-05-03T00:00:00Z";
        String end = "2024-05-05T00:00:00Z";

        when(deviceLogService.getAllLogsFromDevice(deviceIdObj, ZonedDateTime.parse(start),
              ZonedDateTime.parse(end)))
              .thenThrow(new IllegalArgumentException("Device not found or Device don't exist in room"));

        String jsonResponse = """
              {
                   "error": "Device not found or Device don't exist in room",
                   "_links": {
                       "self": {
                           "href": "http://localhost/api/v1/devices"
                       }
                   }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/devices/1/logs" +
                    "?room-id=2" +
                    "&start=2024-05-03T00:00:00Z" +
                    "&end=2024-05-05T00:00:00Z"))
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), true);
    }

    @Test
    void getAllLogsFromDevice_nonExistingDeviceId() throws Exception
    {
        // Arrange
        long deviceId = 2L;
        DeviceID deviceIdObj = new DeviceID(deviceId);
        String start = "2024-05-03T00:00:00Z";
        String end = "2024-05-05T00:00:00Z";

        when(deviceLogService.getAllLogsFromDevice(deviceIdObj, ZonedDateTime.parse(start),
              ZonedDateTime.parse(end)))
              .thenThrow(new IllegalArgumentException("Device not found or Device don't exist in room"));

        String jsonResponse = """
              {
                   "error": "Device not found or Device don't exist in room",
                   "_links": {
                       "self": {
                           "href": "http://localhost/api/v1/devices"
                       }
                   }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(
                    get("/api/v1/devices/2/logs" +
                          "?room-id=1" +
                          "&start=2024-05-03T00:00:00.00Z" +
                          "&end=2024-05-05T00:00:00.00Z"))
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), true);
    }

    @Test
    void getMaximumTemperatureDifferenceTest() throws Exception
    {
        // Arrange
        long deviceId = 1L;
        String startDate = "2022-01-01T00:00:00Z";
        String endDate = "2022-01-02T00:00:00Z";
        long delta = 1000L;
        double expectedMaxTempDiff = 10.0;

        when(environmentalTemperatureService.getMaximumTemperatureDifference(deviceId, startDate, endDate, delta))
              .thenReturn(expectedMaxTempDiff);

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/devices/" + deviceId + "/start=" + startDate + "&end=" + endDate + "&delta=" + delta))
              .andExpect(status().isOk());

        // Assert
        String jsonResponse = """
              {
                  "tempDiff": 10.0,
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getMaximumTemperatureDifferenceTest_failInvalidInput() throws Exception
    {
        // Arrange
        long deviceId = 1L;
        String startDate = "2022-01-01T00:00:00Z";
        String endDate = "2022-01-02T00:00:00Z";
        long delta = 1000L;

        when(environmentalTemperatureService.getMaximumTemperatureDifference(deviceId, startDate, endDate, delta))
              .thenThrow(new IllegalArgumentException("Invalid input"));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/devices/" + deviceId + "/start=" + startDate + "&end=" + endDate + "&delta=" + delta))
              .andExpect(status().isBadRequest());

        // Assert
        String jsonResponse = """
              {
                  "error": "Invalid input",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void closeBlindRollerTest() throws Exception
    {
        // Arrange
        long deviceId = 1L;

        ActuatorActivityLog actuatorActivityLog = new ActuatorActivityLog(new ActivityLogId(1L), new ActuatorId(1L),
              new TimeStamp(ZonedDateTime.parse("2024-05-04T23:44:28.513805300Z")), new Measurement("1.0"));

        when(actuatorActService.closeBlindRoller(any(DeviceID.class), any(Measurement.class),
              any(TimeStamp.class), any(ActuatorId.class)))
              .thenReturn(actuatorActivityLog);

        String jsonRequest = """
              {
                  "actuatorId": 1,
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
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(
                    patch("/api/v1/devices/" + deviceId + "/close-blinder")
                          .contentType("application/json")
                          .content(jsonRequest)
              )
              .andExpect(status().isOk())
              .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void closeBlindRollerTest_InvalidDeviceId() throws Exception
    {
        // Arrange
        long deviceId = 6;

        when(actuatorActService.closeBlindRoller(any(DeviceID.class), any(Measurement.class),
              any(TimeStamp.class), any(ActuatorId.class)))
              .thenThrow(new IllegalArgumentException("A device with this device Id doesn't exist"));

        String jsonRequest = """
              {

                  "measurement": "6",
                  "timeStamp": "2024-05-04T23:44:28.513805300Z",
                  "actuatorId": 1
              }
              """;

        // Assert
        String jsonResponse = """
              {
                  "error": "A device with this device Id doesn't exist",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(
                    patch("/api/v1/devices/" + deviceId + "/close-blinder")
                          .contentType("application/json")
                          .content(jsonRequest)
              )
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);

    }

    @Test
    void closeBlindRollerTest_InvalidPercentage() throws Exception
    {
        // Arrange
        long deviceId = 1L;

        String jsonRequest = """
              {
                  "measurement": "101",
                  "timeStamp": "2024-05-04T23:44:28.513805300Z",
                  "actuatorId": 1
              }
              """;

        when(actuatorActService.closeBlindRoller(any(DeviceID.class), any(Measurement.class),
              any(TimeStamp.class), any(ActuatorId.class)))
              .thenThrow(new IllegalArgumentException("The desired closed percentage must be between 0 and 100"));

        // Assert
        String jsonResponse = """
              {
                  "error": "The desired closed percentage must be between 0 and 100",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(
                    patch("/api/v1/devices/" + deviceId + "/close-blinder")
                          .contentType("application/json")
                          .content(jsonRequest)
              )
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void closeBlindRollerTest_PercentageHigherThanCurrent() throws Exception
    {
        // Arrange
        long deviceId = 1L;

        String jsonRequest = """
              {
                  "measurement": "80",
                  "timeStamp": "2024-05-04T23:44:28.513805300Z",
                  "actuatorId": 1
              }
              """;

        when(actuatorActService.closeBlindRoller(any(DeviceID.class), any(Measurement.class),
              any(TimeStamp.class), any(ActuatorId.class)))
              .thenThrow(new IllegalArgumentException("The desired closed percentage must be lower than the current percentage/state of the blind roller"));

        // Assert
        String jsonResponse = """
              {
                  "error": "The desired closed percentage must be lower than the current percentage/state of the blind roller",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(
                    patch("/api/v1/devices/" + deviceId + "/close-blinder")
                          .contentType("application/json")
                          .content(jsonRequest)
              )
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void closeBlindRollerTest_NoBlindRollerActuator() throws Exception
    {
        // Arrange
        long deviceId = 1L;

        String jsonRequest = """
              {
                  "measurement": "45",
                  "timeStamp": "2024-05-04T23:44:28.513805300Z",
                  "actuatorId": 1
              }
              """;

        when(actuatorActService.closeBlindRoller(any(DeviceID.class), any(Measurement.class),
              any(TimeStamp.class), any(ActuatorId.class)))
              .thenThrow(new IllegalArgumentException("This Device must have at least one actuator of the type Blind Roller"));

        // Assert
        String jsonResponse = """
              {
                  "error": "This Device must have at least one actuator of the type Blind Roller",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(
                    patch("/api/v1/devices/" + deviceId + "/close-blinder")
                          .contentType("application/json")
                          .content(jsonRequest)
              )
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void closeBlindRollerTest_DeviceIdBelongsToInexistentDevice() throws Exception
    {
        // Arrange
        long deviceId = 67;

        String jsonRequest = """
              {
                  "measurement": "45",
                  "timeStamp": "2024-05-04T23:44:28.513805300Z",
                  "actuatorId": 1
              }
              """;

        when(actuatorActService.closeBlindRoller(any(DeviceID.class), any(Measurement.class),
              any(TimeStamp.class), any(ActuatorId.class)))
              .thenThrow(new IllegalArgumentException("A device with this device Id doesn't exist"));

        // Assert
        String jsonResponse = """
              {
                  "error": "A device with this device Id doesn't exist",
                  "_links": {
                      "self": {
                          "href": "http://localhost/api/v1/devices"
                      }
                  }
              }
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(
                    patch("/api/v1/devices/" + deviceId + "/close-blinder")
                          .contentType("application/json")
                          .content(jsonRequest)
              )
              .andExpect(status().isBadRequest())
              .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);
    }

    @Test
    void getDevicesByRoomAndSensorType_oneResult() throws Exception
    {
        // Arrange
        long houseId = 1L;
        long roomId = 1L;
        long deviceId = 1L;

        DeviceDTO deviceDTO = new DeviceDTO(roomId, deviceId, "d1", "dm1");

        Map<String, DeviceDTO> data = new HashMap<>();
        data.put("Binary", deviceDTO);
        data.put("BlindRoller", deviceDTO);
        Set<Map.Entry<String, DeviceDTO>> expected = data.entrySet();

        when(devFuncService.getData(houseId)).thenReturn(expected);

        String jsonResponse = """
              [
                {
                  "Binary":
                    {
                      "roomId":1,
                      "deviceModel":"dm1",
                      "deviceID":1,
                      "deviceName":"d1",
                      "isActive":true,
                      "links":
                        [
                          {
                            "rel":"self",
                            "href":"http://localhost/api/v1/devices"
                          },
                          {
                            "rel":"self",
                            "href":"http://localhost/api/v1/devices"
                          }
                        ]
                    }
                },
                {
                  "BlindRoller":
                    {
                      "roomId":1,
                      "deviceModel":"dm1",
                      "deviceID":1,
                      "deviceName":"d1",
                      "isActive":true,
                      "links":
                        [
                          {
                            "rel":"self",
                            "href":"http://localhost/api/v1/devices"
                          },
                          {
                            "rel":"self",
                            "href":"http://localhost/api/v1/devices"
                          }
                        ]
                    }
                }
              ]
              """;

        // Act
        ResultActions resultActions = mockMvc.perform(get("/api/v1/devices?house-id=1"))
              .andExpect(status().isOk())
              .andExpect(content().json(jsonResponse));

        // Assert
        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), true);
    }
}
