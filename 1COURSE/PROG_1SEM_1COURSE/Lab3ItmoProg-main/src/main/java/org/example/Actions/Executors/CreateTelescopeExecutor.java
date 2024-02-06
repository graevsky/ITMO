package org.example.Actions.Executors;
import org.example.Actions.AbstractAction;
import org.example.Actions.CreateTelescopeAction;
import org.example.Actions.Executors.ActionExecutor;
import org.example.Human.Human;
import org.example.Things.GlassShards;
import org.example.Things.Telescope;

public class CreateTelescopeExecutor implements ActionExecutor {
    public Human executeAction(AbstractAction action, Human human) {
        CreateTelescopeAction createTelescopeAction = (CreateTelescopeAction) action;
        // Extract required parameters from createTelescopeAction
        String telescopeName = createTelescopeAction.getTelescopeName();

        // Perform the execution of the action with the specific parameters
        GlassShards glassShardsOptional = (GlassShards) human.getItemFromInventory(GlassShards.class);
        if (glassShardsOptional != null) {
            int zoom = glassShardsOptional.getAmount();
            human.removeFromInventory(glassShardsOptional);
            Telescope telescope = new Telescope(telescopeName, "", 1, zoom);
            human.addThing(telescope);
            System.out.println("Telescope " + telescopeName + " with zoom " + zoom + "x was created.");
        } else {
            System.out.println("Cannot create telescope. No glass shards found in inventory.");
        }
        return human;
    }
}