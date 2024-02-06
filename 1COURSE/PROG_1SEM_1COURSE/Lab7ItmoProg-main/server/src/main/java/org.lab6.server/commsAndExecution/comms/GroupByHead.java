package org.lab6.server.commsAndExecution.comms;



import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;


import java.util.HashMap;
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
    public ResultData execute(CommData command) {
        System.out.println("Group dragons by head size: ");
        Map<Float, Long> groupByHeads = new HashMap<>();

            StringBuilder result = new StringBuilder();
            for (Dragon drako : dragonQueue) {
                Float head = drako.getHead().getSize();
                groupByHeads.put(head, groupByHeads.getOrDefault(head, 0L) + 1);
            }
            for (Map.Entry<Float, Long> entry : groupByHeads.entrySet()) {
                String resultString = "Head size: " + entry.getKey() + "Amount: " + entry.getValue();
                result.append(resultString).append('\n');
            }
            return new ResultData(result.toString(),"",1);

    }
}
