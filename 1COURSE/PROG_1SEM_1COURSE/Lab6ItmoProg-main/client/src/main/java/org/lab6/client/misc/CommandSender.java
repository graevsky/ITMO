package org.lab6.client.misc;

import org.lab6.common.CommData;
import org.lab6.common.ResultData;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class CommandSender {
    public CommandSender(){

    }
    public void sendCommand(SocketChannel client, String command, String commandParams) throws IOException, ClassNotFoundException {
        CommData outgoingCommand = new CommData(command, commandParams);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(outgoingCommand);
        oos.flush();

        byte[] bytes = baos.toByteArray();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        client.write(buffer);
        buffer.clear();

        ByteBuffer responseBuffer = ByteBuffer.allocate(2048);
        client.read(responseBuffer);
        responseBuffer.flip();

        ByteArrayInputStream bais = new ByteArrayInputStream(responseBuffer.array());
        ObjectInputStream ois = new ObjectInputStream(bais);
        ResultData resultData = (ResultData) ois.readObject();

        System.out.println("Response: " + (resultData.getResultId() == 1 ? resultData.getResult() : resultData.getError()));
    }
}
