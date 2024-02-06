package org.lab6.server;


import org.lab6.common.CommData;
import org.lab6.common.ResultData;

import org.lab6.server.OBJECTS.Dragon;
import org.lab6.server.OBJECTS.DragonPriorityQueue;
import org.lab6.server.commsAndExecution.DragonCheck;
import org.lab6.server.commsAndExecution.comms.Add;
import org.lab6.common.CommInterface;
import org.lab6.server.commsAndExecution.comms.Help;
import org.lab6.server.commsAndExecution.comms.ShowDragons;
import org.lab6.server.commsAndExecution.comms.assistants.Adder;

import org.lab6.server.commsAndExecution.comms.Info;
import org.lab6.server.commsAndExecution.comms.UpdateById;
import org.lab6.server.commsAndExecution.comms.RemoveById;
import org.lab6.server.commsAndExecution.comms.Clear;
import org.lab6.server.commsAndExecution.comms.AddIfMax;
import org.lab6.server.commsAndExecution.comms.RemovesGreater;
import org.lab6.server.commsAndExecution.comms.RemovesLower;
import org.lab6.server.commsAndExecution.comms.Save;

import org.lab6.server.commsAndExecution.comms.GroupByHead;
import org.lab6.server.commsAndExecution.comms.FilterGreaterAge;
import org.lab6.server.commsAndExecution.comms.PrintFieldAscendingColor;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Stream;


public class App {
    private final String csvInp;
    private DragonPriorityQueue dragonPriorityQueue;
    private final HashMap<String, CommInterface> commands;
    private boolean status;


    /**
     * Constructor for the Dragon PriorityQueue. It also adds commands to the HashMap command processor.
     *

     */

    public App(String csvInp) {
        this.dragonPriorityQueue = new DragonPriorityQueue();
        this.csvInp = csvInp;
        this.commands = new HashMap<>();
        this.status = true;

        this.addCommand(new Add(dragonPriorityQueue));
        this.addCommand(new ShowDragons(dragonPriorityQueue));
        this.addCommand(new Help(this.getCommands()));
        this.addCommand(new Info(dragonPriorityQueue));
        this.addCommand(new UpdateById(dragonPriorityQueue));
        this.addCommand(new RemoveById(dragonPriorityQueue));
        this.addCommand(new Clear(dragonPriorityQueue));
        //this.addCommand(new ExecuteScript(dragonPriorityQueue, 0));
        this.addCommand(new AddIfMax(dragonPriorityQueue));
        this.addCommand(new RemovesGreater(dragonPriorityQueue));
        this.addCommand(new Save(dragonPriorityQueue));
        this.addCommand(new RemovesLower(dragonPriorityQueue));
        this.addCommand(new GroupByHead(dragonPriorityQueue));
        this.addCommand(new FilterGreaterAge(dragonPriorityQueue));
        this.addCommand(new PrintFieldAscendingColor(dragonPriorityQueue));
    }
    public boolean getStatus(){
        return status;
    }
    public void setStatus(boolean newStatus){
        status = newStatus;
    }

    /**
     * Adds command to the Hash Map
     *
     * @param command is the command,that should be added to the commands Hash Map
     */
    public void addCommand(CommInterface command) {
        this.commands.put(command.name(), command);
    }

    /**
     * This is the block, that processes all the commands
     *
     *
     */

    public ResultData processCommand(String name, String args) {
        CommInterface command = this.commands.get(name);
        if (command == null) {
            return new ResultData("","Unknown command!",0);
        } else {
            CommData parsedCommand = new CommData("", args);
            return command.execute(parsedCommand);

        }
    }

    /**
     * @return commands in the Hash Map
     */
    public HashMap<String, CommInterface> getCommands() {
        return this.commands;
    }

    /**
     * Start of the program.
     */

    public void createQueue() {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvInp))) {
            Stream<String> lines = reader.lines();
            ///TODO:lambda here
            lines.forEach(strCurrentLine -> {
                CommData params = new CommData("", strCurrentLine);
                try {
                    Dragon dragon = Adder.createDragon(params);
                    try {
                        DragonCheck.checkDragon(dragon);
                        dragonPriorityQueue.add(dragon);
                    } catch (Exception e) {
                        System.out.println("Validation error: " + e.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage() + " in dragon " + params.getArgs());
                }
            });
        } catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
        } catch (IOException e) {
            System.out.println("Failed adding dragons: " + e.getMessage());
        }
    }

}
