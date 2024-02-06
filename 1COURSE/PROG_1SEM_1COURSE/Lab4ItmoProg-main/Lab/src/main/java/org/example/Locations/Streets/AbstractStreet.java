package org.example.Locations.Streets;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.Locations.Location;

@EqualsAndHashCode
@ToString
public abstract class AbstractStreet implements StreetInterface {
    protected String streetName;
    protected Location streetLocation;

    public AbstractStreet(String streetName, Location streetLocation) {
        this.streetName = streetName;
        this.streetLocation = streetLocation;
    }

    @Override
    public Location getStreetLocation() {
        return streetLocation;
    }

    @Override
    public String getStreetName() {
        return streetName;
    }
}
