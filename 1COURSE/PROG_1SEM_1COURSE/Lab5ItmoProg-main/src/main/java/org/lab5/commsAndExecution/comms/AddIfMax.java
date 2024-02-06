package org.lab5.commsAndExecution.comms;

import org.lab5.commsAndExecution.DragonCheck;
import org.lab5.OBJECTS.Dragon;
import org.lab5.OBJECTS.DragonPriorityQueue;
import org.lab5.commsAndExecution.comms.assistants.Adder;

import java.util.Objects;

public class AddIfMax implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    public AddIfMax(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    @Override
    public String name() {
        return "add_if_max";
    }

    @Override
    public String descr() {
        return "Adds dragon, comparison by head size. Params:Name(String) X_Coord(Float) Y_Coord(Integer) Age(Integer) Color(RED,WHITE,YELLOW) Type(WATER,UNDERGORUND,AIR,FIRE) Character(CUNNING,WISE,EVIL,GOOD) Head_Size(Float)";
    }

    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length < 7) {
            System.out.println("Invalid arguments.");
            return false;
        }


        Dragon dragon = null;
        try{
            dragon = Adder.createDragon(arguments);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        try {
            DragonCheck.checkDragon(dragon);
        } catch (DragonCheck.DragonParseException ex) {
            System.out.println("Dragon was not validated: " + ex.getMessage());
            return false;
        }


        if (dragonQueue.isEmpty() || dragon.getHead().getSize() > Objects.requireNonNull(dragonQueue.peek()).getHead().getSize()) {
            dragonQueue.add(dragon);
            System.out.println("Dragon was added.");
            return true;
        } else {
            System.out.println("Dragon was not added.");
            return false;
        }
    }
}
