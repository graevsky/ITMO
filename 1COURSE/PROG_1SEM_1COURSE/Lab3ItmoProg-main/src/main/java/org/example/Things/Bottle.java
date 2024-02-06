package org.example.Things;

public class Bottle extends AbstractThing{
    public Bottle(String name,String description,int amount){
        super(name,amount,description);
    }
    @Override
    public String toString(){
        return "Bottle "+name;
    }
    @Override
    public int hashCode(){
        return name.hashCode();
    }
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        return false;
    }
}
