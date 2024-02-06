package org.example.Buildings;

import org.example.Human.AbstractHuman;
import org.example.Locations.AbstractLocation;
import org.example.Locations.Streets.AbstractStreet;

public interface BuildingInterface {
    String getBuildingAdress();

    AbstractHuman getBuildingOwner();

    void setBuildingOwner(AbstractHuman human);

    AbstractLocation getBuildingLocation();

    AbstractStreet getBuildingStreet();

}
