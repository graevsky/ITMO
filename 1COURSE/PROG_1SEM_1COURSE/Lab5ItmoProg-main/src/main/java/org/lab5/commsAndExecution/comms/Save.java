package org.lab5.commsAndExecution.comms;

import com.opencsv.CSVWriter;
import org.lab5.OBJECTS.DragonPriorityQueue;
import org.lab5.commsAndExecution.comms.assistants.SaveConstructor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public boolean execute(String[] arguments) {
        try {
            String filePath = arguments[0];

            try {
                File file = new File(filePath);
                FileWriter outputFile = new FileWriter(file);
                CSVWriter writer = new CSVWriter(outputFile);
                String[] header = {"ID", "Name", "X", "Y", "Time and date of creation", "Age", "Color", "Type", "Character", "Head size"};
                writer.writeNext(header);

                List<String[]> processedData = SaveConstructor.processDragonPriorityQueue(dragonQueue);

                for (String[] data : processedData) {
                    writer.writeNext(data);
                }

                writer.close();
                System.out.println("Filepath: " + filePath);
                return true;
            } catch (IOException e) {
                System.out.println("Error occurred while saving: " + e.getMessage());
                return false;
            }

        } catch (Exception e) {
            System.out.println("Missing filepath!");
            return false;
        }
    }
}
