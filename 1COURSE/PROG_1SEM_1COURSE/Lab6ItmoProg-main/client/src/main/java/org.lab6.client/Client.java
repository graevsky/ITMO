package org.lab6.client;

import org.lab6.client.misc.ClientScriptExecutor;
import org.lab6.client.misc.CommandSender;

import java.io.*;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    private final String serverAddress;
    private final int serverPort;


    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void execute() {
        CommandSender commandSender = new CommandSender();
        ClientScriptExecutor clientScriptExecutor = new ClientScriptExecutor();
        try {
            InetAddress host = InetAddress.getByName(serverAddress);
            SocketChannel client = SocketChannel.open(new InetSocketAddress(host, serverPort));

            System.out.println("Connected to server: " + serverAddress + ":" + serverPort);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("> ");
                String command = scanner.next();
                String commandParams = scanner.nextLine().trim();

                if (command.equalsIgnoreCase("save")) {
                    System.out.println("Command is not allowed");
                } else if (command.equalsIgnoreCase("execute_script")) {
                    clientScriptExecutor.executeScript(client, commandParams);
                } else if (command.equalsIgnoreCase("exit")) {
                    break;
                } else {
                    commandSender.sendCommand(client, command, commandParams);
                }
            }

            client.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error connecting to the server (server may be offline): " + e.getMessage());
        }
    }





}
