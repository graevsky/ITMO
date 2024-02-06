package org.lab6.common;

import org.lab6.common.CommData;
import org.lab6.common.ResultData;

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
     * @param command is the command
     * @return true, if command was executed correctly and false if not
     */
    ResultData execute(CommData command);
}
