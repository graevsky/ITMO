package org.lab6.server.commsAndExecution.comms.assistants;



import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SaveConstructor {

    public static List<String[]> processDragonPriorityQueue(DragonPriorityQueue dragonQueue) {
        List<String[]> processedData = new ArrayList<>();

        for (Dragon dragon : dragonQueue) {
            String[] data = new String[10];
            data[0] = String.valueOf(dragon.getId());
            data[1] = dragon.getName();
            data[2] = String.valueOf(dragon.getCoordinates().getX());
            data[3] = String.valueOf(dragon.getCoordinates().getY());
            LocalDateTime creationDate = dragon.getCreationDate();
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = creationDate.format(formatDate);
            data[4] = formattedDate;
            data[5] = String.valueOf(dragon.getAge());
            data[6] = String.valueOf(dragon.getColor());
            data[7] = String.valueOf(dragon.getType());
            data[8] = String.valueOf(dragon.getCharacter());
            data[9] = String.valueOf(dragon.getHead().getSize());

            processedData.add(data);
        }

        return processedData;
    }
}
