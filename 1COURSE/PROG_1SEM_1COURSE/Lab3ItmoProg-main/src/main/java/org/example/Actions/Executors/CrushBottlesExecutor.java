package org.example.Actions.Executors;

import org.example.Actions.AbstractAction;
import org.example.Actions.CrushBottlesAction;
import org.example.Human.Human;
import org.example.Things.Bottle;
import org.example.Things.GlassShards;

public class CrushBottlesExecutor implements ActionExecutor {
    public Human executeAction(AbstractAction action, Human human) {
        CrushBottlesAction crushBottlesAction = (CrushBottlesAction) action;
        // No additional parameters are needed for CrushBottlesAction

        // Perform the execution of the action
        Bottle bottle = (Bottle) human.getItemFromInventory(Bottle.class);
        if (bottle != null) {
            int amountOfShards = bottle.getAmount();
            human.removeFromInventory(bottle);
            GlassShards shards = new GlassShards("Glass shards", "Just shards", amountOfShards);
            human.addThing(shards);
            System.out.println("Shards " + shards.getAmount() + " were created.");
        } else {
            System.out.println("Cannot create glass shards. No bottles found in inventory.");
        }

        return human;
    }
}
