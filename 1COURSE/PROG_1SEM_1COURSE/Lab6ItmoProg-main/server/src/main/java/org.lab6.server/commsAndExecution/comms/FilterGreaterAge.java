package org.lab6.server.commsAndExecution.comms;



import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;

import org.lab6.server.commsAndExecution.comms.assistants.DragonFormatter;


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
    public ResultData execute(CommData command) {
        String[] args = command.getArgs().split(" ");
        int age;
        try{
            age = Integer.parseInt(args[0]);
        }catch (NumberFormatException e){
            return new ResultData("","Use integer: " +e.getMessage(),0);
        }
        System.out.println("All dragons, that are older, than this: " + age);
        int count = 0;
        StringBuilder result = new StringBuilder();
        for (Dragon drako : dragonQueue) {
            if (drako.getAge() > age) {
                count++;
                String dragonData = DragonFormatter.FormatDragon(drako);
                result.append(dragonData).append('\n');
            }
        }
        if (count == 0) {
            return new ResultData("All dragons are younger!","",1);
        }
        return new ResultData(result.toString(),"",1);
    }

}
