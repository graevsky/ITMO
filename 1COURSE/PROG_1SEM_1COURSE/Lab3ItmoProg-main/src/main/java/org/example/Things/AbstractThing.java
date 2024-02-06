package org.example.Things;

public abstract class AbstractThing implements ThingInterface {
    protected String name;
    protected int amount;
    protected String description;

    public AbstractThing(String name, int amount, String description) {
        this.name = name;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
