package org.example.Pokemons;

import org.example.Moves.SpecialMove.Fire_Blast;
import org.example.Moves.SpecialMove.Dragon_Rage;
import org.example.Moves.PhysicalMoves.Jump_Kick;

import ru.ifmo.se.pokemon.*;

/**
 * Class,that describes Marshtomp pokemon
 */
public class Marshtomp extends Pokemon{
    /**
     * Default constructor of the Marshtomp class pokemon
     * @param name is the name of Marshtomp pokemon
     * @param level is the level of Marshtomp pokemon
     */
    public Marshtomp(String name,int level){
        super(name,level);
        setType(Type.WATER);
        setStats(70,85,70,60,70,50);/*hp,att,def,spAtt,spDef,speed*/
        setMove(new Jump_Kick(),new Fire_Blast(),new Dragon_Rage());
    }
}
