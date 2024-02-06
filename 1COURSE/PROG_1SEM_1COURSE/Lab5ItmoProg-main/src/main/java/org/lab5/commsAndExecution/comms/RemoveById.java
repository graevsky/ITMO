package org.lab5.commsAndExecution.comms;

import org.lab5.OBJECTS.Dragon;
import org.lab5.OBJECTS.DragonPriorityQueue;


/**
 * This is the block, that handles RemoveById command and its arguments
 */
public class RemoveById implements CommInterface {
    private  DragonPriorityQueue dragonQueue;

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
     * @param arguments is the id of the dragon, that should be removed
     * @return true, if command was executed correctly and false if not
     */
    @Override//zdesh ya pridumal boolean
    public boolean execute(String[] arguments) {
            try {
                boolean removeFlag = false;
                Long id = Long.parseLong(arguments[0]);
                for (Dragon dragon : dragonQueue) {
                    if (dragon.getId().equals(id)) {
                        dragonQueue.remove(dragon);
                        System.out.println("Dragon " + id + " exterminated!");
                        return true;
                    }
                }
                System.out.println("Id not found!");
                return false;
            }catch (Exception e){
                System.out.println(e);
                return false;
            }
    }
}

