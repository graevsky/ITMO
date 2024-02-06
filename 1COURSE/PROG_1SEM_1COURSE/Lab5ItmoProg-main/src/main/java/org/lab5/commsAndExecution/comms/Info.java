package org.lab5.commsAndExecution.comms;

import org.lab5.OBJECTS.Dragon;
import org.lab5.OBJECTS.DragonPriorityQueue;
import org.lab5.commsAndExecution.comms.assistants.Printer;

import java.util.HashMap;
import java.util.Map;

public class Info implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    public Info(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    @Override
    public String name() {
        return "info";
    }

    @Override

    public String descr() {
        return "Information about DragonPriorityQueue. No params.";
    }

    @Override
    public boolean execute(String[] arguments) {
        try {
            Printer.printData(getInfo());
            return true;
        } catch (Exception e) {
            System.out.println("Error occurred while printing info: " + e);
            return false;
        }
    }

    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("Collection type", dragonQueue.getClass().getSimpleName());
        info.put("Date and time of creation", dragonQueue.getInitDate());
        info.put("Collection size", dragonQueue.size());
        info.put("Types of dragons", dragonQueue.stream().map(Dragon::getType).distinct().toArray());
        return info;
    }
}
