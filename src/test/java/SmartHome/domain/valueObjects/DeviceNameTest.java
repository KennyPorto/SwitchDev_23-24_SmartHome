package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceNameTest
{

    final static String expectedMessage = "Parameter value 'name' must be a non-empty string.";
    final static String sDeviceName1 = "AirConditioner";

    @Test
    void shouldCreateAValidDeviceName()
    {

        new DeviceName(sDeviceName1);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", " \n"})
    void shouldThrowExceptionDeviceName(String name)
    {

        // Act
        Exception exception = assertThrows(Exception.class, () -> {
            new DeviceName(name);
        });

        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldReturnFalseEqualsWithNull()
    {

        // Arrange
        DeviceName oDeviceName = new DeviceName(sDeviceName1);

        // Act
        boolean isEquals = oDeviceName.equals(null);

        // Assert
        assertFalse(isEquals);
    }

    @Test
    void shouldReturnTrueEqualsWithSameObject()
    {

        // Arrange
        DeviceName oDeviceName = new DeviceName(sDeviceName1);

        // Act
        boolean isEquals = oDeviceName.equals(oDeviceName);

        // Assert
        assertTrue(isEquals);
    }

    @Test
    void shouldReturnTrueEqualsWithSameName()
    {

        // Arrange
        DeviceName oDeviceName1 = new DeviceName(sDeviceName1);
        DeviceName oDeviceName2 = new DeviceName(sDeviceName1);

        // Act
        boolean isEquals = oDeviceName1.equals(oDeviceName2);

        // Assert
        assertTrue(isEquals);
    }

    @Test
    void shouldReturnTheNameString()
    {

        // Arrange
        DeviceName oDeviceName1 = new DeviceName(sDeviceName1);

        // Act + Assert
        assertEquals(oDeviceName1.name, sDeviceName1);
    }

    @Test
    void shouldBeTheSameHashCode()
    {

        // Arrange
        DeviceName deviceName1 = new DeviceName("Heater");
        DeviceName deviceName2 = new DeviceName("Heater");

        // Act
        int hashCode1 = deviceName1.hashCode();
        int hashCode2 = deviceName2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }
}
