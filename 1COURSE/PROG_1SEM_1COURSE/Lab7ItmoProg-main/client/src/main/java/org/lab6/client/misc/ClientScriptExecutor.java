package org.lab6.client.misc;

import org.lab6.common.CommData;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ClientScriptExecutor {
    private static final int max_count = 5;

    public void executeScript(SocketChannel client, String filePath) throws IOException, ClassNotFoundException {
        ///TODO:stream api i lambda
        CommandSender commandSender = new CommandSender();
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.limit(max_count)
                    .map(line -> line.split(" ", 2))
                    .map(words -> new CommData(words[0], words.length >= 2 ? words[1] : ""))
                    .forEach(commData -> {
                        try {
                            commandSender.sendCommand(client, commData.getName(), commData.getArgs());
                        } catch (IOException | ClassNotFoundException e) {
                            throw new UncheckedIOException((IOException) e);
                        }
                    });
        }
    }
}
