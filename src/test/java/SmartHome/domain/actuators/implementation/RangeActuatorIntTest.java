package SmartHome.domain.actuators.implementation;

import SmartHome.domain.valueObjects.*;
import SmartHome.domain.valueObjects.values.ValueFactory;
import SmartHome.domain.valueObjects.values.ValueFactoryImpl;
import SmartHome.domain.valueObjects.values.implementation.RangeActuatorIntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RangeActuatorIntTest
{
    private ActuatorName nameDouble;
    private ActuatorId idDouble;
    private int lowerLimit;
    private int upperLimit;
    private RangeActuatorInt rangeActuatorIntDouble;
    private RangeActuatorIntValue valueDouble;
    private ValueFactory valueFactoryDouble;
    private DeviceID deviceIdDouble;
    private ActuatorTypeId actuatorTypeIdDouble;
    private LimitInt limitIntDouble;
    private ActuatorModel actuatorModel;

    private ActuatorName name;
    private ActuatorId id;
    private RangeActuatorInt rangeActuatorInt;
    private ValueFactory valueFactory;
    private DeviceID deviceId;
    private ActuatorTypeId actuatorTypeId;
    private LimitInt limitInt;

    @BeforeEach
    void setup()
    {
        deviceIdDouble = mock(DeviceID.class);
        nameDouble = mock(ActuatorName.class);
        idDouble = mock(ActuatorId.class);
        actuatorTypeIdDouble = mock(ActuatorTypeId.class);
        long devId = 1L;
        /**
         * Aggregate integration tests
         **/
        long actId = 1L;
        String actName = "RangeActuatorInt";
        lowerLimit = -1;
        upperLimit = 1;
        actuatorModel = new ActuatorModel(actName);

        valueDouble = mock(RangeActuatorIntValue.class);
        valueFactoryDouble = mock(ValueFactory.class);
        limitIntDouble = mock(LimitInt.class);
        when(valueFactoryDouble.createRangeActuatorInt(lowerLimit, upperLimit)).thenReturn(valueDouble);

        rangeActuatorIntDouble = new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble,
                actuatorModel, limitIntDouble);

        rangeActuatorIntDouble.configureActuator(lowerLimit, upperLimit, valueFactoryDouble);

        id = new ActuatorId(actId);
        name = new ActuatorName(actName);
        deviceId = new DeviceID(devId);
        String actTypeId = "Temp";
        actuatorTypeId = new ActuatorTypeId(actTypeId);
        limitInt = new LimitInt(-1, 1);
        rangeActuatorInt = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);
        valueFactory = new ValueFactoryImpl();
        rangeActuatorInt.configureActuator(lowerLimit, upperLimit, valueFactory);
    }

    @Test
    void actuatorNameNull()
    {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorInt(deviceIdDouble, null, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble));

        // Assert
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void actuatorIdNull()
    {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorInt(deviceIdDouble, nameDouble, null, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble));

        // Assert
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    void actuatorDeviceIDNull()
    {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorInt(null, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void actuatorTypeNameNull()
    {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, null, actuatorModel,
                        limitIntDouble));

        // Assert
        assertEquals("ActuatorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void createRangeActuatorIntSuccessfully()
    {
        RangeActuatorInt rangeActuatorInt =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        assertNotNull(rangeActuatorInt);
    }

    @Test
    void configureRangeActuatorInSuccessfully()
    {
        assertTrue(rangeActuatorIntDouble.configureActuator(lowerLimit, upperLimit, valueFactoryDouble));
    }

    @Test
    void getName()
    {
        // Act
        ActuatorName result = rangeActuatorIntDouble.getName();
        ActuatorName expected = nameDouble;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getActuatorTypeName()
    {
        // Act
        ActuatorTypeId result = rangeActuatorIntDouble.getActuatorTypeId();
        ActuatorTypeId expected = actuatorTypeIdDouble;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getMeasurementValidStringValue()
    {
        // Arrange
        when(valueDouble.toString()).thenReturn("0");

        // Act
        String result = rangeActuatorIntDouble.getReading();
        String expected = "0";

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getLowerLimit()
    {
        // Act
        int result = rangeActuatorIntDouble.getLowerLimit();
        int expected = -1;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getUpperLimit()
    {
        // Act
        int result = rangeActuatorIntDouble.getUpperLimit();
        int expected = 1;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void setMeasurementValueInsideBoundary()
    {
        when(valueDouble.setValue("0")).thenReturn(true);

        // Assert
        assertTrue(rangeActuatorIntDouble.setMeasurement("0"));
    }

    @Test
    void setMeasurementValueBiggerThanBoundary()
    {
        when(valueDouble.setValue("2")).thenReturn(false);
        // Assert
        assertFalse(rangeActuatorIntDouble.setMeasurement("2"));
    }

    @Test
    void setMeasurementValueSmallerThanBoundary()
    {
        when(valueDouble.setValue("-2")).thenReturn(false);

        // Assert
        assertFalse(rangeActuatorIntDouble.setMeasurement("-2"));
    }

    @Test
    void setMeasurementValueStringDontConvertToNumber()
    {
        when(valueDouble.setValue("A")).thenReturn(false);

        // Assert
        assertFalse(rangeActuatorIntDouble.setMeasurement("A"));
    }

    @Test
    void setMeasurementThrowsNumberFormatException()
    {
        // Arrange
        String invalidMeasurement = "notANumber";
        when(valueDouble.setValue(invalidMeasurement)).thenThrow(NumberFormatException.class);

        // Act
        boolean result = rangeActuatorIntDouble.setMeasurement(invalidMeasurement);

        // Assert
        assertFalse(result);
    }

    @Test
    void setMeasurementWithoutActuatorConfigured()
    {
        // Arrange
        RangeActuatorInt rangeAI =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> rangeAI.setMeasurement("1"));
        assertEquals("Actuator not configured yet.", exception.getMessage());
    }

    @Test
    void validIdentity()
    {
        // Arrange
        RangeActuatorInt rangeActuator =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);

        // Act
        when(idDouble.toString()).thenReturn("1");
        ActuatorId result = rangeActuator.identity();

        // Assert
        assertEquals(idDouble, result);
    }

    @Test
    void rangeActuatorIsNotSameAsDifferentObject()
    {
        // Arrange
        BlindRollerActuator blindRollerActuator = mock(BlindRollerActuator.class);
        RangeActuatorInt rangeActuator =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);

        // Act
        boolean result = rangeActuator.sameAs(blindRollerActuator);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceId()
    {
        // Act
        DeviceID result = rangeActuatorIntDouble.getDeviceID();
        DeviceID expected = deviceIdDouble;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void sameAs_nullObject()
    {
        // Arrange
        RangeActuatorInt rangeActuator =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        // Act
        boolean result = rangeActuator.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_sameObject()
    {
        // Arrange
        RangeActuatorInt rangeActuator =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_DifferentLowerLimit()
    {
        // Arrange
        RangeActuatorInt rangeActuator =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        RangeActuatorInt rangeActuator1 =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-1, 2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentUpperLimit()
    {
        // Arrange
        RangeActuatorInt rangeActuator =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        RangeActuatorInt rangeActuator1 =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2, 1, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentName()
    {
        // Arrange
        RangeActuatorInt rangeActuator =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        RangeActuatorInt rangeActuator1 =
                new RangeActuatorInt(deviceIdDouble, new ActuatorName("name"), idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2, 2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentId()
    {
        // Arrange
        RangeActuatorInt rangeActuator =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        RangeActuatorInt rangeActuator1 =
                new RangeActuatorInt(deviceIdDouble, nameDouble, new ActuatorId(2L), actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2, 2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentActuatorTypeId()
    {
        // Arrange
        RangeActuatorInt rangeActuator =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        RangeActuatorInt rangeActuator1 =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, new ActuatorTypeId("d"), actuatorModel,
                        limitIntDouble);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2, 2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentDeviceId()
    {
        // Arrange
        RangeActuatorInt rangeActuator =
                new RangeActuatorInt(deviceIdDouble, nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        RangeActuatorInt rangeActuator1 =
                new RangeActuatorInt(new DeviceID(54), nameDouble, idDouble, actuatorTypeIdDouble, actuatorModel,
                        limitIntDouble);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2, 2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    /**
     * Aggregate integration tests
     **/
    @Test
    void actuatorNameNull_integration()
    {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorInt(deviceId, null, id, actuatorTypeId, actuatorModel, limitInt));

        // Assert
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    void actuatorIdNull_integration()
    {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorInt(deviceId, name, null, actuatorTypeId, actuatorModel, limitInt));

        // Assert
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    void actuatorDeviceIDNull_integration()
    {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorInt(null, name, id, actuatorTypeId, actuatorModel, limitInt));

        // Assert
        assertEquals("DeviceID cannot be null", exception.getMessage());
    }

    @Test
    void actuatorTypeNameNull_integration()
    {
        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new RangeActuatorInt(deviceId, name, id, null, actuatorModel, limitInt));

        // Assert
        assertEquals("ActuatorTypeId cannot be null", exception.getMessage());
    }

    @Test
    void createRangeActuatorIntSuccessfully_integration()
    {
        RangeActuatorInt rangeActuatorInt = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel,
                limitInt);
        assertNotNull(rangeActuatorInt);
    }

    @Test
    void configureRangeActuatorInSuccessfully_integration()
    {
        assertTrue(rangeActuatorInt.configureActuator(lowerLimit, upperLimit, valueFactory));
    }

    @Test
    void getName_integration()
    {
        // Act
        ActuatorName result = rangeActuatorInt.getName();
        ActuatorName expected = name;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getActuatorTypeName_integration()
    {
        // Act
        ActuatorTypeId result = rangeActuatorInt.getActuatorTypeId();
        ActuatorTypeId expected = actuatorTypeId;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getLowerLimit_integration()
    {
        // Act
        int result = rangeActuatorInt.getLowerLimit();
        int expected = -1;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void getUpperLimit_integration()
    {
        // Act
        int result = rangeActuatorInt.getUpperLimit();
        int expected = 1;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void setMeasurementValueInsideBoundary_integration()
    {
        // Assert
        assertTrue(rangeActuatorInt.setMeasurement("0"));
    }

    @Test
    void setMeasurementValueBiggerThanBoundary_integration()
    {
        // Assert
        assertFalse(rangeActuatorInt.setMeasurement("2"));
    }

    @Test
    void setMeasurementValueSmallerThanBoundary_integration()
    {
        // Assert
        assertFalse(rangeActuatorInt.setMeasurement("-2"));
    }

    @Test
    void setMeasurementValueStringDontConvertToNumber_integration()
    {
        // Assert
        assertFalse(rangeActuatorInt.setMeasurement("A"));
    }

    @Test
    void setMeasurementThrowsNumberFormatException_integration()
    {
        // Arrange
        String invalidMeasurement = "notANumber";

        // Act
        boolean result = rangeActuatorInt.setMeasurement(invalidMeasurement);

        // Assert
        assertFalse(result);
    }

    @Test
    void setMeasurementWithoutActuatorConfigured_integration()
    {
        // Arrange
        RangeActuatorInt rangeAI = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> rangeAI.setMeasurement("1"));
        assertEquals("Actuator not configured yet.", exception.getMessage());
    }

    @Test
    void validIdentity_integration()
    {
        // Arrange
        RangeActuatorInt rangeActuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel,
                limitInt);

        // Act
        ActuatorId result = rangeActuator.identity();

        // Assert
        assertEquals(id, result);
    }

    @Test
    void rangeActuatorIsNotSameAsDifferentObject_integration()
    {
        // Arrange
        BlindRollerActuator blindRollerActuator = mock(BlindRollerActuator.class);
        RangeActuatorInt rangeActuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);

        // Act
        boolean result = rangeActuator.sameAs(blindRollerActuator);

        // Assert
        assertFalse(result);
    }

    @Test
    void validDeviceId_integration()
    {
        // Act
        DeviceID result = rangeActuatorInt.getDeviceID();
        DeviceID expected = deviceId;

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void sameAs_nullObject_integration()
    {
        // Arrange
        RangeActuatorInt rangeActuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel,
                limitInt);
        // Act
        boolean result = rangeActuator.sameAs(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_sameObject_integration()
    {
        // Arrange
        RangeActuatorInt rangeActuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator);

        // Assert
        assertTrue(result);
    }

    @Test
    void sameAs_DifferentLowerLimit_integration()
    {
        // Arrange
        RangeActuatorInt rangeActuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);
        RangeActuatorInt rangeActuator1 = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-1, 2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentUpperLimit_integration()
    {
        // Arrange
        RangeActuatorInt rangeActuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);
        RangeActuatorInt rangeActuator1 = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2, 1, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentName_integration()
    {
        // Arrange
        RangeActuatorInt rangeActuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);
        RangeActuatorInt rangeActuator1 =
                new RangeActuatorInt(deviceId, new ActuatorName("name"), id, actuatorTypeId, actuatorModel, limitInt);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2, 2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentId_integration()
    {
        // Arrange
        RangeActuatorInt rangeActuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);
        RangeActuatorInt rangeActuator1 =
                new RangeActuatorInt(deviceId, name, new ActuatorId(2L), actuatorTypeId, actuatorModel, limitInt);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2, 2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void sameAs_DifferentActuatorTypeId_integration()
    {
        // Arrange
        RangeActuatorInt rangeActuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel, limitInt);
        RangeActuatorInt rangeActuator1 =
                new RangeActuatorInt(deviceId, name, id, new ActuatorTypeId("d"), actuatorModel, limitInt);
        rangeActuator.configureActuator(-2, 2, new ValueFactoryImpl());
        rangeActuator1.configureActuator(-2, 2, new ValueFactoryImpl());

        // Act
        boolean result = rangeActuator.sameAs(rangeActuator1);

        // Assert
        assertFalse(result);
    }

    @Test
    void getModel() {
        RangeActuatorInt rangeActuator = new RangeActuatorInt(deviceId, name, id, actuatorTypeId, actuatorModel,
                new LimitInt(-1, 1));

        assertEquals("RangeActuatorInt", rangeActuator.getActuatorModel().model);
    }
}