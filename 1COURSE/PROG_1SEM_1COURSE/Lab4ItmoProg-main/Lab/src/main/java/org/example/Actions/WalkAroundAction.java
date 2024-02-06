package org.example.Actions;

import org.example.Human.Human;
import org.example.Locations.Location;

public class WalkAroundAction extends AbstractAction {
    protected Location loc1;
    protected Location loc2;

    public WalkAroundAction(Location loc1, Location loc2) {
        super("WalkAround", "Action to walk around");
        this.loc2 = loc2;
        this.loc1 = loc1;
    }

    public void execute(Human human) {
        System.out.println(human.getName() + " is going to " + loc2.getLocationName() + " from " + loc1.getLocationName());
        human.setCurrentLocation(loc2);
        human.setCurrentLocation(loc1);
    }
}
