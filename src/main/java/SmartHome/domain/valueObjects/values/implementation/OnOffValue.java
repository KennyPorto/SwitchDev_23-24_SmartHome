package SmartHome.domain.valueObjects.values.implementation;

import SmartHome.domain.valueObjects.values.Value;

public class OnOffValue implements Value
{
    public final boolean isOn;

    public OnOffValue(boolean isOn)
    {
        this.isOn = isOn;
    }
}
