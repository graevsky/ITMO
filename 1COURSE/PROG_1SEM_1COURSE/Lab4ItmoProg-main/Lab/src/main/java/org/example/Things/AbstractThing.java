package org.example.Things;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class AbstractThing implements ThingInterface {
    protected String name;
    protected int amount;
    protected String description;
    protected Size size;

    public AbstractThing(String name, int amount, String description, Size size) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.size = size;
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public Coords getCoords() {
        return new Coords(10, 20, 30);
    }

    ///TODO:INCL STATIC CLASS HERE
    public static class Coords {
        private final int x;
        private final int y;
        private final int z;

        public Coords(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }
}
