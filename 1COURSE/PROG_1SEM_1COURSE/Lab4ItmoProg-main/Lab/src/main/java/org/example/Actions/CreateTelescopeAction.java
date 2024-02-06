package org.example.Actions;

import org.example.Exceptions.ItemNotFoundException;
import org.example.Human.Human;
import org.example.Things.GlassShards;
import org.example.Things.Size;
import org.example.Things.Telescope;


public class CreateTelescopeAction extends AbstractAction {

    static int counter = 1;

    public CreateTelescopeAction() {
        super("CreateTelescope", "Action to create telescope from shards");

    }

    public void execute(Human human) {
        GlassShards glassShardsOptional;
        try {
            glassShardsOptional = (GlassShards) human.getItemFromInventory(GlassShards.class);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        int zoom = glassShardsOptional.getAmount();
        human.removeFromInventory(glassShardsOptional);
        String telescopeName = "Telescope №" + counter;
        Telescope telescope = new Telescope(telescopeName, "", 1, new Size(400, 400, 900), zoom);
        human.addThing(telescope);
        System.out.println("Telescope " + telescopeName + " with zoom " + zoom + "x was created.");

        counter++;
    }
}
