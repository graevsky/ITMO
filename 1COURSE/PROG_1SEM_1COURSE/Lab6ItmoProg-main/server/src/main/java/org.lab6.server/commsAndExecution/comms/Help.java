package org.lab6.server.commsAndExecution.comms;


import org.lab6.common.CommData;
import org.lab6.common.CommInterface;
import org.lab6.common.ResultData;

import java.util.HashMap;


public class Help implements CommInterface {

    private final HashMap<String, CommInterface> comms;

    public Help(HashMap<String, CommInterface> comms) {
        this.comms = comms;
        comms.put("help", this);
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public ResultData execute(CommData command) {
        try {
            StringBuilder result = new StringBuilder();
            for(String key : comms.keySet()){
                CommInterface comm = comms.get(key);
                result.append(comm.name()).append(" ").append(comm.descr()).append('\n');
            }
            return new ResultData(result.toString(),"",1);
        } catch (NullPointerException e) {
            String errorString = "Help cannot be shown: " + e.getMessage();
            return new ResultData("",errorString,0);
        }
    }


    @Override
    public String descr() {
        return "Displays a list of available commands and their descriptions. No params.";
    }
}
