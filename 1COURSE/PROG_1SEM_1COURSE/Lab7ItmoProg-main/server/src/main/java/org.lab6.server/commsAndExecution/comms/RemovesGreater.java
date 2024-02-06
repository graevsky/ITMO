package org.lab6.server.commsAndExecution.comms;


import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;

import java.util.NoSuchElementException;


/**
 * This is the block, that handles RemoveGreater command and its arguments(comparison by head size)
 */
public class RemovesGreater implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    /**
     * @param dragonQueue is the Dragon PriorityQueue, that should be modified
     */
    public RemovesGreater(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    /**
     * Override for the command name
     *
     * @return remove_greater - name of the command
     */
    @Override
    public String name() {
        return "remove_greater";
    }

    /**
     * Override for the comand description
     *
     * @return description of the remove_greater command
     */
    @Override
    public String descr() {
        return "Removes all dragons with head size, that is greater, than parameter. Params: Head_Size(Float)";
    }

    /**
     * This block handles the removal process from the Dragon PriorityQueue
     *
     * @param command is the size of head
     * @return true, if command was executed correctly and false if not
     */

    @Override
    public ResultData execute(CommData command) {
        System.out.println("Comparison by head size.");
        String[] args = command.getArgs().split(" ");
        float headSize;
        try{
             headSize = Float.parseFloat(args[0]);
        }catch (NumberFormatException e){
            return new ResultData("","Use float: " +e.getMessage(),0);
        }
        int counter = 0;
        try {
            for (Dragon drako : dragonQueue) {
                if (!dragonQueue.isEmpty() && drako.getHead().getSize() > headSize) {
                    dragonQueue.remove(drako);
                    counter++;
                }
            }
            return new ResultData("Removed " + counter + " dragons!","",1);
        } catch (NoSuchElementException | ClassCastException  e) {
            return new ResultData("","Invalid head size/format " +e.getMessage(),0);
        }
    }
}
