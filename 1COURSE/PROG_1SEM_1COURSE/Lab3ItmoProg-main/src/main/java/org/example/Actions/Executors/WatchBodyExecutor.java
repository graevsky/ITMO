package org.example.Actions.Executors;

import org.example.Actions.AbstractAction;
import org.example.Actions.CelestialBody;
import org.example.Actions.Executors.ActionExecutor;
import org.example.Actions.WatchBodyAction;
import org.example.Human.Human;
import org.example.Things.Telescope;

public class WatchBodyExecutor implements ActionExecutor {
    public Human executeAction(AbstractAction action, Human human) {
        WatchBodyAction watchBodyAction = (WatchBodyAction) action;
        Telescope telescope = watchBodyAction.getTelescope();
        CelestialBody body = watchBodyAction.getBody();

        // Perform the execution of the action with the specific parameters
        System.out.println("Watching " + body + " with " + telescope.getName());
        return human;
    }
}
