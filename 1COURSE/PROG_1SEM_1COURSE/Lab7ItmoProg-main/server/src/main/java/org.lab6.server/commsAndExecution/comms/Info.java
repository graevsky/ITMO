package org.lab6.server.commsAndExecution.comms;



import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.DragonPriorityQueue;
import org.lab6.server.OBJECTS.Dragon;

import java.util.Arrays;
import java.util.NoSuchElementException;


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
    public ResultData execute(CommData command) {
        try {
            String result = "Collection type: " + dragonQueue.getClass().getSimpleName() + '\n' +
                    "Date and time of creation: " + dragonQueue.getInitDate() + '\n' +
                    "Collection size: " + dragonQueue.size() + '\n' +
                    "Types of dragons in collection: " + Arrays.toString(
                    dragonQueue.stream()
                            .map(Dragon::getType)
                            .distinct()
                            .toArray()) +
                    '\n';///TODO:STREAM HERE
            return new ResultData(result,"",1);
        } catch (NullPointerException | NoSuchElementException | ClassCastException | IllegalStateException  e) {
            String errorString = "Error occured while printing info: " + e;
            return new ResultData("",errorString,0);
        }
    }


}
