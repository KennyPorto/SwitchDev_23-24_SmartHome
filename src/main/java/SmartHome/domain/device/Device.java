package SmartHome.domain.device;

import SmartHome.ddd.AggregateRoot;
import SmartHome.domain.valueObjects.*;

import java.util.Objects;

public class Device implements AggregateRoot<DeviceID> {
    private final RoomId _roomId;
    private final DeviceID _deviceID;
    private final DeviceName _deviceName;
    private final DeviceModel _deviceModel;
    private DeviceStatus _deviceStatus;
    private final DeviceFactory _deviceFactory;

    protected Device(RoomId roomId, DeviceID deviceID, DeviceName deviceName, DeviceModel deviceModel,
                  DeviceFactory deviceFactory) {
        this._roomId = roomId;
        this._deviceID = deviceID;
        this._deviceName = deviceName;
        this._deviceModel = deviceModel;
        this._deviceFactory = deviceFactory;
        _deviceStatus = _deviceFactory.createDeviceStatus(true);
    }

    protected Device(RoomId roomId, DeviceID deviceID, DeviceName deviceName, DeviceModel deviceModel,
                     DeviceStatus deviceStatus, DeviceFactory deviceFactory) {
        this._roomId = roomId;
        this._deviceID = deviceID;
        this._deviceName = deviceName;
        this._deviceModel = deviceModel;
        this._deviceFactory = deviceFactory;
        _deviceStatus = deviceStatus;
    }

    public RoomId getRoomId() { return _roomId; }

    public DeviceID identity() {
        return _deviceID;
    }

    public DeviceName getName() {
        return _deviceName;
    }

    public DeviceModel getModel() {
        return _deviceModel;
    }

    public DeviceStatus getDeviceStatus() {
        return _deviceStatus;
    }

    public boolean deactivateDevice() {
        this._deviceStatus = _deviceFactory.createDeviceStatus(false);
        return true;
    }

    @Override
    public boolean equals(Object object) {

        if(this == object)
            return true;

        if ( object instanceof Device device ) {
            return this._deviceID.equals(device._deviceID);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_roomId, _deviceID, _deviceName, _deviceModel, _deviceFactory);
    }

    @Override
    public boolean sameAs(Object object) {

        if ( object instanceof Device device ) {
            return this._roomId == device._roomId &&
                    this._deviceName.equals(device._deviceName) &&
                    this._deviceModel.equals(device._deviceModel);
        }

        return false;
    }

}
