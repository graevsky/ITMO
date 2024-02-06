package org.example.Pokemons;

import org.example.Moves.SpecialMove.Fire_Blast;
import org.example.Moves.SpecialMove.Dragon_Rage;

import ru.ifmo.se.pokemon.*;

/**
 * Class that describes Mudkip pokemon
 */
public class Mudkip extends Pokemon{
    /**
     * Default constructor of the Mudkip class pokemon
     * @param name is the name of Mudkip pokemon
     * @param level is the age of Mudkip pokemon
     */
    public Mudkip(String name,int level){
        super(name,level);
        setType(Type.WATER);
        setStats(50,70,50,50,50,40);/*hp,att,def,spAtt,spDef,speed*/
        setMove(new Dragon_Rage(), new Fire_Blast());
    }
}
