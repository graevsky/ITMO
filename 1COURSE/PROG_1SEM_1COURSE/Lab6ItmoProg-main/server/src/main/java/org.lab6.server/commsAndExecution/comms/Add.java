package org.lab6.server.commsAndExecution.comms;


import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;

import org.lab6.server.commsAndExecution.DragonCheck;
import org.lab6.server.commsAndExecution.comms.assistants.Adder;

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
     * @return add - name of the command
     */
    @Override
    public String name() {
        return "add";
    }

    /**
     * Override for the command description
     * @return description of the add command
     */
    @Override
    public String descr() {
        return "Adding dragon:Name X_Coord Y_Coord Age Color Type Character Head_Size";
    }

    /**
     * This block handles the process of adding dragon to the Dragon PriorityQueue
     * @param command are the parameters of the dragon
     * @return true, if command was executed correctly and false if not
     */
    @Override
    public ResultData execute(CommData command) {
        if (command.getArgsLen() < 7) {
            return new ResultData("","Not enough arguments. Usage:Params:Name(String) X_Coord(Float) Y_Coord(Integer) Age(Integer) Color(RED,WHITE,YELLOW)" +
                    " Type(WATER,UNDERGORUND,AIR,FIRE) Character(CUNNING,WISE,EVIL,GOOD) Head_Size(Float)",0);
        }
        Dragon dragon;
        try{
            dragon = Adder.createDragon(command);
        }catch( Exception e) {
            String resulString = "Dragon could not be created: " + e.getMessage();
            return new ResultData("",resulString,0);
        }
        try {
            DragonCheck.checkDragon(dragon);
        } catch (DragonCheck.DragonParseException ex) {
            String errorString = "Dragon was not validated: " + ex.getMessage();
            return new ResultData("",errorString,0);
        }
        try {
            dragonQueue.add(dragon);
            return new ResultData("Dragon was added!","",1);

        } catch (NullPointerException | ClassCastException | IllegalAccessError e) {
            String errorString = "Dragon was not added: " + e.getMessage();
            return new ResultData("",errorString,0);
        }
    }
}
