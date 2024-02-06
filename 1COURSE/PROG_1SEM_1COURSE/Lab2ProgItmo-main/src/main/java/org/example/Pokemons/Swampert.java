package org.example.Pokemons;

import org.example.Moves.SpecialMove.Fire_Blast;
import org.example.Moves.SpecialMove.Dragon_Rage;
import org.example.Moves.PhysicalMoves.Jump_Kick;
import org.example.Moves.PhysicalMoves.Horn_Drill;

import ru.ifmo.se.pokemon.*;

/**
 * Class that describes Swampert class pokemons
 */
public class Swampert extends Pokemon{
    /**
     * Default constructor for Swampert class pokemons.
     * @param name is the name of Swampert pokemon
     * @param level is the level of Swampert pokemon
     */

    /*CHECK WHI NOT FULL PART OF PARAMS    */
    public Swampert(String name,int level){
        super(name,level);
        setType(Type.WATER);
        setStats(100,110,90,85,90,60);/*hp,att,def,spAtt,spDef,speed*/
        setMove(new Horn_Drill(),new Fire_Blast(),new Dragon_Rage(),new Jump_Kick());
    }
}
