package org.lab6.server.commsAndExecution.comms.assistants;

import java.util.Map;

public class Printer {
    public static void printData(Map<String, Object> data) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            System.out.print(key + ": ");

            if (value instanceof Object[] arrayValue) {
                for (Object element : arrayValue) {
                    System.out.print("  " + element);
                }
            } else {
                System.out.print(value);
            }
            System.out.println();
        }
    }
}
