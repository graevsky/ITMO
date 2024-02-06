package org.example.Things;

public class Telescope extends AbstractThing{
    protected int zoom;
    public Telescope(String name,String description,int amount,int zoom){
        super(name, amount, description);
        this.zoom = zoom;
    }
    int getZoom(){
        return zoom;
    }
    @Override
    public String toString(){
        return "Telescope "+name;
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
