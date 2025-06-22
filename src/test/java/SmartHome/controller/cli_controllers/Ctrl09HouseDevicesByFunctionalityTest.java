package SmartHome.controller.cli_controllers;

import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.actuatortype.ActuatorType;
import SmartHome.domain.actuatortype.ActuatorTypeFactory;
import SmartHome.domain.device.DeviceFactory;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.repository.HouseRepository;
import SmartHome.domain.room.RoomFactory;
import SmartHome.domain.sensors.SensorFactory;
import SmartHome.domain.sensortype.SensorType;
import SmartHome.domain.sensortype.SensorTypeFactory;
import SmartHome.domain.valueObjects.*;
import SmartHome.mapper.DeviceDTO;
import SmartHome.persistence.mem.*;
import SmartHome.service.DevFuncService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Ctrl09HouseDevicesByFunctionalityTest
{
    private HouseFactory houseFactory;
    private RoomRepositoryImplMem roomRepository;
    private DeviceRepositoryImplMem deviceRepository;
    private SensorRepositoryImplMem sensorRepository;
    private ActuatorRepository actuatorRepository;
    private Ctrl09HouseDevicesByFunctionality ctrl09ListOfDevicesGroupedByRoomAndFunctionality;
    @BeforeEach
    void setup() {
        houseFactory = new HouseFactory();
        roomRepository = new RoomRepositoryImplMem();
        deviceRepository = new DeviceRepositoryImplMem();
        sensorRepository = new SensorRepositoryImplMem();
        actuatorRepository = new ActuatorRepositoryImplMem();
        DevFuncService devFuncService = new DevFuncService(houseFactory, roomRepository, deviceRepository, sensorRepository, actuatorRepository);
        ctrl09ListOfDevicesGroupedByRoomAndFunctionality = new Ctrl09HouseDevicesByFunctionality(devFuncService);
    }

    @Test
    void getDevicesByRoomAndFunctionality_successEmptyList() {
        // Arrange
        long houseId = 1L;

        // Act
        Map<String, DeviceDTO> expected = new HashMap<>();

        // Assert
        assertEquals(expected.entrySet(), ctrl09ListOfDevicesGroupedByRoomAndFunctionality.getDevicesByRoomAndFunctionality(houseId));
    }

    @Test
    void getDevicesByRoomAndFunctionality_successListWithElements() {
        // Arrange
        long houseId = 1L;
        HouseRepository houseRepository = new HouseRepositoryImplMem();
        houseRepository.save(houseFactory.createHouse(new HouseId(houseId),
                new Address("s1", "d1", "4000-100", "c1", "Portugal"),
                new GPS(1, 1)));

        RoomFactory roomFactory = new RoomFactory();
        roomRepository.save(roomFactory.createRoom(new RoomName("r1"),
                new RoomId(1L),
                new HouseId(1L),
                new Floor("f1"),
                new Dimensions(1.0, 1.0, 1.0),
                        OutdoorIndoor.OUTDOOR)
                );

        DeviceFactory deviceFactory = new DeviceFactory();
        deviceRepository.save(deviceFactory.createDevice(new RoomId(1L),
                new DeviceID(1L),
                new DeviceName("d1"),
                new DeviceModel("dm1"),
                new DeviceStatus(true)));

        SensorFactory sensorFactory = new SensorFactory();
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactory();
        Optional<SensorTypeId> sensorTypeId = sensorTypeFactory.createSensorTypeName("Binary");
        if (sensorTypeId.isPresent()) {
            SensorType sensorType = sensorTypeFactory.createSensorType(sensorTypeId.get(), MeasurementUnit.Celsius);
            SensorTypeRepositoryImplMem sensorTypeRepositoryImplMem = new SensorTypeRepositoryImplMem();
            sensorTypeRepositoryImplMem.save(sensorType);
            sensorRepository.save(sensorFactory.createSensor(new DeviceID(1L),
                    new SensorModel("BinarySwitch"),
                    new SensorName("s1"),
                    new SensorId(1L),
                    sensorTypeId.get()));

            ActuatorFactory actuatorFactory = new ActuatorFactory();
            ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactory();
            Optional<ActuatorTypeId> actuatorTypeId = actuatorTypeFactory.createActuatorTypeName("BlindRoller");
            if (actuatorTypeId.isPresent()) {

                ActuatorType actuatorType = actuatorTypeFactory.createActuatorType(actuatorTypeId.get(), MeasurementUnit.Percentage);
                ActuatorTypeRepositoryImplMem actuatorTypeRepository = new ActuatorTypeRepositoryImplMem();
                actuatorTypeRepository.save(actuatorType);
                actuatorRepository.save(actuatorFactory.createActuator(new DeviceID(1L),
                        new ActuatorModel("BlindRollerActuator"),
                        new ActuatorName("blind"),
                        new ActuatorId(1L),
                        actuatorTypeId.get()));

                DeviceDTO deviceDTO = new DeviceDTO(1L,1L, "d1", "dm1");

                // Act
                Map<String, DeviceDTO> expected = new HashMap<>();
                expected.put("Binary", deviceDTO);
                expected.put("BlindRoller", deviceDTO);
                Iterator<Map.Entry<String, DeviceDTO>> expectedIter = expected.entrySet().iterator();
                Iterator<Map.Entry<String, DeviceDTO>> resultIter = ctrl09ListOfDevicesGroupedByRoomAndFunctionality.getDevicesByRoomAndFunctionality(houseId).iterator();

                // Assert
                while (expectedIter.hasNext()) {
                    Map.Entry<String, DeviceDTO> currentExpected = expectedIter.next();
                    Map.Entry<String, DeviceDTO> currentResult = resultIter.next();
                    assertEquals(currentExpected.getKey(), currentResult.getKey());
                    assertEquals(currentExpected.getValue().deviceID, currentResult.getValue().deviceID);
                    assertEquals(currentExpected.getValue().deviceName, currentResult.getValue().deviceName);
                    assertEquals(currentExpected.getValue().deviceModel, currentResult.getValue().deviceModel);
                    assertEquals(currentExpected.getValue().roomId, currentResult.getValue().roomId);
                }
            }
        }
    }

    @Test
    void getDevicesByRoomAndFunctionality_NonExistingHouseId() {
        assertEquals(List.of().toString(), ctrl09ListOfDevicesGroupedByRoomAndFunctionality.getDevicesByRoomAndFunctionality(1L).toString());
    }
}