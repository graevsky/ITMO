package org.lab5.commsAndExecution.comms;

import org.lab5.OBJECTS.*;
import org.lab5.commsAndExecution.DragonCheck;
import org.lab5.commsAndExecution.comms.assistants.Adder;


/**
 * This is the block, that handles Add command and its arguments
 */
public class Add implements CommInterface {
    private final DragonPriorityQueue dragonQueue;
    /**
     * @param dragonQueue is the Dragon PriorityQueue, that should be modified
     */
    public Add(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }
    /**
     * Override for the command name
     *
     * @return add - name of the command
     */
    @Override
    public String name() {
        return "add";
    }
    /**
     * Override for the command description
     *
     * @return description of the add command
     */
    @Override
    public String descr() {
        return "Adding dragon:Name X_Coord Y_Coord Age Color Type Character Head_Size";
    }
    /**
     * This block handles the process of adding dragon to the Dragon PriorityQueue
     *
     * @param arguments are the parameters of the dragon
     * @return true, if command was executed correctly and false if not
     */
    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length < 7) {
            System.out.println("Params:Name(String) X_Coord(Float) Y_Coord(Integer) Age(Integer) Color(RED,WHITE,YELLOW) Type(WATER,UNDERGORUND,AIR,FIRE) Character(CUNNING,WISE,EVIL,GOOD) Head_Size(Float)");
            return false;
        }
        Dragon dragon;
        try {
            dragon = Adder.createDragon(arguments);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        try {
            DragonCheck.checkDragon(dragon);
        } catch (DragonCheck.DragonParseException ex) {
            System.out.println("Dragon was not validated: " + ex.getMessage());
            return false;
        }
        try {
            dragonQueue.add(dragon);
            System.out.println("Dragon was added");
            return true;
        } catch (Exception e) {
            System.out.println("Dragon was not added");
            return false;
        }
    }
}
