package org.example.Buildings;

import org.example.Locations.Streets.AbstractStreet;

public class Building extends AbstractBuilding {
    public Building(String adress, AbstractStreet street) {
        super(adress, street);
    }
}
