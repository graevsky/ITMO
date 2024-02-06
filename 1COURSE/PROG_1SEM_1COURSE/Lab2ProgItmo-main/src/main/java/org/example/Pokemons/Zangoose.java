package org.example.Pokemons;

import org.example.Moves.PhysicalMoves.Brick_Break;
import org.example.Moves.PhysicalMoves.Drill_Peck;
import org.example.Moves.SpecialMove.Dragon_Rage;
import org.example.Moves.SpecialMove.Tri_Attack;
import ru.ifmo.se.pokemon.*;

/**
 * Class that describes Zangoose family pokemon
 */

public class Zangoose extends Pokemon{
    /**
     * Default constructor for Zangoose family pokemons
     * @param name is the name of  Zangoose pokemon
     * @param level is the level of Zangoose pokemon
     */
    public Zangoose(String name,int level){
        super(name,level);
        setType(Type.NORMAL);
        setStats(73,115,60,60,60,90);/*hp,att,def,spAtt,spDef,speed*/
        setMove(new Dragon_Rage(), new Tri_Attack(), new Brick_Break(),new Drill_Peck());
    }
}
