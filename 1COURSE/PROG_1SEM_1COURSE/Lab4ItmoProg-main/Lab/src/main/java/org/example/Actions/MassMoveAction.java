package org.example.Actions;

import org.example.Human.Human;
import org.example.Locations.Location;

import java.util.ArrayList;

public class MassMoveAction extends AbstractAction {

    protected ArrayList<Human> humans;
    protected Location location_to_move;

    public MassMoveAction(ArrayList<Human> humans, Location location_to_move) {
        super("MassMoveAction", "Action for mass move");
        this.humans = humans;
        this.location_to_move = location_to_move;
    }

    public void execute(Human human) {
        System.out.println("Mass move was caused by " + human.getName());

        for (Human human1 : humans) {
            human1.setCurrentLocation(location_to_move);
        }
    }
}
