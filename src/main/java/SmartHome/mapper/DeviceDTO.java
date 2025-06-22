package SmartHome.mapper;

import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    public long roomId;
    public String deviceModel;
    public long deviceID;
    public String deviceName;
    public boolean isActive;


    public DeviceDTO(long roomId, long id, String name, String model) {
        this.roomId = roomId;
        this.deviceID = id;
        this.deviceName = name;
        this.deviceModel = model;
        this.isActive = true;
    }

    public DeviceDTO(long roomId, String deviceName, String deviceModel, boolean isActive)
    {
        this.roomId = roomId;
        this.deviceModel = deviceModel;
        this.deviceName = deviceName;
        this.isActive = isActive;
    }

    public DeviceDTO(long roomId, long deviceID, String deviceName, String deviceModel, boolean isActive)
    {
        this.roomId = roomId;
        this.deviceModel = deviceModel;
        this.deviceID = deviceID;
        this.deviceName = deviceName;
        this.isActive = isActive;
    }
}
