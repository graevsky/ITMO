package org.example.Animals;

import org.example.Enums.AnimalTypeEnum;
import org.example.Human.AbstractHuman;

public class Juke extends AbstractAnimal {

    public Juke(String name) {
        super(name, AnimalTypeEnum.INSECT);
    }

    @Override
    public String makeSound() {
        return "ZZZZZZZZZZZZZZZZZZZ";
    }

    public void attackEntity(AbstractHuman human) {
        System.out.println(human.getName() + " was attacked by " + this.getAnimalName());
    }
}
