package SmartHome.controller.rest_controllers;

import SmartHome.controller.rest_controllers.resources.ActuatorActivityLogResourceHandler;
import SmartHome.controller.rest_controllers.resources.DeviceResourceHandler;
import SmartHome.controller.rest_controllers.resources.SensorActivityLogResourceHandler;
import SmartHome.domain.activitylog.implementation.ActuatorActivityLog;
import SmartHome.domain.activitylog.implementation.SensorActivityLog;
import SmartHome.domain.device.Device;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.*;
import SmartHome.service.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/devices")
@AllArgsConstructor
public class DeviceController
{
    private final DeviceService deviceService;
    private final DeviceLogService deviceLogService;
    private final EnvironmentalTemperatureService environmentalTemperatureService;
    private final DevFuncService deviceFuncService;
    private final ActuatorActService actuatorActService;

    @PostMapping
    public ResponseEntity<Object> addDevice(@RequestBody DeviceDTO dto)
    {
        DeviceName deviceName = new DeviceName(dto.deviceName);
        DeviceID deviceID = new DeviceID(dto.deviceID);
        DeviceModel deviceModel = new DeviceModel(dto.deviceModel);
        RoomId roomId = new RoomId(dto.roomId);

        Device device = deviceService.add(deviceName, deviceID, deviceModel, roomId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                    DeviceResourceHandler.manageAdd(DeviceMapper.deviceToDto(device)));
    }

    @PatchMapping("/{device-id}/deactivate")
    public ResponseEntity<Object> deactivateDevice(@PathVariable("device-id") long deviceId)
    {
        Device device = deviceService.deactivateDevice(deviceId);

        return ResponseEntity.status(HttpStatus.OK).body(DeviceResourceHandler.manageDeactivate(
                DeviceMapper.deviceToDtoIsActive(device)));
    }

    @GetMapping("/{device-id}")
    public ResponseEntity<DeviceDTO> findDeviceById(@PathVariable("device-id") long deviceId)
    {
        Device device = deviceService.findById(deviceId);
        return ResponseEntity.status(HttpStatus.OK).body(DeviceResourceHandler.manageFindById(
                            DeviceMapper.deviceToDto(device)));
    }

    @GetMapping
    public ResponseEntity<Iterable<DeviceDTO>> findAllDevices()
    {
        Iterable<Device> devices = deviceService.findAll();
        Iterable<DeviceDTO> deviceDTOs = DeviceResourceHandler.manageFindAll(
              DeviceMapper.deviceListToDto(devices)
        );
        return ResponseEntity.status(HttpStatus.OK).body(deviceDTOs);
    }

    @GetMapping(params = "room-id")
    public ResponseEntity<Iterable<DeviceDTO>> findAllDevicesByRoom(@RequestParam("room-id") long roomId)
    {
        Iterable<Device> devices = deviceService.findAllByRoomId(roomId);
        Iterable<DeviceDTO> deviceDTOs = DeviceResourceHandler.manageFindAll(
              DeviceMapper.deviceListToDto(devices)
        );
        return ResponseEntity.status(HttpStatus.OK).body(deviceDTOs);
    }

    @GetMapping(value = "/{device-id}/logs", params = { "start", "end" })
    public ResponseEntity<Object> getAllLogsFromDevice(@PathVariable("device-id") long deviceId,
                                                       @RequestParam("start") String startDate,
                                                       @RequestParam("end") String endDate)
    {
        DeviceID deviceIdObj = new DeviceID(deviceId);
        Pair<Iterable<ActuatorActivityLog>, Iterable<SensorActivityLog>> response =
                deviceLogService.getAllLogsFromDevice(deviceIdObj, ZonedDateTime.parse(startDate),
                        ZonedDateTime.parse(endDate));

        Iterable<ActuatorActivityLogDTO> actuatorLogsDto = ActivityLogMapper.toDTOs(response.getLeft());
        Iterable<SensorActivityLogDTO> sensorLogsDto = ActivityLogMapper.sensorLogAndMeasurementToDto(response.getRight());

        Iterable<SensorActivityLogDTO> sensorLogsResource =
              SensorActivityLogResourceHandler.manageFindAll(sensorLogsDto);

        Iterable<ActuatorActivityLogDTO> actuatorLogsResource =
              ActuatorActivityLogResourceHandler.manageFindAll(actuatorLogsDto);

        return ResponseEntity.status(HttpStatus.OK).body(new DeviceRecordsDTO(actuatorLogsResource,
              sensorLogsResource)
        );
    }

    @GetMapping("/{device-id}/start={start}&end={end}&delta={delta}")
    public ResponseEntity<Object> getMaximumTemperatureDifference(@PathVariable("device-id") long deviceId,
                                                                  @PathVariable("start") String startDate,
                                                                  @PathVariable("end") String endDate,
                                                                  @PathVariable("delta") long delta)
    {
        double result = environmentalTemperatureService.getMaximumTemperatureDifference(deviceId, startDate,
              endDate, delta);
        return ResponseEntity.status(HttpStatus.OK).body(
              DeviceResourceHandler.manageMaxInstTempDiff(
                    new MaxInstantTempDiffDTO(result)
              ));
    }

    @GetMapping(params = "house-id")
    public ResponseEntity<Object> getDevicesByRoomAndSensorType(@RequestParam("house-id") long houseId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(
              DeviceResourceHandler.manageFindBySensorType(
                    deviceFuncService.getData(houseId)
              ));
    }

    @PatchMapping(value = "/{device-id}/close-blinder")
    public ResponseEntity<ActuatorActivityLogDTO> closeBlindRoller(@PathVariable("device-id") long deviceId,
                                                                   @RequestBody CloseBlindRollerDTO closeBlindRollerDTO
    ){
        ActuatorActivityLog result = actuatorActService.closeBlindRoller(
                new DeviceID(deviceId), new Measurement(closeBlindRollerDTO.measurement),
                new TimeStamp(closeBlindRollerDTO.timeStamp), new ActuatorId(closeBlindRollerDTO.actuatorId)
        );

        return ResponseEntity.status(HttpStatus.OK).body(
              DeviceResourceHandler.manageCloseBlindRollerActuator(
                    ActivityLogMapper.toDTO(result)
              ));
    }
}
