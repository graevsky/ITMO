package org.lab5.commsAndExecution.comms;


/**
 * This is the interface for all commands
 */
public interface CommInterface {
    /**
     * @return name of the command
     */
    String name();

    /**
     * @return description of the command
     */
    String descr();

    /**
     * This block executes command
     *
     * @param arguments are the arguments for the command
     * @return true, if command was executed correctly and false if not
     */
    boolean execute(String[] arguments);
}
