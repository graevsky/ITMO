package org.lab5.commsAndExecution.comms;

import org.lab5.OBJECTS.Dragon;
import org.lab5.OBJECTS.DragonPriorityQueue;
import org.lab5.commsAndExecution.comms.assistants.DragonFormatter;
import org.lab5.commsAndExecution.comms.assistants.Printer;

import java.util.*;


public class FilterGreaterAge implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    public FilterGreaterAge(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    @Override
    public String name() {
        return "filter_greater_than_age";
    }

    @Override
    public String descr() {
        return "All dragons, that are over a certain age. Params: Age(Integer)";
    }

    @Override
    public boolean execute(String[] arguments) {
        int age = Integer.parseInt(arguments[0]);
        System.out.println("All dragons, that are older, than this: " + age);
        Map<String, Object> formattedData = new LinkedHashMap<>();
        int count = 0;
        for (Dragon drako : dragonQueue) {
            if (drako.getAge() > age) {
                count++;
                String dragonData = DragonFormatter.FormatDragon(drako);
                formattedData.put("ID: " + drako.getId(), dragonData);
            }
        }
        if (count == 0) {
            System.out.println("All dragons in collection are younger!");
            return false;
        }
        Printer.printData(formattedData);
        return true;
    }

}
