package org.lab6.server;

import org.lab6.common.CommData;
import org.lab6.common.ResultData;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Server {
    private final int serverPort;
    private final App application;
    private BufferedReader serverInput;

    public Server(int serverPort, App application) {
        this.serverPort = serverPort;
        this.application = application;
        serverInput = new BufferedReader(new InputStreamReader(System.in));

    }


    public void start() {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(serverPort));
            Selector selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server running on port: " + serverPort);
            ByteBuffer buffer = ByteBuffer.allocate(256);

            while (application.getStatus()) {
                if (serverInput.ready()) {
                    processServerCommand();
                }
                selector.selectNow();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();


                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        acceptClient(selector, key);
                    } else if (key.isReadable()) {
                        readClient(key, buffer);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }

    }

    private void acceptClient(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);

        System.out.println("Client connected on: " + clientChannel.getRemoteAddress());
    }

    private void readClient(SelectionKey key, ByteBuffer buffer) {
        SocketChannel clientChannel = (SocketChannel) key.channel();

        buffer.clear();
        int read;
        try {
            read = clientChannel.read(buffer);
        } catch (IOException e) {
            System.err.println("Reading problem, closing connection");
            key.cancel();
            return;
        }

        if (read == -1) {
            System.err.println("Nothing was there to be read, closing connection");
            key.cancel();
            return;
        }


        Executors.newSingleThreadExecutor().submit(() -> processRequest(buffer, key));
    }

    private void processRequest(ByteBuffer buffer, SelectionKey key) {
        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            CommData commandData = (CommData) ois.readObject();
            ResultData resultData = application.processCommand(commandData.getName(), commandData.getArgs());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(resultData);
            oos.flush();
            byte[] sendData = baos.toByteArray();

            ByteBuffer sendBuffer = ByteBuffer.wrap(sendData);
            SocketChannel clientChannel = (SocketChannel) key.channel();
            clientChannel.write(sendBuffer);



        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error processing request: " + e.getMessage());
        }

    }
    private void processServerCommand() {
        try {
            String command = serverInput.readLine();
            if(command.equalsIgnoreCase("exit")){
                String currentDirectory = System.getProperty("user.dir");
                String filename = "dragonsDump.csv";
                String filepath = currentDirectory + File.separator + filename;

                CommData outgoingCommand = new CommData("save", filepath); // use save command
                ResultData resultData = application.processCommand(outgoingCommand.getName(), outgoingCommand.getArgs());
                System.out.println(resultData.getResult() + resultData.getError());
                application.setStatus(false);
            }
            String[] commandParts = command.split(" ", 2);
            String commandName = commandParts[0];
            String commandArgs = commandParts.length > 1 ? commandParts[1] : "";

            CommData outgoingCommand = new CommData(commandName, commandArgs);

            ResultData resultData1 = application.processCommand(outgoingCommand.getName(),outgoingCommand.getArgs());

            System.out.println(resultData1.getResult() + resultData1.getError());
        } catch (IOException e) {
            System.err.println("Error reading server command: " + e.getMessage());
        }
    }
}
