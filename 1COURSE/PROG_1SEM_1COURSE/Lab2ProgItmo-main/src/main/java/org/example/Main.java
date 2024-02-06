package org.example;

import ru.ifmo.se.pokemon.*;
import org.example.Pokemons.*;

/**
 * @author graevsky
 * @version  0.00.0.0.1
 * This is the main class of lab 2 prog itmo.
 * Adding allies,adding axis,starting battle
 */
public class Main {
    public static void main(String[] args) {
        /**
         * Creation of battle object
         */
        Battle battle = new Battle();
        /**
         * Adding alllies to the battle(with their names and levels)
         */
        battle.addAlly(new Hoothoot("poke 1",1));
        battle.addAlly(new Marshtomp("poke 2",2));
        battle.addAlly(new Mudkip("poke 3",3));
        /**
         * Adding axis to the battle(with their names and levels)
         */
        battle.addFoe(new Noctowl("poke 4",4));
        battle.addFoe(new Swampert("poke 5",5));
        battle.addFoe(new Zangoose("poke 6",6));
        /**
         * Starting the battle
         */
        battle.go();
    }


}