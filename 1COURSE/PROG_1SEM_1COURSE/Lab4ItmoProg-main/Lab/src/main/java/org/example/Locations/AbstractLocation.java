package org.example.Locations;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class AbstractLocation implements LocationInterface {
    protected String locationName;
    protected String locationDescription;

    public AbstractLocation(String locationName, String locationDescription) {
        this.locationDescription = locationDescription;
        this.locationName = locationName;
    }

    @Override
    public String getLocationName() {
        return locationName;
    }

    @Override
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String getLocationDescription() {
        return locationDescription;
    }

    @Override
    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }
}
