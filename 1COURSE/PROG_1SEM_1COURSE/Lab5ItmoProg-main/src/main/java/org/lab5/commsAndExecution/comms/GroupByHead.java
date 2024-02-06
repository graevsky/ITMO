package org.lab5.commsAndExecution.comms;

import org.lab5.OBJECTS.Dragon;
import org.lab5.OBJECTS.DragonPriorityQueue;
import org.lab5.commsAndExecution.comms.assistants.Printer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GroupByHead implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    public GroupByHead(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    @Override
    public String name() {
        return "group_counting_by_head";
    }

    @Override
    public String descr() {
        return "Groups dragons by head size and print number of dragons in each group. No params";
    }

    @Override
    public boolean execute(String[] arguments) {
        System.out.println("Group dragons by head size: ");
        Map<Float, Long> groupByHeads = new HashMap<>();
        try {
            for (Dragon drako : dragonQueue) {
                Float head = drako.getHead().getSize();
                groupByHeads.put(head, groupByHeads.getOrDefault(head, 0L) + 1);
            }

            Map<String, Object> formattedData = new LinkedHashMap<>();
            for (Map.Entry<Float, Long> entry : groupByHeads.entrySet()) {
                formattedData.put("Head size: " + entry.getKey(), "Amount: " + entry.getValue());
            }

            Printer.printData(formattedData);
            return true;

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }
}
