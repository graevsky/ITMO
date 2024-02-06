package org.example.Locations;

import java.util.Objects;

public class Location extends AbstractLocation {


    public Location(String locationName, String locationDescription){
        super(locationName,locationDescription);
    }

    @Override
    public String toString(){
        return "Location "+locationName;
    }
    @Override
    public int hashCode(){
        return locationName.hashCode();
    }
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        return false;
    }
}
