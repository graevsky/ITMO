package org.example.Actions;


import org.example.Exceptions.ItemNotFoundException;
import org.example.Human.Human;
import org.example.Things.AbstractThing;
import org.example.Things.GlassShards;


public class WatchCloserAction extends AbstractAction {

    public WatchCloserAction() {
        super("WatchCloserAction", "Action to watch closer to some body");

    }

    public void execute(Human human) {
        GlassShards glassShardsOptional;
        try {
            glassShardsOptional = (GlassShards) human.getItemFromInventory(GlassShards.class);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }


        AbstractThing thingToWatch = null;
        for (AbstractThing thing : human.getInventoryArray()) {
            if (thing != glassShardsOptional) {
                thingToWatch = thing;
                break;
            }
        }
        if (glassShardsOptional != null && thingToWatch != null) {
            int k = glassShardsOptional.getAmount();
            System.out.println(human.getName() + " watching closer to " + thingToWatch.getName() +
                    ". Result of watch: x:" + k * thingToWatch.getSize().getX() + " y:" + k * thingToWatch.getSize().getY() + " z:" + k * thingToWatch.getSize().getZ() + ". Wow! Things are " + k + "x time bigger!");
        } else {
            System.out.println("Nothing to watch!");
        }
    }
}
