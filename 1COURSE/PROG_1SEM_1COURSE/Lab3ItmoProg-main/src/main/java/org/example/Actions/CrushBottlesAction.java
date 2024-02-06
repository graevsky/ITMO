package org.example.Actions;

import org.example.Actions.Executors.CrushBottlesExecutor;

import java.util.Objects;


public class CrushBottlesAction extends AbstractAction{



    public CrushBottlesAction() {
        super("CrushBottles", "Action to crush bottles into glass shards", new CrushBottlesExecutor());
    }
    @Override
    public String toString(){
        return "Shards";
    }
    @Override
    public int hashCode(){
        return Objects.hash(getName(),getDescription());
    }
}
