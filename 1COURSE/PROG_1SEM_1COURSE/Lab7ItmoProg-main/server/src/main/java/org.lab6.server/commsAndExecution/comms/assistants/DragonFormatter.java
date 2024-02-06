package org.lab6.server.commsAndExecution.comms.assistants;



import org.lab6.server.OBJECTS.Dragon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DragonFormatter {
    public static String FormatDragon(Dragon dragon){
        LocalDateTime creationDate = dragon.getCreationDate();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = creationDate.format(formatDate);

        return "ID: " + dragon.getId() + " Name: " + dragon.getName() + " Coordinates: " + dragon.getCoordinates().getX() +
                " and " + dragon.getCoordinates().getY() + " Time and date of creation: " + formattedDate + " Age: " + dragon.getAge() + " Color: " + dragon.getColor() +
                " Type: " + dragon.getType() + " Character: " + dragon.getCharacter() + " Head size: " + dragon.getHead().getSize();
    }
}
