package SmartHome.controller.cli_controllers;

import SmartHome.mapper.MaxInstantTempDiffDTO;
import SmartHome.mapper.TimePeriodDTO;
import SmartHome.service.EnvironmentalTemperatureService;

public class Ctrl34GetMaxInstantaneousTempDifOutsideInside
{
    private final EnvironmentalTemperatureService environmentalTemperatureService;

    public Ctrl34GetMaxInstantaneousTempDifOutsideInside(EnvironmentalTemperatureService environmentalTemperatureService) {
        this.environmentalTemperatureService = environmentalTemperatureService;
    }

    public MaxInstantTempDiffDTO getMaximumTemperatureDifference(long deviceID,
                                                                 TimePeriodDTO timePeriodDTO, long maximumSecondsAllowedBetweenReadings) {
        double result = environmentalTemperatureService.getMaximumTemperatureDifference(deviceID, timePeriodDTO.startDate, timePeriodDTO.endDate, maximumSecondsAllowedBetweenReadings);

        return new MaxInstantTempDiffDTO(result);
    }
}
