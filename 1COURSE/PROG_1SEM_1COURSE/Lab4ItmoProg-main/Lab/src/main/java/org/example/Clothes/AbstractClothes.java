package org.example.Clothes;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.Enums.ClothesTypesEnum;

@EqualsAndHashCode
@ToString
public abstract class AbstractClothes implements ClothesInterface {
    protected ClothesTypesEnum clothesTypesEnum;
    protected String brand;
    protected String color;

    public AbstractClothes(ClothesTypesEnum clothesTypesEnum, String brand, String color) {
        this.brand = brand;
        this.clothesTypesEnum = clothesTypesEnum;
        this.color = color;
    }

    @Override
    public String getBrand() {
        return "Clothes made by " + brand;
    }

    @Override
    public String getType() {
        return "Clothes for " + clothesTypesEnum.getType();
    }

    @Override
    public String getColor() {
        return "Clothes with color " + color;
    }
}

