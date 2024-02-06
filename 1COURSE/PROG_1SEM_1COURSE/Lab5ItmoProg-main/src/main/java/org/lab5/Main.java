package org.lab5;


import org.lab5.commsAndExecution.DragonCheck;
import org.lab5.commsAndExecution.CommandAdder;
import org.lab5.commsAndExecution.comms.assistants.Adder;
import org.lab5.ENUM.DragonCharacter;
import org.lab5.ENUM.DragonColor;
import org.lab5.ENUM.DragonType;
import org.lab5.OBJECTS.Coordinates;
import org.lab5.OBJECTS.Dragon;
import org.lab5.OBJECTS.DragonHead;
import org.lab5.OBJECTS.DragonPriorityQueue;
import org.lab5.commsAndExecution.comms.assistants.Adder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author graevsky
 * @version 1
 * This is the main class of lab 5 prog
 */
public class Main {
    /**
     * @param args filepath to the csv file
     * @throws FileNotFoundException csv file not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        /*
          Main file imports dragons from the csv file and adds them to the Dragon PriorityQueue.
          Also, there is "switch" between args filepath and default filepath.
         */
        DragonPriorityQueue dragonQueue = new DragonPriorityQueue();
        //SWITCHER
        //"/Users/graevsky/Desktop/itmoLabs/lab5ItmoProg/csv/dragons.csv"
        //"C:\Users\graev\Desktop\ITMO\PROG_1SEM_1COURSE\Lab5ItmoGradle\\csv\dragons.csv"
        //"/home/studs/s386871/forHelios/dragons.csv";
        //String csvInp = args[0];

        String csvInp = "/Users/graevsky/Desktop/ITMO/PROG_2SEM_1COURSE/itmoLabs/Lab5ItmoProg/csv/dragons.csv";

        BufferedReader scanner = new BufferedReader(new FileReader(csvInp));

        String strCurrentLine;
        try {
            while ((strCurrentLine = scanner.readLine()) != null) {
                String[] params = strCurrentLine.split(",");
                try {
                    Dragon dragon = Adder.createDragon(params);
                    try{
                        DragonCheck.checkDragon(dragon);
                            dragonQueue.add(dragon);
                    }catch (Exception e) {
                        System.out.println("Validation error: " + e.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage() + " in dragon " + params[0]);
                }
            }


        } catch (IOException e) {
            System.out.println("POIZHOSHLA SMERT.....");
            return;
        }

        CommandAdder run = new CommandAdder(dragonQueue);
        run.run();
    }

}