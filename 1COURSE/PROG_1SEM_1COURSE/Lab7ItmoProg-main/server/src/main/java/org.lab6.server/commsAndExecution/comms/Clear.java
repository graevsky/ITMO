package org.lab6.server.commsAndExecution.comms;


import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.DragonPriorityQueue;


/**
 * This is the block, that handles Clear command
 */
public class Clear implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    /**
     * @param dragonQueue is the Dragon PriorityQueue, that should be cleared
     */
    public Clear(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    /**
     * Override for the command name
     *
     * @return clear - name of the command
     */
    @Override
    public String name() {
        return "clear";
    }

    /**
     * Override for the command description
     *
     * @return description of the clear command
     */
    @Override
    public String descr() {
        return "Clears DragonPriorityQueue collection. No params";
    }

    /**
     * This block clears the Dragon PriorityQueue(kills all dragons)
     *
     * @param arguments empty string of arguments
     * @return true, if command was executed correctly and false if not
     */
    @Override
    public ResultData execute(CommData arguments) {
        try {
            dragonQueue.clear();
            return new ResultData("Collection cleared!","",1);
        } catch (NullPointerException e) {
            return new ResultData("","Collection was not cleared "+e.getMessage(),0);
        }
    }
}
