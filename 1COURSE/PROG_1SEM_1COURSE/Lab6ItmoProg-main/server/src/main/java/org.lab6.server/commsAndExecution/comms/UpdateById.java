package org.lab6.server.commsAndExecution.comms;


import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;

import org.lab6.server.commsAndExecution.comms.assistants.Adder;

/**
 * This is the block, that handles UpdateById command and its arguments
 */
public class UpdateById implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    /**
     * @param dragonQueue is the Dragon PriorityQueue, that should be modified
     */
    public UpdateById(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    /**
     * Override for the command name
     *
     * @return update_by_id * name of the command
     */
    @Override
    public String name() {
        return "update_id";
    }

    /**
     * Override for the command description
     *
     * @return description of the update_by_id command
     */
    @Override
    public String descr() {
        return "Updates dragon with this id. Params:Id(Integer) Name(String) X_Coord(Float) Y_Coord(Integer) Age(Integer) Color(RED,WHITE,YELLOW) Type(WATER,UNDERGORUND,AIR,FIRE) Character(CUNNING,WISE,EVIL,GOOD) Head_Size(Float)";
    }

    /**
     * This block handles the Dragon PriorityQueue change process
     *
     * @param command are the parameters of the dragon, that should be updated
     * @return true, if command was executed correctly and false if not
     */
    @Override
    public ResultData execute(CommData command) {
        if (command.getArgsLen() < 8) {
            return new ResultData("","Invalid arguments. Usage:Id(Integer) Name(String) X_Coord(Float) Y_Coord(Integer) Age(Integer) Color(RED,WHITE,YELLOW) Type(WATER,UNDERGORUND,AIR,FIRE) Character(CUNNING,WISE,EVIL,GOOD) Head_Size(Float)",0);
        }
        String[] args = command.getArgs().split(" ");
        long id;
        try{
            id = Long.parseLong(args[0]);
        }catch (NumberFormatException e){
            return new ResultData("","Id is not integer: " + e.getMessage(),0);
        }
        Dragon dragonToUpdate = null;
        for (Dragon dragon : dragonQueue) {
            if (dragon.getId()== id) {
                dragonToUpdate = dragon;
                break;
            }
        }
        if (dragonToUpdate != null) {
            String[] dragonArguments = new String[args.length - 1];
            System.arraycopy(args, 1, dragonArguments, 0, args.length - 1);
            try{
                Adder.setDragon(dragonArguments, dragonToUpdate);
            }catch (Adder.DragonParseException e){
                return new ResultData("","Dragon could not be updated: " +e.getMessage(),0);
            }
            return new ResultData("Dragon updated","",1);
        } else {
            return new ResultData("","Dragon with ID" + id + " not found. Check the ID.",0);
        }
    }
}
