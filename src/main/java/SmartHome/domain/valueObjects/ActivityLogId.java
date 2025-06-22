package SmartHome.domain.valueObjects;

import SmartHome.ddd.DomainId;

import java.util.Objects;

public class ActivityLogId implements DomainId
{
    public long id;

    public ActivityLogId(long id)
    {
        if (!validateArguments(id)) throw new IllegalArgumentException("Id cannot be smaller than 1");
        this.id = id;
    }

    private boolean validateArguments(long id)
    {
        return id > 0;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ActivityLogId that = (ActivityLogId) object;

        return id == that.id;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
