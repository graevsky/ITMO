package org.lab5.commsAndExecution.comms;

import org.lab5.OBJECTS.Dragon;
import org.lab5.OBJECTS.DragonPriorityQueue;


/**
 * This is the block, that handles RemoveLower command and its arguments(comparison by head size)
 */
public class RemovesLower implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    /**
     * @param dragonQueue is the Dragon PriorityQueue, that should be modified
     */
    public RemovesLower(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    /**
     * Override for the command name
     *
     * @return remove_greater - name of the command
     */
    @Override
    public String name() {
        return "remove_lower";
    }

    /**
     * Override for the comand description
     *
     * @return description of the remove_greater command
     */
    @Override
    public String descr() {
        return "Removes all dragons with head size, that is lower, than parameter. Params: Head_Size(Integer)";
    }

    /**
     * This block handles the removal process from the Dragon PriorityQueue
     *
     * @param arguments is the size of head
     * @return true, if command was executed correctly and false if not
     */
    @Override
    public boolean execute(String[] arguments) {
        System.out.println("Comparison by head size.");
        float headSize = Float.parseFloat(arguments[0]);
        int counter = 0;
        try {
            for (Dragon drako : dragonQueue) {
                if (!dragonQueue.isEmpty() && drako.getHead().getSize() < headSize) {
                    dragonQueue.remove(drako);
                    counter++;
                }
            }
            System.out.println("Deleted " + counter + " dragon(s).");
            return true;
        } catch (Exception e) {
            System.out.println("Invalid head size/format.");
            return false;
        }
    }
}
