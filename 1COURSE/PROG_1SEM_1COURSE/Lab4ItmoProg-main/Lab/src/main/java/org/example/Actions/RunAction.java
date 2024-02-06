package org.example.Actions;

import org.example.Human.Human;

public class RunAction extends AbstractAction {

    public RunAction() {
        super("RunAction", "Action needed for running");
    }

    public void execute(Human human) {
        System.out.println(human.getName() + " is running 10 times faster that a speed of light at " + human.getCurrentLocation().getLocationName());
    }
}
