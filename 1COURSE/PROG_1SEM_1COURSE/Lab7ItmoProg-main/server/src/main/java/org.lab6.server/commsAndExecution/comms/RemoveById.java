package org.lab6.server.commsAndExecution.comms;


import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;

import java.util.NoSuchElementException;

/**
 * This is the block, that handles RemoveById command and its arguments
 */
public class RemoveById implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    /**
     * @param dragonQueue is the Dragon PriorityQueue, that should be modified
     */
    public RemoveById(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    /**
     * Override for the command name
     *
     * @return remove_by_id - name of the command
     */
    @Override
    public String name() {
        return "remove_by_id";
    }

    /**
     * Override for the command description
     *
     * @return description of the remove_by_id command
     */
    @Override
    public String descr() {
        return "Removes dragon by its id. Params: ID(Integer)";
    }

    /**
     * This block handles the removal process
     *
     * @param command is the id of the dragon, that should be removed
     * @return true, if command was executed correctly and false if not
     */
    @Override
    public ResultData execute(CommData command) {
        try {
            String[] args = command.getArgs().split(" ");
            long id;
            try {
                id = Long.parseLong(args[0]);
            }catch (NumberFormatException e){
                return new ResultData("","Id should be integer!",0);
            }
            for (Dragon dragon : dragonQueue) {
                if (dragon.getId() == id) {
                    dragonQueue.remove(dragon);
                    return new ResultData("Dragon " + id + " exterminated!", "", 1);
                }
            }
            return new ResultData("", "Dragon with id " + id + " not found!!", 0);
        }catch (NoSuchElementException | ClassCastException  e){
            return new ResultData("","Error occurred: " + e.getMessage(),0);
        }
    }
}

