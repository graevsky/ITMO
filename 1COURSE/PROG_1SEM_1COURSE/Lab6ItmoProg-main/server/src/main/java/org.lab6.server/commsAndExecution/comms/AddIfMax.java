package org.lab6.server.commsAndExecution.comms;



import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;

import org.lab6.server.commsAndExecution.DragonCheck;
import org.lab6.server.commsAndExecution.comms.assistants.Adder;

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
    public ResultData execute(CommData command) {

        if (command.getArgsLen() < 7) {
            return new ResultData("","Not enough arguments. Usage:Params:Name(String) X_Coord(Float) Y_Coord(Integer) Age(Integer) Color(RED,WHITE,YELLOW)" +
                    " Type(WATER,UNDERGORUND,AIR,FIRE) Character(CUNNING,WISE,EVIL,GOOD) Head_Size(Float)",0);
        }
        Dragon dragon = null;
        try{
            dragon = Adder.createDragon(command);
        }catch (Adder.DragonParseException e){
            return new ResultData("","Dragon could not be created: " + e.getMessage() ,0);
        }

        try {
            DragonCheck.checkDragon(dragon);
        } catch (DragonCheck.DragonParseException ex) {
            String errorString = "Dragon was not validated: " + ex.getMessage();
            return new ResultData("",errorString,0);
        }

        if (dragonQueue.isEmpty() || dragon.getHead().getSize() > Objects.requireNonNull(dragonQueue.peek()).getHead().getSize()) {
            try {
                dragonQueue.add(dragon);
            }catch (NullPointerException | ClassCastException | IllegalAccessError e) {
                String errorString = "Dragon was not added: " + e.getMessage();
                return new ResultData("", errorString, 0);
            }
            return new ResultData("Dragon was added!","",1);
        } else {
            return new ResultData("Dragon was not added!, because of head size!","",0);
        }
    }
}
