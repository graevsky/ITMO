package org.example.Buildings;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.Human.AbstractHuman;
import org.example.Locations.AbstractLocation;
import org.example.Locations.Streets.AbstractStreet;

@EqualsAndHashCode
@ToString
public abstract class AbstractBuilding implements BuildingInterface {
    protected String adress;
    protected AbstractHuman owner;
    protected AbstractStreet street;

    public AbstractBuilding(String adress, AbstractStreet street) {
        this.adress = adress;
        this.street = street;
    }

    @Override
    public AbstractStreet getBuildingStreet() {
        return street;
    }

    @Override
    public String getBuildingAdress() {
        return adress;
    }

    @Override
    public AbstractHuman getBuildingOwner() {
        return owner;
    }

    @Override
    public void setBuildingOwner(AbstractHuman human) {
        this.owner = human;
    }

    @Override
    public AbstractLocation getBuildingLocation() {
        return street.getStreetLocation();
    }
}
