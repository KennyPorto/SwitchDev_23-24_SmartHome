package SmartHome.mapper;

import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
public class CloseBlindRollerDTO
{
    public long actuatorId;
    public String measurement;
    public ZonedDateTime timeStamp = ZonedDateTime.now();

    public CloseBlindRollerDTO(long actuatorId, String measurement)
    {
        this.actuatorId = actuatorId;
        this.measurement = measurement;
    }
}
