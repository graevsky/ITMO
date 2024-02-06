package org.lab5.commsAndExecution;

import org.lab5.commsAndExecution.comms.*;
import org.lab5.OBJECTS.DragonPriorityQueue;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Runner class handles terminal and starts programs.
 */
public class CommandAdder {
    private final HashMap<String, CommInterface> commands;

    /**
     * Constructor for the Dragon PriorityQueue. It also adds commands to the HashMap command processor.
     *
     * @param dragonQueue is the Dragon PriorityQueue, which should be handled.
     */

    public CommandAdder(DragonPriorityQueue dragonQueue) {
        this.commands = new HashMap<>();

        this.addCommand(new Add(dragonQueue));
        this.addCommand(new ShowDragons(dragonQueue));
        this.addCommand(new Help(this.getCommands()));
        this.addCommand(new Info(dragonQueue));
        this.addCommand(new UpdateById(dragonQueue));
        this.addCommand(new RemoveById(dragonQueue));
        this.addCommand(new Clear(dragonQueue));
        this.addCommand(new ExecuteScript(dragonQueue,0 ));
        this.addCommand(new AddIfMax(dragonQueue));
        this.addCommand(new RemovesGreater(dragonQueue));
        this.addCommand(new Save(dragonQueue));
        this.addCommand(new RemovesLower(dragonQueue));
        this.addCommand(new GroupByHead(dragonQueue));
        this.addCommand(new FilterGreaterAge(dragonQueue));
        this.addCommand(new PrintFieldAscendingColor(dragonQueue));
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
     * @param commandLine is the command with its arguments
     */

    public void processCommand(String commandLine) {
        String[] parts = commandLine.split("\\s+");
        String commandName = parts[0];
        CommInterface command = this.commands.get(commandName);
        if (command == null) {
            System.out.println("Unknown command: " + commandName);
        } else {
            String[] arguments = new String[parts.length - 1];
            System.arraycopy(parts, 1, arguments, 0, parts.length - 1);
            boolean executed = command.execute(arguments);
            if (!executed) {
                System.out.println("Error executing command.");
            }
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
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String commandLine = scanner.nextLine().trim();
            if (commandLine.isEmpty()) {
                continue;
            }
            if (commandLine.equals("exit")) {
                break;
            }
            this.processCommand(commandLine);
        }
    }
}
