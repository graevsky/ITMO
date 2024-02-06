package org.example.Things;

public class Telescope extends AbstractThing {
    protected int zoom;

    public Telescope(String name, String description, int amount, Size size, int zoom) {
        super(name, amount, description, size);
        this.zoom = zoom;
    }
}
