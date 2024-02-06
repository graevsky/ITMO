package org.example.Actions;

import org.example.Exceptions.ItemNotFoundException;
import org.example.Human.Human;
import org.example.Things.Bottle;
import org.example.Things.GlassShards;
import org.example.Things.Size;


public class CrushBottlesAction extends AbstractAction {


    public CrushBottlesAction() {
        super("CrushBottles", "Action to crush bottles to get some shards");
    }

    public void execute(Human human) {
        Bottle bottle;
        try {
            bottle = (Bottle) human.getItemFromInventory(Bottle.class);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        int ammountOfShards = bottle.getAmount();
        human.removeFromInventory(bottle);
        GlassShards shards = new GlassShards("Glass shards", "Just shards",
                ammountOfShards, new Size(10, 10, 10));
        human.addThing(shards);
        System.out.println(shards.getAmount() + " shards were created by " + human.getName());

    }

}
