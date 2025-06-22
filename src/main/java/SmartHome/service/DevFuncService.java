package SmartHome.service;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.device.Device;
import SmartHome.domain.house.HouseFactory;
import SmartHome.domain.repository.ActuatorRepository;
import SmartHome.domain.repository.DeviceRepository;
import SmartHome.domain.repository.RoomRepository;
import SmartHome.domain.repository.SensorRepository;
import SmartHome.domain.room.Room;
import SmartHome.domain.sensors.Sensor;
import SmartHome.domain.valueObjects.HouseId;
import SmartHome.mapper.DeviceDTO;
import SmartHome.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DevFuncService {
    private final HouseFactory _houseFactory;
    private final RoomRepository _roomRepository;
    private final DeviceRepository _deviceRepository;
    private final SensorRepository _sensorRepository;
    private final ActuatorRepository _actuatorRepository;

    public DevFuncService(HouseFactory houseFactory,
                          @Qualifier("roomRepoSpringDataImpl") RoomRepository roomRepository,
                          @Qualifier("deviceRepoSpringDataImpl") DeviceRepository deviceRepository,
                          @Qualifier("sensorRepoSpringDataImpl") SensorRepository sensorRepository,
                          @Qualifier("actuatorRepoSpringDataImpl") ActuatorRepository actuatorRepository)
    {
        if (houseFactory == null || roomRepository == null || deviceRepository == null || sensorRepository == null
                || actuatorRepository == null)
        {
            throw new IllegalArgumentException();
        }
        this._houseFactory = houseFactory;
        this._roomRepository = roomRepository;
        this._deviceRepository = deviceRepository;
        this._sensorRepository = sensorRepository;
        this._actuatorRepository = actuatorRepository;
    }

    public Set<Map.Entry<String, DeviceDTO>> getData(long houseId)
    {
        List<Device> deviceList = getAllHouseDevices(houseId);
        if (deviceList == null)
        {
            return null;
        }

        return getFunctionalities(deviceList);
    }

    private List<Device> getAllHouseDevices(long houseId)
    {
        Optional<HouseId> id = _houseFactory.createHouseId(houseId);

        if (id.isEmpty())
        {
            return null;
        }

        Iterable<Room> rooms = _roomRepository.findAllByHouseId(id.get().id);
        List<Device> totalDevicesList = new ArrayList<>();

        for (Room room : rooms)
        {
            Iterable<Device> devices = _deviceRepository.findAllByRoomId(room.identity().id);
            for (Device device : devices) totalDevicesList.add(device);
        }

        return totalDevicesList;
    }

    private Set<Map.Entry<String, DeviceDTO>> getFunctionalities(List<Device> deviceList)
    {
        Map<String, DeviceDTO> functionalities = new HashMap<>();

        for (Device device : deviceList)
        {
            Iterable<Sensor> sensors = _sensorRepository.findAllByDeviceId(device.identity().id);
            Iterable<Actuator> actuators = _actuatorRepository.findAllByDeviceId(device.identity().id);
            DeviceDTO deviceDTO = DeviceMapper.deviceToDto(device);

            for (Sensor sensor : sensors)
            {
                functionalities.put(sensor.getSensorTypeId().toString(), deviceDTO);
            }

            for (Actuator actuator : actuators)
            {
                functionalities.put(actuator.getActuatorTypeId().toString(), deviceDTO);
            }
        }

        return functionalities.entrySet();
    }
}
