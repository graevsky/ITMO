package org.example.Enums;

public enum ClothesTypesEnum {
    HEAD("Hat"),
    BODY("Apparel"),
    LEGS("Garment"),
    SHOES("Shoes");
    private final String description;

    ClothesTypesEnum(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public String getType() {
        return this.name();
    }
}
