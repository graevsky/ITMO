package org.lab5.commsAndExecution.comms.assistants;

import org.lab5.OBJECTS.*;
import org.lab5.ENUM.*;


public class Adder {

    public static Dragon createDragon(String[] arguments) throws Exception {
        String name = "";
        try {
            if (!arguments[0].isEmpty()) {
                name = arguments[0];
            }
        } catch (Exception e) {
            throw new Exception("Invalid name format");
        }

        float x;
        int y;
        try {
            x = Float.parseFloat(arguments[1]);
            y = Integer.parseInt(arguments[2]);
            if (y <= -324) {
                throw new Exception("Y coordinate must be greater than -324!");
            }
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            throw new Exception("Invalid coordinate format");
        }


        Coordinates coordinates = new Coordinates(x, y);

        Long age = null;
        try {
            long parsedAge = Long.parseLong(arguments[3]);
            if (parsedAge > 0) {
                age = parsedAge;
            } else {
                throw new Exception("Invalid age format");
            }
        } catch (NumberFormatException ex) {
            try {
                DragonColor color = DragonColor.valueOf(arguments[3].toUpperCase());
                if (color == DragonColor.RED || color == DragonColor.YELLOW || color == DragonColor.WHITE) {
                    arguments = insElem.insertElement(arguments, 3, null);
                } else {
                    throw new Exception("Invalid age format.");
                }
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex2) {
                throw new Exception("Invalid age format");
            }
        }

        DragonColor color;
        try {
            color = DragonColor.valueOf(arguments[4].toUpperCase());
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            throw new Exception("Invalid color format");
        }

        DragonType type = null;
        try {
            if (!arguments[5].isEmpty()) {
                type = DragonType.valueOf(arguments[5].toUpperCase());
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            throw new Exception("Invalid type format");
        }

        DragonCharacter character;
        try {
            character = DragonCharacter.valueOf(arguments[6].toUpperCase());
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            throw new Exception("Invalid character format");
        }

        float headSize;
        try {
            headSize = Float.parseFloat(arguments[7]);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            throw new Exception("Invalid head size.");
        }

        DragonHead head = new DragonHead(headSize);


        return new Dragon(name, coordinates, age, color, type, character, head);
    }

    public static void setDragon(String[] arguments, Dragon dragon) {
        try {
            if (!arguments[0].isEmpty()) {
                dragon.setName(arguments[0]);
            }
        } catch (Exception e) {
            System.out.println("Invalid name format");
            return;
        }

        float x;
        int y;
        try {
            x = Float.parseFloat(arguments[1]);
            y = Integer.parseInt(arguments[2]);
            if (y <= -324) {
                System.out.println("Y coordinate must be greater than -324!");
            }
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            System.out.println("Invalid coordinate format");
            return;
        }
        Coordinates coordinates = new Coordinates(x, y);
        dragon.setCoordinates(coordinates);

        Long age = null;
        try {
            long parsedAge = Long.parseLong(arguments[3]);
            if (parsedAge > 0) {
                age = parsedAge;
            } else {
                System.out.println("Invalid age format");
                return;
            }
        } catch (NumberFormatException ex) {
            try {
                DragonColor color = DragonColor.valueOf(arguments[3].toUpperCase());
                if (color == DragonColor.RED || color == DragonColor.YELLOW || color == DragonColor.WHITE) {
                    arguments = insElem.insertElement(arguments, 3, null);
                } else {
                    System.out.println("Invalid age format");
                    return;
                }
            } catch (IllegalArgumentException | IndexOutOfBoundsException ex2) {
                System.out.println("Invalid age format");
                return;
            }
        }
        dragon.setAge(age);

        try {
            dragon.setColor(DragonColor.valueOf(arguments[4].toUpperCase()));
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            System.out.println("Invalid color format");
            return;
        }


        try {
            if (!arguments[5].isEmpty()) {
                dragon.setType(DragonType.valueOf(arguments[5].toUpperCase()));
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            System.out.println("Invalid type format");
            return;
        }


        try {
            dragon.setCharacter(DragonCharacter.valueOf(arguments[6].toUpperCase()));
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            System.out.println("Invalid character format");
            return;
        }

        float headSize;
        try {
            headSize = Float.parseFloat(arguments[7]);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            System.out.println("Invalid head size. Try again.");
            return;
        }

        DragonHead head = new DragonHead(headSize);
        dragon.setHead(head);

    }
}
