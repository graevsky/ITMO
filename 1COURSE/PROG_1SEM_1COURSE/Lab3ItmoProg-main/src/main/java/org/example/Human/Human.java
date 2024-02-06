package org.example.Human;

import org.example.Actions.AbstractAction;

import org.example.Actions.CrushBottlesAction;
import org.example.Locations.Location;
import org.example.Things.AbstractThing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Human extends AbstractHuman {



    public Human(String name,int age,Location location) {
        super(name,age,location);
        System.out.println("Hooman - " + name + " with age " + age + " created successfully at "+ location.getLocationName() +"!");
    }
    @Override
    public String toString(){
        return "Human " + name+", " +age;
    }
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        return false;
    }
    @Override
    public int hashCode(){
        return name.hashCode();
    }





}
