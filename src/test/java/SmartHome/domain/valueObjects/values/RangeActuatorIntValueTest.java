package SmartHome.domain.valueObjects.values;

import SmartHome.domain.valueObjects.values.implementation.RangeActuatorIntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeActuatorIntValueTest {
    private int lowerLimit;
    private int upperLimit;

    @BeforeEach
    void setup() {
        lowerLimit = -1;
        upperLimit = 1;
    }

    @Test
    void createRangeActuatorIntValueSuccessfully() {
        RangeActuatorIntValue rangeActuatorIntValue = new RangeActuatorIntValue(lowerLimit, upperLimit);
        assertNotNull(rangeActuatorIntValue);
    }

    @Test
    void setValueRangeActuatorIntValue() {
        // Arrange
        String measured = "0";

        RangeActuatorIntValue rangeActuatorIntValue = new RangeActuatorIntValue(lowerLimit, upperLimit);

        // Assert
        assertTrue(rangeActuatorIntValue.setValue(measured));
    }

    @Test
    void setMeasurementValueInsideBoundary() {
        // Arrange
        RangeActuatorIntValue rangeActuatorIntValue = new RangeActuatorIntValue(lowerLimit, upperLimit);

        // Assert
        assertTrue(rangeActuatorIntValue.setValue("0"));
    }

    @Test
    void setMeasurementValueEqualsToUpperToBoundary() {
        // Arrange
        RangeActuatorIntValue rangeActuatorIntValue = new RangeActuatorIntValue(lowerLimit, upperLimit);

        // Assert
        assertTrue(rangeActuatorIntValue.setValue("1"));
    }

    @Test
    void setMeasurementValueEqualsToLowerToBoundary() {
        // Arrange
        RangeActuatorIntValue rangeActuatorIntValue = new RangeActuatorIntValue(lowerLimit, upperLimit);

        // Assert
        assertTrue(rangeActuatorIntValue.setValue("-1"));
    }

    @Test
    void setMeasurementValueBiggerThanBoundary() {
        // Arrange
        RangeActuatorIntValue rangeActuatorIntValue = new RangeActuatorIntValue(lowerLimit, upperLimit);

        // Assert
        assertFalse(rangeActuatorIntValue.setValue("2"));
    }

    @Test
    void setMeasurementValueSmallerThanBoundary() {
        // Arrange
        RangeActuatorIntValue rangeActuatorIntValue = new RangeActuatorIntValue(lowerLimit, upperLimit);

        // Assert
        assertFalse(rangeActuatorIntValue.setValue("-2"));
    }
}