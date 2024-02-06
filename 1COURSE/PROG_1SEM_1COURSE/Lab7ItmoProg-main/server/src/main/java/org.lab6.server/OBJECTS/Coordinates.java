package org.lab6.server.OBJECTS;

/**
 * This block descrbes x and y coordinates of dragon
 */
public class Coordinates {
    private final float x;

    private final Integer y;

    /**
     * Constructor needed to create new coordinates
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Coordinates(float x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns x coordinate
     *
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * Returns y coordinate
     *
     * @return y
     */
    public Integer getY() {
        return y;
    }

}
