package SmartHome.domain.actuators.implementation;

import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.ValueFactory;
import SmartHome.domain.valueObjects.values.ValueFactoryImpl;
import SmartHome.domain.valueObjects.values.implementation.RangeActuatorFractionalValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RangeActuatorDecimalTest {
    private ActuatorName nameDouble;
    private double lowerLimit;
    private double upperLimit;
    private double precision;
    private RangeActuatorDecimal rangeActuatorDecimalDouble;
    private RangeActuatorFractionalValue rangeActuatorFractionalValueDouble;
    private ValueFactory valueFactoryDouble;
    private ActuatorId idDouble;
    private DeviceID deviceIdDouble;
    private ActuatorTypeId actuatorTypeIdDouble;

    private ActuatorName name;
    private RangeActuatorDecimal rangeActuatorDecimal;
    private ValueFactory valueFactory;
    private ActuatorId id;
    private DeviceID deviceId;
    private ActuatorTypeId actuatorTypeId;
    private LimitFractional limitFractional;
    private ActuatorModel actuatorModel;

    @BeforeEach
    void setup() {
        deviceIdDouble = mock(DeviceID.class);
        nameDouble = mock(ActuatorName.class);
        idDouble = mock(ActuatorId.class);
        actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        actuatorModel = new ActuatorModel("RangeActuatorDecimal");

        lowerLimit = -1.0;
        upperLimit = 1.0;
        precision = 0.01;
        long actId = 1L;
        long devId = 1L;

        String actName = "act1";
        String actTypeId = "Temp";
        limitFractional = new LimitFractional(lowerLimit, upperLimit);

        rangeActuatorFractionalValueDouble = mock(RangeActuatorFractionalValue.class);
        valueFactoryDouble = mock(ValueFactory.class);
        when(valueFactoryDouble.createRangeActuatorDecimal(lowerLimit, upperLimit)).thenReturn(rangeActuatorFractionalValueDouble);

        rangeActuatorDecimalDouble = new RangeActuatorDecimal(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble,
                limitFractional, actuatorModel);

        rangeActuatorDecimalDouble.configureActuator(lowerLimit, upperLimit, precision, valueFactoryDouble);

        valueFactory = new ValueFactoryImpl();
        id = new ActuatorId(actId);
        deviceId = new DeviceID(devId);
        name = new ActuatorName(actName);
        actuatorTypeId = new ActuatorTypeId(actTypeId);
        rangeActuatorDecimal = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId, limitFractional, actuatorModel);
        rangeActuatorDecimal.configureActuator(lowerLimit, upperLimit, precision, valueFactory);
    }

    @Test
    void actuatorNameNull() {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorDecimal(deviceIdDouble, null, idDouble, actuatorTypeIdDouble,
                        limitFractional, actuatorModel));

        // Assert
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void actuatorIdNull() {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorDecimal(deviceIdDouble, nameDouble, null, actuatorTypeIdDouble,
                        limitFractional, actuatorModel));

        // Assert
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    void actuatorDeviceIDNull() {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorDecimal(null, nameDouble, idDouble, actuatorTypeIdDouble,
                        limitFractional, actuatorModel));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void createRangeActuatorFractionalSuccessfully() {
        RangeActuatorDecimal rangeActuatorDecimal = new RangeActuatorDecimal(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, limitFractional, actuatorModel);
        assertNotNull(rangeActuatorDecimal);
    }

    @Test
    void configureRangeActuatorFractionalSuccessfully() {
        assertTrue(rangeActuatorDecimalDouble.configureActuator(lowerLimit, upperLimit, precision, valueFactoryDouble));
    }

    @Test
    void getName() {
        // Act
        ActuatorName result = rangeActuatorDecimalDouble.getName();
        ActuatorName expected = nameDouble;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getActuatorTypeName() {
        // Act
        ActuatorTypeId result = rangeActuatorDecimalDouble.getActuatorTypeId();
        ActuatorTypeId expected = actuatorTypeIdDouble;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getLowerLimit() {
        // Act
        double result = rangeActuatorDecimalDouble.getLowerLimit();
        double expected = -1.0;

        // Assert
        assertEquals(expected, result, 0.01);
    }


    @Test
    void getUpperLimit() {
        // Act
        double result = rangeActuatorDecimalDouble.getUpperLimit();
        double expected = 1.0;

        // Assert
        assertEquals(expected, result, 0.01);
    }

    @Test
    void setMeasurementValueInsideBoundaryUpperLimit() {
        // Arrange
        when(rangeActuatorFractionalValueDouble.setValue("0.99")).thenReturn(true);

        // Assert
        assertTrue(rangeActuatorDecimalDouble.setMeasurement("0.99"));
    }

    @Test
    void setMeasurementValueInsideBoundaryLowerLimit() {
        // Arrange
        when(rangeActuatorFractionalValueDouble.setValue("-0.99")).thenReturn(true);
        // Assert
        assertTrue(rangeActuatorDecimalDouble.setMeasurement("-0.99"));
    }

    @Test
    void setMeasurementValueBiggerThanBoundary() {
        // Arrange
        when(rangeActuatorFractionalValueDouble.toString()).thenReturn("2.0");

        // Assert
        assertFalse(rangeActuatorDecimalDouble.setMeasurement("2.0"));
    }

    @Test
    void setMeasurementValueSmallerThanBoundary() {
        // Arrange
        when(rangeActuatorFractionalValueDouble.toString()).thenReturn("-2.0");

        // Assert
        assertFalse(rangeActuatorDecimalDouble.setMeasurement("-2.0"));
    }

    @Test
    void setMeasurementValueStringNotDontConvertToNumber() {
        // Assert
        assertFalse(rangeActuatorDecimalDouble.setMeasurement("A"));
    }

    @Test
    void getPrecision() {
        // Act
        double result = rangeActuatorDecimalDouble.getPrecision();
        double expected = 0.01;

        // Assert
        assertEquals(expected, result, 0.001);
    }

    @Test
    void getPrecisionNegativeValue() {
        // Arrange
        precision = -0.01;

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> rangeActuatorDecimalDouble.configureActuator(lowerLimit, upperLimit, precision,
                        valueFactoryDouble));
    }

    @Test
    void setMeasurementThrowsNumberFormatException() {
        // Arrange
        String invalidMeasurement = "notANumber";
        when(rangeActuatorFractionalValueDouble.setValue(invalidMeasurement)).thenThrow(NumberFormatException.class);

        // Act
        boolean result = rangeActuatorDecimalDouble.setMeasurement(invalidMeasurement);

        // Assert
        assertFalse(result);
    }

    @Test
    void setMeasurementWithoutActuatorConfigured() {
        // Arrange
        RangeActuatorDecimal rangeAF = new RangeActuatorDecimal(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, limitFractional, actuatorModel);

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> rangeAF.setMeasurement("1.0"));
        assertEquals("Actuator not configured yet.", exception.getMessage());
    }

    @Test
    void validIdentity() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, limitFractional, actuatorModel);

        // Assert
        assertEquals(idDouble, rangeActuator.identity());
    }

    @Test
    void rangeActuatorIsNotSameAsDifferentObject() {
        // Arrange
        BlindRollerActuator blindRollerActuator = mock(BlindRollerActuator.class);
        RangeActuatorDecimal rangeActuator = mock(RangeActuatorDecimal.class);
        // Act
        boolean result = rangeActuator.sameAs(blindRollerActuator);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceId() {
        // Act
        DeviceID result = rangeActuatorDecimalDouble.getDeviceID();
        DeviceID expected = deviceIdDouble;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void sameAs_nullObject() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, limitFractional, actuatorModel);
        // Act
        boolean result = rangeActuator.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_sameObject() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, limitFractional, actuatorModel);

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator);

        // Assert
        assertTrue(result);
    }

    @Test
    void getMeasurementValidStringValue() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceIdDouble, nameDouble, idDouble,
                actuatorTypeIdDouble, limitFractional, actuatorModel);
        ValueFactory valueFactory = mock(ValueFactory.class);
        RangeActuatorFractionalValue value = mock(RangeActuatorFractionalValue.class);
        when(valueFactory.createRangeActuatorDecimal(lowerLimit, upperLimit)).thenReturn(value);
        when(value.toString()).thenReturn("0.0");
        RangeActuatorFractionalValue rangeActuatorFractionalValue = mock(RangeActuatorFractionalValue.class);
        when(rangeActuatorFractionalValue.toString()).thenReturn("0.0");

        rangeActuator.configureActuator(lowerLimit, upperLimit, precision, valueFactory);

        // Act
        String result = rangeActuator.getReading();
        String expected = "0.0";

        // Assert
        assertEquals(expected, result);
    }

    /**
     * integration tests
     **/
    @Test
    void actuatorNameNull_integration() {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorDecimal(deviceId, null, id, actuatorTypeId, limitFractional, actuatorModel));

        // Assert
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void actuatorIdNull_integration() {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorDecimal(deviceId, name, null, actuatorTypeId, limitFractional, actuatorModel));

        // Assert
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    void actuatorDeviceIDNull_integration() {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorDecimal(null, name, id, actuatorTypeId, limitFractional, actuatorModel));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void createRangeActuatorFractionalSuccessfully_integration() {
        RangeActuatorDecimal rangeActuatorDecimal = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);
        assertNotNull(rangeActuatorDecimal);
    }

    @Test
    void configureRangeActuatorFractionalSuccessfully_integration() {
        assertTrue(rangeActuatorDecimal.configureActuator(lowerLimit, upperLimit, precision, valueFactory));
    }

    @Test
    void getName_integration() {
        // Act
        ActuatorName result = rangeActuatorDecimal.getName();
        ActuatorName expected = name;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getActuatorTypeName_integration() {
        // Act
        ActuatorTypeId result = rangeActuatorDecimal.getActuatorTypeId();
        ActuatorTypeId expected = actuatorTypeId;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getLowerLimit_integration() {
        // Act
        double result = rangeActuatorDecimal.getLowerLimit();
        double expected = -1.0;

        // Assert
        assertEquals(expected, result, 0.01);
    }


    @Test
    void getUpperLimit_integration() {
        // Act
        double result = rangeActuatorDecimal.getUpperLimit();
        double expected = 1.0;

        // Assert
        assertEquals(expected, result, 0.01);
    }

    @Test
    void setMeasurementValueInsideBoundaryUpperLimit_integration() {
        // Assert
        assertTrue(rangeActuatorDecimal.setMeasurement("0.99"));
    }

    @Test
    void setMeasurementValueInsideBoundaryLowerLimit_integration() {
        // Assert
        assertTrue(rangeActuatorDecimal.setMeasurement("-0.99"));
    }

    @Test
    void setMeasurementValueBiggerThanBoundary_integration() {

        // Assert
        assertFalse(rangeActuatorDecimal.setMeasurement("2.0"));
    }

    @Test
    void setMeasurementValueSmallerThanBoundary_integration() {
        // Assert
        assertFalse(rangeActuatorDecimal.setMeasurement("-2.0"));
    }

    @Test
    void setMeasurementValueStringNotDontConvertToNumber_integration() {
        // Assert
        assertFalse(rangeActuatorDecimal.setMeasurement("A"));
    }

    @Test
    void getPrecision_integration() {
        // Act
        double result = rangeActuatorDecimal.getPrecision();
        double expected = 0.01;

        // Assert
        assertEquals(expected, result, 0.001);
    }

    @Test
    void setMeasurementThrowsNumberFormatException_integration() {
        // Arrange
        String invalidMeasurement = "notANumber";

        // Act
        boolean result = rangeActuatorDecimal.setMeasurement(invalidMeasurement);

        // Assert
        assertFalse(result);
    }

    @Test
    void setMeasurementWithoutActuatorConfigured_integration() {
        // Arrange
        RangeActuatorDecimal rangeAF = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> rangeAF.setMeasurement("1.0"));
        assertEquals("Actuator not configured yet.", exception.getMessage());
    }

    @Test
    void validIdentity_integration() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);

        // Assert
        assertEquals(id, rangeActuator.identity());
    }

    @Test
    void validDeviceId_integration() {
        // Act
        DeviceID result = rangeActuatorDecimal.getDeviceID();
        DeviceID expected = deviceId;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void sameAs_nullObject_integration() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);
        // Act
        boolean result = rangeActuator.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_sameObject_integration() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_DifferentLowerLimit_integration() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);
        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);
        rangeActuator.configureActuator(-2.0, 2.0, 0.1, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-1.0, 2.0, 0.1, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentUpperLimit_integration() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);
        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);
        rangeActuator.configureActuator(-2.0, 2.0, 0.1, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2.0, 1.0, 0.1, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentPrecision_integration() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);
        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);
        rangeActuator.configureActuator(-2.0, 2.0, 0.1, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2.0, 2.0, 0.2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

//    @Test
//    void sameAs_DifferentId_integration() {
//        // Arrange
//        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
//                limitFractional, actuatorModel);
//        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(deviceId, name, new ActuatorId(2L),
//                actuatorTypeId, limitFractional, actuatorModel);
//        rangeActuator.configureActuator(-2.0, 2.0, 0.1, new ValueFactoryImpl());
//        rangeActuator1.configureActuator(-2.0, 2.0, 0.1, new ValueFactoryImpl());
//
//        // Act
//        boolean result = rangeActuator.sameAs(rangeActuator1);
//
//        // Assert
//        assertFalse(result);
//    }

    @Test
    void sameAs_SameMeasurementObject_integration() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);
        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertTrue(result);
    }

    @Test
    void getModel() {
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);

        assertEquals("RangeActuatorDecimal", rangeActuator.getActuatorModel().model);
    }

    @Test
    void sameAs_DifferentClassObject()
    {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id, actuatorTypeId,
                limitFractional, actuatorModel);
        Object differentClassObject = new Object();

        // Act
        boolean result = rangeActuator.sameAs(differentClassObject);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentLowerLimit() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id,
                actuatorTypeId, limitFractional, actuatorModel);
        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(deviceId, name, id,
                actuatorTypeId, new LimitFractional(-2.0, 1.0), actuatorModel);

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentUpperLimit() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id,
                actuatorTypeId, limitFractional, actuatorModel);
        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(deviceId, name, id,
                actuatorTypeId, new LimitFractional(-1.0, 2.0), actuatorModel);

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentPrecision() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id,
                actuatorTypeId, limitFractional, actuatorModel);
        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(deviceId, name, id,
                actuatorTypeId, limitFractional, actuatorModel);
        rangeActuator.configureActuator(-2.0, 2.0, 0.1, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2.0, 2.0, 0.2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

//    @Test
//    void sameAs_DifferentId() {
//        // Arrange
//        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id,
//                actuatorTypeId, limitFractional, actuatorModel);
//        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(deviceId, name, new ActuatorId(2L),
//                actuatorTypeId, limitFractional, actuatorModel);
//
//        // Act
//        boolean result = rangeActuator.sameAs(rangeActuator1);
//
//        // Assert
//        assertFalse(result);
//    }

    @Test
    void sameAs_DifferentDeviceId() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id,
                actuatorTypeId, limitFractional, actuatorModel);
        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(new DeviceID(2L), name, id,
                actuatorTypeId, limitFractional, actuatorModel);

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentPrecision2() {
        // Arrange
        RangeActuatorDecimal rangeActuator = new RangeActuatorDecimal(deviceId, name, id,
                actuatorTypeId, limitFractional, actuatorModel);
        RangeActuatorDecimal rangeActuator1 = new RangeActuatorDecimal(new DeviceID(2L), name, id,
                actuatorTypeId, limitFractional, actuatorModel);
        rangeActuator.configureActuator(limitFractional.lowerLimit, limitFractional.upperLimit, 1.0, new ValueFactoryImpl());
        rangeActuator1.configureActuator(limitFractional.lowerLimit, limitFractional.upperLimit, 2.0, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

}