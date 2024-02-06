package org.example.Actions;


import org.example.Enums.CelestialBody;
import org.example.Human.Human;
import org.example.Things.Telescope;


public class WatchBodyAction extends AbstractAction {

    protected Telescope telescope;
    protected CelestialBody body;

    public WatchBodyAction(Telescope telescope, CelestialBody body) {
        super("WatchBody", "Action to watch Celestial Bodies");
        this.telescope = telescope;
        this.body = body;
    }
    public WatchBodyAction( CelestialBody body) {
        super("WatchBody", "Action to watch Celestial Bodies");
        this.telescope = null;
        this.body = body;
    }


    public void execute(Human human) {
        System.out.println(human.getName() + " is watching " + body + " with " + ((telescope == null) ? "eyes" : telescope.getName()));
    }

}
