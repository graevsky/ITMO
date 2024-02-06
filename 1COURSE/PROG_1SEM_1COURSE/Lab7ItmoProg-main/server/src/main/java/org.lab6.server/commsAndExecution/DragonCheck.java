package org.lab6.server.commsAndExecution;


import org.lab6.server.OBJECTS.Coordinates;
import org.lab6.server.OBJECTS.Dragon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * This  file validates dragon and check if they are suitable to be added into the Dragon PriorityQueue.
 */
public class DragonCheck {
    /**
     * This is needed to check if dragon could be added to priority queue
     *
     * @param dragon dragon to be validated
     */
    public static void checkDragon(Dragon dragon) {
        List<String> errors = new ArrayList<>();

        if (Objects.isNull(dragon.getName()) || dragon.getName().isEmpty()) {
            errors.add("Name should not be empty!");
        }
        if (Objects.isNull(dragon.getCoordinates())) {
            errors.add("Coordinates should  not be empty!");
        } else {
            Coordinates coordinates = dragon.getCoordinates();
            if (coordinates.getY() <= -234) {
                errors.add("Y coordinate should be greater than -234!");
            }
        }
        if (Objects.isNull(dragon.getColor())) {
            errors.add("Color should not be empty!");
        }

        if (Objects.isNull(dragon.getCharacter())) {
            errors.add("Character should not be empty!");
        }

        if (Objects.nonNull(dragon.getAge()) && dragon.getAge() < 0) {
            errors.add("Age should be null or > 0!");
        }

        if (!errors.isEmpty()) {
            throw new DragonParseException(String.join("; ", errors));
        }
    }

    /**
     * YES
     */
    public static class DragonParseException extends RuntimeException {
        /**
         * Block, that parse exceptions
         *
         * @param message is the exception
         */
        public DragonParseException(String message) {
            super(message);
        }
    }

}
