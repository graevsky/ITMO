package org.lab5.commsAndExecution.comms;

import org.lab5.OBJECTS.DragonPriorityQueue;


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
    public boolean execute(String[] arguments) {
        try {
            dragonQueue.clear();
            System.out.println("All dragons are dead");
            return true;
        } catch (Exception e) {
            System.out.println("Collection was not cleared: " + e);
            return false;
        }
    }
}
