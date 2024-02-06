package org.example.Enums;

public enum AnimalTypeEnum {
    BIRD("Bird"),
    INSECT("Insect"),
    DOG("Dog");
    private final String description;

    AnimalTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public String getDescription() {
        return this.name();
    }
}
