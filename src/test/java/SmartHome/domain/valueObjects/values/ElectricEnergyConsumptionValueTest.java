package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.ElectricEnergyConsumptionValue;
import SmartHome.domain.valueObjects.values.implementation.WhValue;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ElectricEnergyConsumptionValueTest
{

    @Test
    void validEnergyConsumptionOverAPeriod()
    {
        // Arrange
        Map<LocalTime, WhValue> data = new HashMap<>();
        data.put(LocalTime.of(10, 0), new WhValue(10.0));
        data.put(LocalTime.of(11, 0), new WhValue(20.0));
        data.put(LocalTime.of(12, 0), new WhValue(30.0));
        ElectricEnergyConsumptionValue consumptionValue = new ElectricEnergyConsumptionValue(LocalTime.of(10, 0), LocalTime.of(12, 0), data);

        // Act
        double consumption = consumptionValue.consumption;

        // Assert
        assertEquals(20.0, consumption);
    }

    @Test
    void invalidTimePeriod()
    {
        // Arrange
        Map<LocalTime, WhValue> data = new HashMap<>();
        data.put(LocalTime.of(10, 0), new WhValue(10.0));
        data.put(LocalTime.of(11, 0), new WhValue(20.0));
        data.put(LocalTime.of(12, 0), new WhValue(30.0));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new ElectricEnergyConsumptionValue(LocalTime.of(12, 0), LocalTime.of(10, 0), data);
        }, "Invalid time period");
    }

    @Test
    void invalidNegativeEnergyConsumption()
    {
        // Arrange
        Map<LocalTime, WhValue> data = new HashMap<>();
        data.put(LocalTime.of(10, 0), new WhValue(30.0));
        data.put(LocalTime.of(11, 0), new WhValue(20.0));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new ElectricEnergyConsumptionValue(LocalTime.of(10, 0), LocalTime.of(11, 0), data);
        }, "Invalid energy consumption value");
    }


}
