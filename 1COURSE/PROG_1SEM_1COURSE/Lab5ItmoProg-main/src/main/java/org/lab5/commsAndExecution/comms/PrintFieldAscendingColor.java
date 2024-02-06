package org.lab5.commsAndExecution.comms;

import org.lab5.OBJECTS.Dragon;
import org.lab5.OBJECTS.DragonPriorityQueue;
import org.lab5.commsAndExecution.comms.assistants.Printer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

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
        return "Prints color of all elements in ascending order of ID. No params.";
    }

    @Override
    public boolean execute(String[] arguments) {
        System.out.println("Printing color of all elements in ascending order of ID: ");
        ArrayList<Dragon> sortedDragons = new ArrayList<>(dragonQueue);
        try {
            sortedDragons.sort(Comparator.comparing(Dragon::getId));

            Map<String, Object> formattedData = new LinkedHashMap<>();
            for (Dragon dragon : sortedDragons) {
                formattedData.put("Dragon ID: " + dragon.getId(), "Color: " + dragon.getColor());
            }
            Printer.printData(formattedData);
            return true;
        } catch (Exception e) {
            System.out.println("DragonPriorityQueue sorting error: " + e);
            return false;
        }
    }
}
