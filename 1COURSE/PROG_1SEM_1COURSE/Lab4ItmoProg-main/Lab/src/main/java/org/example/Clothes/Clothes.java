package org.example.Clothes;

import org.example.Enums.ClothesTypesEnum;

public class Clothes extends AbstractClothes {
    public Clothes(ClothesTypesEnum clothesTypesEnum, String brand, String color) {
        super(clothesTypesEnum, brand, color);
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getType() {
        return clothesTypesEnum.getType();
    }

    @Override
    public String getColor() {
        return color;
    }

    public String getClothesDescription() {
        return clothesTypesEnum + " made by " + brand + " with color " + color;
    }
}
