package org.lab5.commsAndExecution.comms;

import org.lab5.OBJECTS.Dragon;
import org.lab5.OBJECTS.DragonPriorityQueue;
import org.lab5.commsAndExecution.comms.assistants.Printer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShowDragons implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    public ShowDragons(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    @Override
    public String name() {
        return "show";
    }

    @Override
    public String descr() {
        return "Showing dragons, No params.";
    }

    @Override
    public boolean execute(String[] arguments) {
        if (dragonQueue.isEmpty()) {
            System.out.println("Nothing to show.");
            return false;
        }

        Map<String, Object> formattedData = new LinkedHashMap<>();
        for (Dragon dragon : dragonQueue) {
            LocalDateTime creationDate = dragon.getCreationDate();
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = creationDate.format(formatDate);

            String dragonData = String.format("Name %s, Coordinates %s and %s, Time of add %s, Age %s, Color %s, Type %s, Character %s, Head size %s",
                    dragon.getName(),
                    dragon.getCoordinates().getX(),
                    dragon.getCoordinates().getY(),
                    formattedDate,
                    dragon.getAge(),
                    dragon.getColor(),
                    dragon.getType(),
                    dragon.getCharacter(),
                    dragon.getHead().getSize());

            formattedData.put("ID: " + dragon.getId(), dragonData);
        }
        Printer.printData(formattedData);
        return true;
    }

}
