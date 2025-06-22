package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValueFactoryImplTest {

    @Test
    void createRangeActuatorInt() {
        int lowerLimit = -1;
        int upperLimit = 1;
        RangeActuatorIntValue val = new ValueFactoryImpl().createRangeActuatorInt(lowerLimit, upperLimit);
        assertInstanceOf(RangeActuatorIntValue.class, val);
    }

    @Test
    void createRangeActuatorFractional() {
        double lowerLimit = -1.0;
        double upperLimit = 1.0;
        RangeActuatorFractionalValue val = new ValueFactoryImpl().createRangeActuatorDecimal(lowerLimit, upperLimit);
        assertInstanceOf(RangeActuatorFractionalValue.class, val);
    }

    @Test
    void createWValueTest() {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        double value = 10.0;

        // Act
        Value result = valueFactory.createWValue(value);

        // Assert
        assertInstanceOf(WValue.class, result);
    }

    @Test
    void createWValue_Null()
    {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        double value = -10.0;

        // Act
        Value result = valueFactory.createWValue(value);

        // Assert
        assertNull(result);
    }

    @Test
    void createWhValueTestTrue() {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        double value = 10.2;

        // Act
        Value result = valueFactory.createWhValue(value);

        // Assert
        assertInstanceOf(WhValue.class, result);
    }

    @Test
    void createKmhCardinalValueTestTrue() {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        double speed = 10;
        double radian = Math.PI;

        // Act
        KmhCardinalValue result = valueFactory.createKmhCardinalValue(speed, radian);

        // Assert
        assertEquals(speed, result.speed);
        assertEquals("West", result.getWindDirection(radian));
    }

    @Test
    void createKmhCardinalValueTestFalse()
    {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        double speed = -10;
        double radian = Math.PI;

        // Act
        KmhCardinalValue result = valueFactory.createKmhCardinalValue(speed, radian);

        // Assert
        assertNull(result);
    }

    @Test
    void createPercentage() {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();

        //Act
        Value result = valueFactory.createPercentage(50);

        // Assert
        assertInstanceOf(PercentageValue.class, result);
    }

    @Test
    void invalidCreatePercentage()
    {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();

        //Act
        Value result = valueFactory.createPercentage(150);

        // Assert
        assertNull(result);
    }

    @Test
    void createCelsiusTemperature() {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        double value = 3;

        //Act
        Value result = valueFactory.createCelsiusTemperature(value);

        // Assert
        assertInstanceOf(CelsiusValue.class, result);
    }

    @Test
    void createWm2Value() {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        double value = 3;

        //Act
        Value result = valueFactory.createWm2Value(value);

        // Assert
        assertInstanceOf(Wm2Value.class, result);
    }

    @Test
    void createSunriseValue()
    {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        double latitude = 40.7128;
        double longitude = -74.0060;

        // Act
        Value result = valueFactory.createSunriseValue(LocalDate.now(), latitude, longitude);

        // Assert
        assertInstanceOf(SunriseValue.class, result);
    }

    @Test
    void createSunsetValue()
    {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        double latitude = 40.7128;
        double longitude = -74.0060;

        // Act
        Value result = valueFactory.createSunsetValue(LocalDate.now(), latitude, longitude);

        // Assert
        assertInstanceOf(SunsetValue.class, result);
    }

    @Test
    void createAveragePowerConsumptionValue()
    {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(1, 59);
        Map<LocalTime, WValue> data = Map.of(LocalTime.of(1, 0), new WValue(10.0));

        // Act
        Value result = valueFactory.createAveragePowerConsumptionValue(start, end, data);

        // Assert
        assertInstanceOf(AveragePowerConsumptionValue.class, result);
    }

    @Test
    void invalidCreateAveragePowerConsumptionValue()
    {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 59);

        // Act
        Value result = valueFactory.createAveragePowerConsumptionValue(start, end, null);

        // Assert
        assertNull(result);
    }

    @Test
    void createElectricEnergyConsumptionValue()
    {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(1, 59);
        Map<LocalTime, WhValue> data = Map.of(LocalTime.of(1, 0), new WhValue(10.0));

        // Act
        Value result = valueFactory.createElectricEnergyConsumptionValue(start, end, data);

        // Assert
        assertInstanceOf(ElectricEnergyConsumptionValue.class, result);
    }

    @Test
    void invalidCreateElectricEnergyConsumptionValue()
    {
        // Arrange
        ValueFactoryImpl valueFactory = new ValueFactoryImpl();
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 59);

        // Act
        Value result = valueFactory.createElectricEnergyConsumptionValue(start, end, null);

        // Assert
        assertNull(result);
    }


}