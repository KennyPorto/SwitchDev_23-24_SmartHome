package SmartHome.domain.valueObjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SensorTypeIdTest
{
    @Test
    void createSensorTypeName()
    {
        String name = "sensor";
        SensorTypeId sensorTypeId = new SensorTypeId(name);

        assertEquals(name, sensorTypeId.id);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void createSensorTypeName_invalidName(String name)
    {
        assertThrows(IllegalArgumentException.class, () -> new SensorTypeId(name));
    }

    @Test
    void returnName_ToString()
    {
        String name = "sensor";
        SensorTypeId sensorTypeId = new SensorTypeId(name);

        assertEquals(name, sensorTypeId.toString());
    }

    @Test
    void equals_sameObject()
    {
        SensorTypeId sensorTypeId = new SensorTypeId("1");
        SensorTypeId sensorTypeId1 = new SensorTypeId("1");

        assertEquals(sensorTypeId, sensorTypeId1);
    }

    @Test
    void equals_differentObjectNewObject()
    {
        SensorTypeId sensorTypeId = new SensorTypeId("1");
        Object sensorTypeId1 = new Object();

        assertNotEquals(sensorTypeId, sensorTypeId1);
    }

    @Test
    void hashCode_same()
    {
        SensorTypeId sensorTypeId = new SensorTypeId("1");
        SensorTypeId sensorTypeId1 = new SensorTypeId("1");

        assertEquals(sensorTypeId.hashCode(), sensorTypeId1.hashCode());
    }
}