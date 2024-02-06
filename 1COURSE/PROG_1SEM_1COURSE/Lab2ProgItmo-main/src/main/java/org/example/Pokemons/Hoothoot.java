package org.example.Pokemons;

import org.example.Moves.StatusMove.Protect;
import org.example.Moves.SpecialMove.Dragon_Rage;
import org.example.Moves.SpecialMove.Fire_Blast;
import ru.ifmo.se.pokemon.*;

/**
 * Class,that describes Hoothoot pokemon.
 */
public class Hoothoot extends Pokemon{
    /**
     * Default constructor with name and level.
     * @param name name of the hoothoot pokemon
     * @param level level of the hoothoot pokemon
     */
    public Hoothoot(String name,int level){
        super(name,level);
        setType(Type.NORMAL,Type.FLYING);
        setStats(60,30,30,36,56,50);/*hp,att,def,spAtt,spDef,speed*/
        setMove(new Dragon_Rage(), new Protect(),new Fire_Blast());
    }
}
