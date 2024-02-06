/*package org.lab6.server.commsAndExecution.comms;

import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;
import org.lab6.server.App;
import org.lab6.server.OBJECTS.DragonPriorityQueue;

import java.io.BufferedReader;
import java.io.FileReader;

public class ScriptExecutor implements CommInterface {
    private static int max_count = 5;
    private int counter;
    private final DragonPriorityQueue dragonQueue;

    public ScriptExecutor(DragonPriorityQueue dragonQueue, int counter) {
        this.dragonQueue = dragonQueue;
        this.counter = counter;
    }

    @Override
    public String name(){
        return "execute_script";
    }

    @Override
    public String descr(){
        return "Executes script. Params:filepath(String)";
    }
    @Override
    public ResultData execute(CommData command) {
        try {
            String[] arguments = command.getArgs().split(" ");
            String filePath = arguments[0];
            if (counter >= max_count) {
                System.out.println("Max recursion depth reached!");
                return false;
            } else {
                try {
                    BufferedReader scanner = new BufferedReader(new FileReader(filePath));
                    String strCurrentLine;
                    while ((strCurrentLine = scanner.readLine()) != null) {
                        App commandProcessor = new App();
                        commandProcessor.addCommand(new Add(dragonQueue));
                        commandProcessor.addCommand(new ShowDragons(dragonQueue));
                        commandProcessor.addCommand(new Help(commandProcessor.getCommands()));
                        commandProcessor.addCommand(new Info(dragonQueue));
                        commandProcessor.addCommand(new UpdateById(dragonQueue));
                        commandProcessor.addCommand(new RemoveById(dragonQueue));
                        commandProcessor.addCommand(new Clear(dragonQueue));
                        commandProcessor.addCommand(new ExecuteScript(dragonQueue, counter + 1));
                        commandProcessor.addCommand(new AddIfMax(dragonQueue));
                        commandProcessor.addCommand(new RemovesGreater(dragonQueue));
                        commandProcessor.addCommand(new Save(dragonQueue));
                        commandProcessor.addCommand(new RemovesLower(dragonQueue));
                        commandProcessor.addCommand(new GroupByHead(dragonQueue));
                        commandProcessor.addCommand(new FilterGreaterAge(dragonQueue));
                        commandProcessor.addCommand(new PrintFieldAscendingColor(dragonQueue));
                        commandProcessor.processCommand(strCurrentLine);
                    }
                } catch (IOException e) {
                    System.out.println("Error occurred: " + e);
                    return false;
                }
            }

        } catch (Exception e) {
            System.out.println("Missing filepath!");
            return false;
        }
        return true;
    }

}
*/