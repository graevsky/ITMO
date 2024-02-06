package org.lab6.server.commsAndExecution.comms;



import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public ResultData execute(CommData command) {
        if (dragonQueue.isEmpty()) {
            return new ResultData("","Nothing to show",0);
        }
        StringBuilder result = new StringBuilder();
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
            result.append(dragon.getId()).append(" ").append(dragonData).append('\n');
        }
        return new ResultData(result.toString(),"",1);
    }

}
