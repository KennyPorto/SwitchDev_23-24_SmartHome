package SmartHome.mapper;

import SmartHome.domain.actuators.Actuator;
import SmartHome.domain.actuators.ActuatorFactory;
import SmartHome.domain.actuators.implementation.RangeActuatorDecimal;
import SmartHome.domain.actuators.implementation.SwitchOnOffActuator;
import SmartHome.domain.valueObjects.*;
import SmartHome.persistence.jpa.datamodel.ActuatorDataModel;
import SmartHome.persistence.jpa.datamodel.ActuatorRangeDecimalDataModel;
import SmartHome.persistence.jpa.datamodel.ActuatorRangeIntDataModel;
import SmartHome.persistence.jpa.datamodel.BlindRollerDataModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorMapperTest {
    ActuatorId actuatorID;
    ActuatorName actuatorName;
    SwitchOnOffActuator onOffActuator;
    DeviceID deviceID;
    ActuatorTypeId actuatorTypeId;
    ActuatorModel actuatorModel;

    @Test
    void transformToDTO() {
        // Arrange
        actuatorID = new ActuatorId(1L);
        actuatorName = new ActuatorName("act1");
        actuatorTypeId = new ActuatorTypeId("type1");
        actuatorModel = new ActuatorModel("SwitchOnOffActuator");
        deviceID = new DeviceID(1L);
        onOffActuator = new SwitchOnOffActuator(deviceID, actuatorName, actuatorID, actuatorTypeId, actuatorModel);

        // Act
        ActuatorDTO actuatorDTO = ActuatorMapper.toDTO(onOffActuator);

        // Assert
        assertEquals(actuatorName.name, actuatorDTO.name);
        assertEquals(actuatorModel.model, actuatorDTO.model);
        assertEquals(deviceID.id, actuatorDTO.deviceId);
        assertEquals(actuatorTypeId.name, actuatorDTO.actuatorType);
        assertEquals(actuatorID.id, actuatorDTO.actuatorId);
    }

    @Test
    void transformToDTOList() {
        // Arrange
        onOffActuator = new SwitchOnOffActuator(new DeviceID(1L), new ActuatorName("act1"), new ActuatorId(1L),
                new ActuatorTypeId("type1"), new ActuatorModel("SwitchOnOffActuator"));

        RangeActuatorDecimal rangeActuatorDecimal = new RangeActuatorDecimal(new DeviceID(1L), new ActuatorName("act2"),
                new ActuatorId(1L), new ActuatorTypeId("range"), new LimitFractional(-1.0, 1.0),
                new ActuatorModel("RangeActuatorDecimal"));

        // Act
        Iterable<ActuatorDTO> result = ActuatorMapper.toDTO(
                List.of(onOffActuator, rangeActuatorDecimal)
        );

        Iterable<ActuatorDTO> expected = List.of(
                ActuatorMapper.toDTO(onOffActuator),
                ActuatorMapper.toDTO(rangeActuatorDecimal)
        );

        // Assert
        assertEquals(expected.iterator().next().name, result.iterator().next().name);
        assertEquals(expected.iterator().next().model, result.iterator().next().model);
        assertEquals(expected.iterator().next().deviceId, result.iterator().next().deviceId);
        assertEquals(expected.iterator().next().actuatorId, result.iterator().next().actuatorId);
        assertEquals(expected.iterator().next().actuatorType, result.iterator().next().actuatorType);
        assertEquals(2, ((List<ActuatorDTO>) result).size());
    }


    @Test
    void actuatorDataModelToDomain() {
        // Arrange
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);
        when(actuatorDataModel.getActuatorName()).thenReturn("name");
        when(actuatorDataModel.getActuatorId()).thenReturn(1L);
        when(actuatorDataModel.getActuatorTypeId()).thenReturn("type");
        when(actuatorDataModel.getDeviceId()).thenReturn(1L);
        when(actuatorDataModel.getActuatorModel()).thenReturn("SwitchOnOffActuator");


        // Act
        Actuator result = ActuatorMapper.dataModelToDomain(actuatorDataModel);

        // Assert
        assertEquals(1L, result.identity().id);
        assertEquals("name", result.getName().name);
        assertEquals("SwitchOnOffActuator", result.getActuatorModel().model);
        assertEquals(1L, result.getDeviceID().id);
        assertEquals("type", result.getActuatorTypeId().name);
    }


    @Test
    void transformDataModelListToDomainList() {
        // Arrange
        List<ActuatorDataModel> actuatorDataModels = new ArrayList<>();
        ActuatorDataModel actuatorDataModel = mock(ActuatorDataModel.class);
        actuatorDataModels.add(actuatorDataModel);
        when(actuatorDataModel.getActuatorName()).thenReturn("name");
        when(actuatorDataModel.getActuatorId()).thenReturn(1L);
        when(actuatorDataModel.getActuatorTypeId()).thenReturn("type");
        when(actuatorDataModel.getDeviceId()).thenReturn(1L);
        when(actuatorDataModel.getActuatorModel()).thenReturn("SwitchOnOffActuator");

        // Act
        Iterable<Actuator> result = ActuatorMapper.dataModelListToDomain(actuatorDataModels);

        // Assert
        assertEquals(1L, result.iterator().next().identity().id);
        assertEquals("name", result.iterator().next().getName().name);
        assertEquals("SwitchOnOffActuator", result.iterator().next().getActuatorModel().model);
        assertEquals(1L, result.iterator().next().getDeviceID().id);
        assertEquals("type", result.iterator().next().getActuatorTypeId().name);
    }

    @Test
    void toDtoRangeActuatorInt() {
        // Arrange
        DeviceID deviceID1 = new DeviceID(1L);
        ActuatorModel actuatorModel1 = new ActuatorModel("RangeActuatorInt");
        ActuatorName actuatorName1 = new ActuatorName("act1");
        ActuatorId actuatorId = new ActuatorId(1L);
        ActuatorTypeId actuatorTypeId1 = new ActuatorTypeId("range");
        LimitInt limitInt = new LimitInt(-1, 1);
        Actuator actuator = new ActuatorFactory().createActuator(deviceID1, actuatorModel1, actuatorName1, actuatorId,
                actuatorTypeId1, limitInt);

        // Act
        ActuatorDTO expected = new ActuatorDTO("act1", "RangeActuatorInt", 1L, "range",
                -1, 1);
        ActuatorDTO result = ActuatorMapper.toDTO(actuator);

        // Assert
        assertEquals(expected.name, result.name);
        assertEquals(expected.model, result.model);
    }

    @Test
    void actuatorDataModelToDomain_RangeInt() {
        // Arrange
        Actuator actuator = new ActuatorFactory().createActuator(new DeviceID(1), new ActuatorModel("RangeActuatorInt"),
                new ActuatorName("act1"), new ActuatorId(1), new ActuatorTypeId("range"), new LimitInt(-1, 1));
        ActuatorDataModel actuatorDataModel = new ActuatorRangeIntDataModel(actuator, -1, 1);

        // Act
        Actuator result = ActuatorMapper.dataModelToDomain(actuatorDataModel);

        // Assert
        assertEquals(1L, result.identity().id);
        assertEquals("act1", result.getName().name);
        assertEquals("RangeActuatorInt", result.getActuatorModel().model);
        assertEquals(1L, result.getDeviceID().id);
        assertEquals("range", result.getActuatorTypeId().name);
    }

    @Test
    void actuatorDataModelToDomain_RangeDecimal() {
        // Arrange
        Actuator actuator = new ActuatorFactory().createActuator(new DeviceID(1),
                new ActuatorModel("RangeActuatorDecimal"), new ActuatorName("act1"), new ActuatorId(1),
                new ActuatorTypeId("range"), new LimitFractional(-1.0, 1.0));

        ActuatorDataModel actuatorDataModel = new ActuatorRangeDecimalDataModel(actuator, -1.0, 1.0);

        // Act
        Actuator result = ActuatorMapper.dataModelToDomain(actuatorDataModel);

        // Assert
        assertEquals(1L, result.identity().id);
        assertEquals("act1", result.getName().name);
        assertEquals("RangeActuatorDecimal", result.getActuatorModel().model);
        assertEquals(1L, result.getDeviceID().id);
        assertEquals("range", result.getActuatorTypeId().name);
    }

    @Test
    void actuatorDataModelToDomain_BlindRoller() {
        // Arrange
        Actuator actuator = new ActuatorFactory().createActuator(new DeviceID(1),
                new ActuatorModel("BlindRollerActuator"), new ActuatorName("act1"), new ActuatorId(1),
                new ActuatorTypeId("blindRoller"));

        ActuatorDataModel actuatorDataModel = new BlindRollerDataModel(actuator, 100);

        // Act
        Actuator result = ActuatorMapper.dataModelToDomain(actuatorDataModel);

        // Assert
        assertEquals(1L, result.identity().id);
        assertEquals("act1", result.getName().name);
        assertEquals("BlindRollerActuator", result.getActuatorModel().model);
        assertEquals(1L, result.getDeviceID().id);
        assertEquals("blindRoller", result.getActuatorTypeId().name);
    }
}