package org.example.Actions;

import org.example.Human.Human;

import java.util.ArrayList;

public class WatchSunCloserAction extends AbstractAction {

    protected ArrayList<Human> humans;

    public WatchSunCloserAction(ArrayList<Human> humans) {
        super("WatchSunCloserAction", "Action to watch sun closer");
        this.humans = humans;
    }

    public void execute(Human human) {
        System.out.println("Because of " + human.getName() + " everybody eyes will hurt!");
        for (Human human1 : humans) {
            System.out.println(human1.getName() + " is watching Sun closely. His eyes are not ok!");
        }
    }
}
