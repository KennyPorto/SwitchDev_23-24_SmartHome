package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.RangeActuatorFractionalValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeActuatorDecimalValueTest
{
    private double lowerLimit;
    private double upperLimit;

    @BeforeEach
    void setup()
    {
        lowerLimit = -1.0;
        upperLimit = 1.0;
    }

    @Test
    void createRangeActuatorFractionalValueSuccessfully()
    {
        RangeActuatorFractionalValue rangeActuatorFractionalValue = new RangeActuatorFractionalValue(lowerLimit, upperLimit);
        assertNotNull(rangeActuatorFractionalValue);
    }

    @Test
    void setValueRangeActuatorFractionalValue()
    {
        // Arrange
        String measured = "0";

        RangeActuatorFractionalValue rangeActuatorFractionalValue = new RangeActuatorFractionalValue(lowerLimit, upperLimit);

        // Act + Assert
        assertTrue(rangeActuatorFractionalValue.setValue(measured));
    }

    @Test
    void setMeasurementValueEqualsToUpperToBoundary()
    {
        // Arrange
        RangeActuatorFractionalValue rangeActuatorFractionalValue = new RangeActuatorFractionalValue(lowerLimit, upperLimit);

        // Assert
        assertTrue(rangeActuatorFractionalValue.setValue("1"));
    }

    @Test
    void setMeasurementValueEqualsToLowerToBoundary()
    {
        // Arrange
        RangeActuatorFractionalValue rangeActuatorFractionalValue = new RangeActuatorFractionalValue(lowerLimit, upperLimit);

        // Assert
        assertTrue(rangeActuatorFractionalValue.setValue("-1"));
    }

    @Test
    void setMeasurementValueInsideBoundary()
    {
        // Arrange
        RangeActuatorFractionalValue rangeActuatorFractionalValue = new RangeActuatorFractionalValue(lowerLimit, upperLimit);

        // Assert
        assertTrue(rangeActuatorFractionalValue.setValue("0"));
    }

    @Test
    void setMeasurementValueBiggerThanBoundary()
    {
        // Arrange
        RangeActuatorFractionalValue rangeActuatorFractionalValue = new RangeActuatorFractionalValue(lowerLimit, upperLimit);

        // Assert
        assertFalse(rangeActuatorFractionalValue.setValue("2"));
    }

    @Test
    void setMeasurementValueSmallerThanBoundary()
    {
        // Arrange
        RangeActuatorFractionalValue rangeActuatorFractionalValue = new RangeActuatorFractionalValue(lowerLimit, upperLimit);

        // Assert
        assertFalse(rangeActuatorFractionalValue.setValue("-2"));
    }
}