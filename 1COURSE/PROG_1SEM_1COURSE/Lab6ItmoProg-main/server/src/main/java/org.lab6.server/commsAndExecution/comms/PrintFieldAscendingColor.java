package org.lab6.server.commsAndExecution.comms;


import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;


import java.util.ArrayList;
import java.util.Comparator;

public class PrintFieldAscendingColor implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    public PrintFieldAscendingColor(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    @Override
    public String name() {
        return "print_field_ascending_color";
    }

    @Override
    public String descr() {
        return "Prints color of all dragons in ascending order of ID. No params.";
    }

    @Override
    public ResultData execute(CommData command) {
        System.out.println("Printing color of all elements in ascending order of ID: ");
        ArrayList<Dragon> sortedDragons = new ArrayList<>(dragonQueue);
        try {
            sortedDragons.sort(Comparator.comparing(Dragon::getId));
            StringBuilder result = new StringBuilder();
            for (Dragon dragon : sortedDragons) {
                result.append("Dragond ID: ").append(dragon.getId()).append(" Color: ").append(dragon.getColor()).append('\n');
            }
            return new ResultData(result.toString(),"",1);
        } catch (NullPointerException | ClassCastException  e) {
            String errorString = "DragonPriorityQueue sorting error: " + e;
            return new ResultData("",errorString,0);
        }
    }
}
