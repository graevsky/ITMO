package org.lab6.server.commsAndExecution.comms;

import com.opencsv.CSVWriter;
import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.OBJECTS.DragonPriorityQueue;
import org.lab6.server.commsAndExecution.comms.assistants.SaveConstructor;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

public class Save implements CommInterface {
    private final DragonPriorityQueue dragonQueue;

    public Save(DragonPriorityQueue dragonQueue) {
        this.dragonQueue = dragonQueue;
    }

    @Override
    public String name() {
        return "save";
    }

    @Override
    public String descr() {
        return "Saves dragon collection to desired location. Params: Filepath(String)";
    }

    @Override
    public ResultData execute(CommData command) {

        String filePath = command.getArgs();
        if(filePath.isEmpty()){
            return new ResultData("","Empty filepath!",0);
        }
        try {
            File file = new File(filePath);
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);
            String[] header = {"ID", "Name", "X", "Y", "Time and date of creation", "Age", "Color", "Type", "Character", "Head size"};
            writer.writeNext(header);

            List<String[]> processedData = SaveConstructor.processDragonPriorityQueue(dragonQueue);
            ///TODO:stram and lambda here
            processedData.stream().forEach(writer::writeNext);

            writer.close();
            return new ResultData("Here is the filepath " + filePath,"",1);
        } catch (IOException e) {
            return new ResultData("","Error occured while saving: " + e.getMessage(),0);
        }

    }
}
