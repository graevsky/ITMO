package org.example.Animals;

import org.example.Enums.AnimalTypeEnum;

public class Dog extends AbstractAnimal {
    public Dog(String name) {
        super(name, AnimalTypeEnum.DOG);
    }

    @Override
    public String makeSound() {
        return "Woof";
    }
}
