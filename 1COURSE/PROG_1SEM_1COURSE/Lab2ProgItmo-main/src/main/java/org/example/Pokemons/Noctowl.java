package org.example.Pokemons;

import org.example.Moves.SpecialMove.Luster_Purge;
import org.example.Moves.StatusMove.Protect;
import org.example.Moves.SpecialMove.Dragon_Rage;
import org.example.Moves.SpecialMove.Fire_Blast;
import ru.ifmo.se.pokemon.*;

/**
 * Class, that describes Noctowl class pokemon
 */

public class Noctowl extends Pokemon{
    /**
     * Defualt constructor for Noctowl class pokemon
     * @param name is the name of Noctowl pokemon
     * @param level is the level of Noctowl pokemon
     */
    public Noctowl(String name,int level){
        super(name,level);
        setType(Type.NORMAL,Type.FLYING);
        setStats(100,50,50,86,96,70);/*hp,att,def,spAtt,spDef,speed*/
        setMove(new Luster_Purge(),new Protect(),new Fire_Blast(),new Dragon_Rage());
    }
}
