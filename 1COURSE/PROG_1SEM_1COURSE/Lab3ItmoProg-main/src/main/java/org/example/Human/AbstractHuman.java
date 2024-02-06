package org.example.Human;

import org.example.Actions.AbstractAction;
import org.example.Actions.CrushBottlesAction;
import org.example.Locations.Location;
import org.example.Locations.LocationInterface;
import org.example.Things.AbstractThing;
import org.example.Things.Bottle;
import org.example.Things.Telescope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractHuman implements HumanInterface {
    protected String name;
    protected int age;
    protected Location location;

    protected ArrayList<AbstractThing> inventory = new ArrayList<>();



    public AbstractHuman(String name, int age,Location location) {
        this.name = name;
        this.age = age;
        this.location = location;
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
    public void setName(String humanName) {
        this.name = humanName;
    }

    @Override
    public void setAge(int humanAge) {
        this.age = humanAge;
    }
    @Override
    public void getInventory(){
        for (AbstractThing abstractThing : inventory) {
            System.out.print("Item: " + abstractThing.getName() + " amount: " + abstractThing.getAmount() +"; ");
        }
    }
    public void removeFromInventory(AbstractThing thing){
        inventory.remove(thing);
    }


    public void addThing(AbstractThing thing){
        inventory.add(thing);
    }


    public AbstractThing getItemFromInventory(Class<? extends AbstractThing> itemClass) {
        return inventory.stream()
                .filter(itemClass::isInstance)
                .findFirst()
                .orElse(null);
    }

    public Location getCurrentLocation() {
        return location;
    }

    public void setCurrentLocation(Location setLocation) {
        System.out.println(name + " moved to " + setLocation.getLocationName() + " from " + location.getLocationName());
        location = setLocation;
    }
}
