package SmartHome.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelsTest {
    @Test
    void getActuatorModels() throws Exception {
        List<String> expected = Arrays.asList("BlindRollerActuator", "SwitchOnOffActuator", "RangeActuatorInt",
                "RangeActuatorDecimal");
        List<String> result = new Models().getActuatorModels();

        assertEquals(expected.toString(), result.toString());
    }

    @Test
    void getSensorModels() throws Exception {
        List<String> expected = Arrays.asList("TemperatureSensor", "HumiditySensor", "ScaleSensor", "DewPointSensor",
                "SolarIrradianceSensor", "WindSensor", "InstantPowerConsumptionSensor", "AveragePowerConsumptionSensor",
                "ElectricEnergyConsumptionSensor", "SunriseSensor", "SunsetSensor", "BinarySwitch", "WindDirection");
        List<String> result = new Models().getSensorModels();

        assertEquals(expected.toString(), result.toString());
    }
}