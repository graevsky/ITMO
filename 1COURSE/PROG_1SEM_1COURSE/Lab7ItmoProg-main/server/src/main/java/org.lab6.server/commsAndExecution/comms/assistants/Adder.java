package org.lab6.server.commsAndExecution.comms.assistants;


import org.lab6.common.CommData;
import org.lab6.common.ResultData;
import org.lab6.server.ENUM.DragonCharacter;
import org.lab6.server.ENUM.DragonColor;
import org.lab6.server.ENUM.DragonType;
import org.lab6.server.OBJECTS.Coordinates;
import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonHead;
import org.lab6.server.commsAndExecution.DragonCheck;

import java.util.ArrayList;
import java.util.List;


public class Adder {

    public static Dragon createDragon(CommData arguments) {
        List<String> errors = new ArrayList<>();
        String[] parsedArgs = arguments.getArgs().split(" ");
        String name = "";
        try {
            if (!parsedArgs[0].isEmpty()) {
                name = parsedArgs[0];
            }
        } catch (Exception e) {
            errors.add("Invalid name format");
        }

        float x = 0;
        int y = 0;
        try {
            x = Float.parseFloat(parsedArgs[1]);
            y = Integer.parseInt(parsedArgs[2]);
            if(y <= -324){
                errors.add("Y coordinate must be greater than -324!");
            }
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            errors.add("Invalid coordinate format");
        }


        Coordinates coordinates = new Coordinates(x, y);

        Long age = null;
        try {
            long parsedAge = Long.parseLong(parsedArgs[3]);
            if (parsedAge > 0) {
                age = parsedAge;
            } else {
                errors.add("Invalid age format");
            }
        } catch (NumberFormatException ex) {
            try {
                DragonColor color = DragonColor.valueOf(parsedArgs[3].toUpperCase());
                if (color == DragonColor.RED || color == DragonColor.YELLOW || color == DragonColor.WHITE) {
                    parsedArgs = insElem.insertElement(parsedArgs,3,null);
                } else {
                    errors.add("Invalid age format.");
                }
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex2) {
                errors.add("Invalid age format");
            }
        }

        DragonColor color = DragonColor.RED;
        try {
            color = DragonColor.valueOf(parsedArgs[4].toUpperCase());
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            errors.add("Invalid color format");
        }

        DragonType type = null;
        try {
            if (!parsedArgs[5].isEmpty()) {
                type = DragonType.valueOf(parsedArgs[5].toUpperCase());
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            errors.add("Invalid type format");
        }

        DragonCharacter character = DragonCharacter.CUNNING;
        try {
            character = DragonCharacter.valueOf(parsedArgs[6].toUpperCase());
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            errors.add("Invalid character format");
        }

        float headSize = 0;
        try {
            headSize = Float.parseFloat(parsedArgs[7]);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            errors.add("Invalid head size.");
        }

        DragonHead head = new DragonHead(headSize);

        if (!errors.isEmpty()) {
            throw new Adder.DragonParseException(String.join("; ", errors));
        }else {
            return new Dragon(name, coordinates, age, color, type, character, head);
        }
    }
    public static void setDragon(String[] parsedArgs,Dragon dragon) {
        List<String> errors = new ArrayList<>();
        try {
            if (!parsedArgs[0].isEmpty()) {
                dragon.setName(parsedArgs[0]);
            }
        } catch (Exception e) {
            errors.add("Invalid name format");

        }

        float x=0;
        int y=0;
        try {
            x = Float.parseFloat(parsedArgs[1]);
            y = Integer.parseInt(parsedArgs[2]);
            if(y <= -324){
                errors.add("Y coordinate must be greater than -324!");
            }
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            errors.add("Invalid coordinate format");

        }
        Coordinates coordinates = new Coordinates(x, y);
        dragon.setCoordinates(coordinates);

        Long age = null;
        try {
            long parsedAge = Long.parseLong(parsedArgs[3]);
            if (parsedAge > 0) {
                age = parsedAge;
            } else {
                errors.add("Invalid age format");

            }
        } catch (NumberFormatException ex) {
            try {
                DragonColor color = DragonColor.valueOf(parsedArgs[3].toUpperCase());
                if (color == DragonColor.RED || color == DragonColor.YELLOW || color == DragonColor.WHITE) {
                    parsedArgs = insElem.insertElement(parsedArgs,3,null);
                } else {
                    errors.add("Invalid age format");

                }
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex2) {
                errors.add("Invalid age format");

            }
        }
        dragon.setAge(age);

        try {
            dragon.setColor(DragonColor.valueOf(parsedArgs[4].toUpperCase()));
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            errors.add("Invalid color format");

        }


        try {
            if (!parsedArgs[5].isEmpty()) {
                dragon.setType(DragonType.valueOf(parsedArgs[5].toUpperCase()));
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            errors.add("Invalid type format");

        }


        try {
            dragon.setCharacter( DragonCharacter.valueOf(parsedArgs[6].toUpperCase()));
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            errors.add("Invalid character format");

        }

        float headSize = 0;
        try {
            headSize = Float.parseFloat(parsedArgs[7]);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            errors.add("Invalid head size. Try again.");

        }

        DragonHead head = new DragonHead(headSize);
        dragon.setHead(head);
        if (!errors.isEmpty()) {
            throw new Adder.DragonParseException(String.join("; ", errors));
        }

    }
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
