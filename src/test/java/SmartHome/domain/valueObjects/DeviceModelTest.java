package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceModelTest
{

    private final static String DEVICE_MODEL = "Heater";
    private final DeviceModel deviceModel = new DeviceModel(DEVICE_MODEL);

    @Test
    void validDeviceModel()
    {
        // Act
        String model = deviceModel.model;

        // Assert
        assertEquals(DEVICE_MODEL, model);
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "\t", " \n"})
    @NullAndEmptySource
    void invalidDeviceModel(String name)
    {
        // Arrange
        String expectedMessage = "Device model cannot be null or empty";

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new DeviceModel(name));
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void equalsSameObject()
    {
        // Act & Assert
        assertEquals(deviceModel, deviceModel);
    }

    @Test
    void equalsSameModel()
    {
        // Arrange
        DeviceModel sameDeviceModel = new DeviceModel(DEVICE_MODEL);

        // Act & Assert
        assertEquals(deviceModel, sameDeviceModel);
    }

    @Test
    void shouldBeTheSameHashCode()
    {

        // Arrange
        DeviceModel deviceModel1 = new DeviceModel("Xiaomi");
        DeviceModel deviceModel2 = new DeviceModel("Xiaomi");

        // Act
        int hashCode1 = deviceModel1.hashCode();
        int hashCode2 = deviceModel2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

}
