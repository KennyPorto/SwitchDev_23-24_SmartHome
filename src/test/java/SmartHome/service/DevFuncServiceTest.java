package SmartHome.service;

import SmartHome.domain.house.HouseFactory;
import SmartHome.persistence.mem.ActuatorRepositoryImplMem;
import SmartHome.persistence.mem.DeviceRepositoryImplMem;
import SmartHome.persistence.mem.RoomRepositoryImplMem;
import SmartHome.persistence.mem.SensorRepositoryImplMem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DevFuncServiceTest {
    private HouseFactory houseFactory;
    private RoomRepositoryImplMem roomRepository;
    private DeviceRepositoryImplMem deviceRepository;
    private SensorRepositoryImplMem sensorRepository;
    private ActuatorRepositoryImplMem actuatorRepository;

    @BeforeEach
    void setup() {
        houseFactory = mock(HouseFactory.class);
        roomRepository = mock(RoomRepositoryImplMem.class);
        deviceRepository = mock(DeviceRepositoryImplMem.class);
        sensorRepository = mock(SensorRepositoryImplMem.class);
        actuatorRepository = mock(ActuatorRepositoryImplMem.class);
    }

    @Test
    void getData_NonExistingHouseId() {
        // Arrange
        long houseId = 1L;

        DevFuncService devFuncService = new DevFuncService(houseFactory, roomRepository, deviceRepository, sensorRepository, actuatorRepository);
        when(houseFactory.createHouseId(houseId)).thenReturn(Optional.empty());

        assertNull(devFuncService.getData(houseId));
    }

    @Test
    void getData_houseFactoryNull() {
        houseFactory = null;

        assertThrows(IllegalArgumentException.class, () -> new DevFuncService(houseFactory, roomRepository, deviceRepository, sensorRepository, actuatorRepository));
    }

    @Test
    void getData_roomRepositoryNull() {
        roomRepository = null;

        assertThrows(IllegalArgumentException.class, () -> new DevFuncService(houseFactory, roomRepository, deviceRepository, sensorRepository, actuatorRepository));
    }

    @Test
    void getData_deviceRepositoryNull() {
        deviceRepository = null;

        assertThrows(IllegalArgumentException.class, () -> new DevFuncService(houseFactory, roomRepository, deviceRepository, sensorRepository, actuatorRepository));
    }

    @Test
    void getData_sensorRepositoryNull() {
        sensorRepository = null;

        assertThrows(IllegalArgumentException.class, () -> new DevFuncService(houseFactory, roomRepository, deviceRepository, sensorRepository, actuatorRepository));
    }

    @Test
    void getData_actuatorRepositoryNull() {
        actuatorRepository = null;

        assertThrows(IllegalArgumentException.class, () -> new DevFuncService(houseFactory, roomRepository, deviceRepository, sensorRepository, actuatorRepository));
    }
}