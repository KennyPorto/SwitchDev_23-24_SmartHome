package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeviceStatusTest {
    @Test
    void createDeviceStatus_activated() {
        assertTrue(new DeviceStatus(true).activated);
    }

    @Test
    void createDeviceStatus_deactivated() {
        assertFalse(new DeviceStatus(false).activated);
    }
}