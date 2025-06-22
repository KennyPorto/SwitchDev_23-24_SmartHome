package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.AveragePowerConsumptionValue;
import SmartHome.domain.valueObjects.values.implementation.WValue;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AveragePowerConsumptionValueTest
{
    @Test
    void averagePowerConsumptionValue_ValidDataWithOneReading() {
        // Arrange
        Map<LocalTime, WValue> data = new HashMap<>();
        data.put(LocalTime.of(10, 0), new WValue(10.0));

        // Act
        AveragePowerConsumptionValue averagePowerConsumptionValue = new AveragePowerConsumptionValue(LocalTime.of(9, 59), LocalTime.of(10, 1), data);

        // Assert
        assertEquals(10.0, averagePowerConsumptionValue.averageConsumption);
    }

    @Test
    void testAveragePowerConsumptionValue_ValidDataWithTwoReadings() {
        // Arrange
        Map<LocalTime, WValue> data = new HashMap<>();
        data.put(LocalTime.of(10, 0), new WValue(10.0));
        data.put(LocalTime.of(11, 0), new WValue(20.0));

        // Act
        AveragePowerConsumptionValue averagePowerConsumptionValue = new AveragePowerConsumptionValue(LocalTime.of(9, 59), LocalTime.of(11, 1), data);

        // Assert
        assertEquals(15.0, averagePowerConsumptionValue.averageConsumption);
    }

    @Test
    void testAveragePowerConsumptionValue_ValidDataWithSeveralReadings() {
        // Arrange
        Map<LocalTime, WValue> data = new HashMap<>();
        data.put(LocalTime.of(10, 0), new WValue(20.0));
        data.put(LocalTime.of(11, 0), new WValue(30.0));
        data.put(LocalTime.of(12, 0), new WValue(40.0));

        // Act
        AveragePowerConsumptionValue averagePowerConsumptionValue = new AveragePowerConsumptionValue(LocalTime.of(10, 0), LocalTime.of(12, 0), data);

        // Assert
        assertEquals(30.0, averagePowerConsumptionValue.averageConsumption);
    }

    @Test
    void testAveragePowerConsumptionValue_InvalidTimePeriod() {
        // Arrange
        Map<LocalTime, WValue> data = new HashMap<>();
        data.put(LocalTime.of(10, 0), new WValue(10.0));
        data.put(LocalTime.of(11, 0), new WValue(20.0));
        data.put(LocalTime.of(12, 0), new WValue(30.0));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new AveragePowerConsumptionValue(LocalTime.of(12, 0), LocalTime.of(10, 0), data);
        }, "Invalid time period");
    }

    @Test
    void testGetReading_ValidPeriod() {
        // Arrange
        Map<LocalTime, WValue> data = new HashMap<>();
        data.put(LocalTime.of(10, 0), new WValue(10.0));
        data.put(LocalTime.of(11, 0), new WValue(20.0));
        data.put(LocalTime.of(12, 0), new WValue(30.0));
        AveragePowerConsumptionValue averagePowerConsumptionValue = new AveragePowerConsumptionValue(LocalTime.of(10, 0), LocalTime.of(12, 0), data);

        // Act
        double average = averagePowerConsumptionValue.getReading(LocalTime.of(10, 0), LocalTime.of(12, 0));

        // Assert
        assertEquals(20.0, average);
    }

    @Test
    void testGetReading_InvalidPeriod() {
        // Arrange
        Map<LocalTime, WValue> data = new HashMap<>();
        data.put(LocalTime.of(10, 0), new WValue(10.0));
        data.put(LocalTime.of(11, 0), new WValue(20.0));
        data.put(LocalTime.of(12, 0), new WValue(30.0));
        AveragePowerConsumptionValue averagePowerConsumptionValue = new AveragePowerConsumptionValue(LocalTime.of(10, 0), LocalTime.of(12, 0), data);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            averagePowerConsumptionValue.getReading(LocalTime.of(12, 0), LocalTime.of(10, 0));
        }, "No readings to show");
    }
}
