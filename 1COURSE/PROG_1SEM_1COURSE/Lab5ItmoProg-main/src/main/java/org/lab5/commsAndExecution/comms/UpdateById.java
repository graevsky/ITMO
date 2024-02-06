package org.lab5.commsAndExecution.comms;

import org.lab5.OBJECTS.Dragon;
import org.lab5.OBJECTS.DragonPriorityQueue;
import org.lab5.commsAndExecution.comms.assistants.Adder;

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
     * @param arguments are the parameters of the dragon, that should be updated
     * @return true, if command was executed correctly and false if not
     */
    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length < 9) {
            System.out.println("Invalid arguments");
            return false;
        }

        Long id;
        id = Long.parseLong(arguments[0]);
        Dragon dragonToUpdate = null;
        for (Dragon dragon : dragonQueue) {
            if (dragon.getId().equals(id)) {
                dragonToUpdate = dragon;
                break;
            }
        }
        if (dragonToUpdate != null) {
            String[] dragonArguments = new String[arguments.length - 1];
            System.arraycopy(arguments, 1, dragonArguments, 0, arguments.length - 1);
            Adder.setDragon(dragonArguments, dragonToUpdate);
            System.out.println("Dragon updated.");
            return true;
        } else {
            System.out.println("Dragon with ID " + id + " not found. Check the ID.");
            return false;
        }
    }
}
