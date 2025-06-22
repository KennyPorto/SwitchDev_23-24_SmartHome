package SmartHome.persistence.jpa.datamodel;

import SmartHome.domain.device.Device;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "device")
@Getter
public class DeviceDataModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    @Column(columnDefinition = "integer DEFAULT 0", nullable = false)
    private int version;

    private String deviceName;
    private String deviceModel;
    private boolean deviceStatus;
    private long roomId;

    public DeviceDataModel()
    {
    }

    public DeviceDataModel(Device device)
    {
        this.id = device.identity().getId();
        this.deviceName = device.getName().name;
        this.deviceModel = device.getModel().model;
        this.deviceStatus = device.getDeviceStatus().activated;
        this.roomId = device.getRoomId().id;
    }
}