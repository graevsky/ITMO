package org.example.Actions;

import org.example.Actions.Executors.WatchBodyExecutor;
import org.example.Things.Telescope;


public class WatchBodyAction extends AbstractAction{

    protected Telescope telescope;
    protected CelestialBody body;

    public WatchBodyAction(Telescope telescope, CelestialBody body) {
        super("WatchBody", "Action to watch Celestial Bodies", new WatchBodyExecutor());
        this.telescope = telescope;
        this.body = body;
    }

    public CelestialBody getBody() {
        return body;
    }

    public Telescope getTelescope() {
        return telescope;
    }

    @Override
    public String toString(){
        return "Telescope " +telescope.getName() + " and " +body;
    }
    @Override
    public int hashCode(){
        return telescope.hashCode() + body.hashCode();
    }
}
