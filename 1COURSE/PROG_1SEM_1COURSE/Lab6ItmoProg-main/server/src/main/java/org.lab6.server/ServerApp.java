package org.lab6.server;

public class ServerApp {
    public static void main(String[] args){
        App app = new App("C:\\Users\\graev\\Desktop\\ITMO\\PROG_1SEM_1COURSE\\lab6\\Lab6ItmoProgTry2\\csv\\dragons.csv");
        ///home/studs/s386871/forHeliosLR6/csv
        //App app = new App("csv/dragons.csv");
        app.createQueue();
        Server server = new Server(5555,app);
        server.start();
    }
}
//ssh -L localhost:5555:localhost:5555 s386871@se.ifmo.ru -p 2222