package org.example.Human;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.Buildings.AbstractBuilding;
import org.example.Locations.Location;
import org.example.Things.AbstractThing;

import java.util.ArrayList;

@EqualsAndHashCode
@ToString
public abstract class AbstractHuman implements HumanInterface {
    protected String name;
    protected int age;
    protected Location location;
    protected AbstractBuilding house;

    protected ArrayList<AbstractThing> inventory = new ArrayList<>();


    public AbstractHuman(String name, int age, Location location, AbstractBuilding house) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.house = house;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }


    @Override
    public AbstractBuilding getBuilding() {
        return house;
    }

    @Override
    public void getInventory() {
        for (AbstractThing abstractThing : inventory) {
            System.out.print("Item: " + abstractThing.getName() + " amount: " + abstractThing.getAmount() + "; ");
        }
    }

    public void removeFromInventory(AbstractThing thing) {
        inventory.remove(thing);
    }


    public void addThing(AbstractThing thing) {
        inventory.add(thing);
    }


    public Location getCurrentLocation() {
        return location;
    }

    public void setCurrentLocation(Location setLocation) {
        System.out.println(name + " moved to " + setLocation.getLocationName() + " from " + location.getLocationName());
        location = setLocation;
    }
}
///TODO:ADD HOUSE FOR HUMAN
