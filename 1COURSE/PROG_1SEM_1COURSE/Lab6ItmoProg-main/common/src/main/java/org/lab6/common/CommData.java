package org.lab6.common;


import java.io.*;

public class CommData implements Serializable {
    private String name;
    private String args;

    public String getName(){
        return name;
    }
    public String getArgs(){
        return args;
    }
    public CommData(String name,String args){
        this.name = name;
        this.args = args;
    }
    public int getArgsLen(){
        return args.split(" ").length;
    }

}
