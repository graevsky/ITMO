package org.lab6.client;

public class ClientApp {
    public static void main(String[] args){
        Client client = new Client("localhost",5555);
        client.execute();
    }

}
