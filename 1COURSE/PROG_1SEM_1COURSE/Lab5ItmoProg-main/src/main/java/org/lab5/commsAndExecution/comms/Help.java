package org.lab5.commsAndExecution.comms;

import org.lab5.commsAndExecution.comms.assistants.Printer;

import java.util.HashMap;
import java.util.Map;

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
    public boolean execute(String[] args) {
        try {
            Map<String, Object> helpData = generateHelpData();
            Printer.printData(helpData);
            return true;
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
            return false;
        }
    }

    public Map<String, Object> generateHelpData() {
        Map<String, Object> helpData = new HashMap<>();
        for (String key : comms.keySet()) {
            CommInterface comm = comms.get(key);
            helpData.put(comm.name(), comm.descr());
        }
        return helpData;
    }

    @Override
    public String descr() {
        return "Displays a list of available commands and their descriptions. No params.";
    }
}
