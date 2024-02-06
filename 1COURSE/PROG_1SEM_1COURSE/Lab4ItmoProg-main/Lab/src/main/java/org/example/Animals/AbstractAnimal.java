package org.example.Animals;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.Enums.AnimalTypeEnum;

@EqualsAndHashCode
@ToString
public abstract class AbstractAnimal implements Animalnterface {
    public String name;
    protected AnimalTypeEnum animalTypeEnum;

    public AbstractAnimal(String name, AnimalTypeEnum animalTypeEnum) {
        this.animalTypeEnum = animalTypeEnum;
        this.name = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAnimalName() {
        return name;
    }

    @Override
    public String getAnimalType() {
        return animalTypeEnum.getDescription();
    }
}
