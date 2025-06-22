package SmartHome.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import java.io.File;
import java.util.List;

public class Models {
    public List<String> getActuatorModels() throws Exception {
        Configurations configs = new Configurations();

        Configuration config = configs.properties(new File(Constants.ACTUATOR_CONFIG));
        String[] actuators = config.getStringArray("actuator");
        return List.of(actuators);
    }

    public List<String> getSensorModels() throws Exception {
        Configurations configs = new Configurations();

        Configuration config = configs.properties(new File(Constants.SENSOR_CONFIG));
        String[] sensors = config.getStringArray("sensor");
        return List.of(sensors);
    }
}
