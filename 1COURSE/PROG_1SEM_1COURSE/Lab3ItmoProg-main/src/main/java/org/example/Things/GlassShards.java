package org.example.Things;

public class GlassShards extends AbstractThing{
    public GlassShards(String name,String description,int amount){
        super(name, amount, description);
    }
    @Override
    public String toString(){
        return "Glass shards "+name;
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
