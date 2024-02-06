package org.example.Moves.PhysicalMoves;
import ru.ifmo.se.pokemon.*;

/**
 * Class that just deal damage. Extends Physical move
 */

public class Drill_Peck extends PhysicalMove{
    /**
     * Default constructor with type,damage,accuracy
     */
    public Drill_Peck(){
        super(Type.FLYING,80,100);
    }

    /**
     *  Applies damage to some pokemon
     * @param pokemon is the pokemon, that will be damaged
     * @param v is the damage
     */
    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        super.applyOppDamage(pokemon, v);
    }

    /**
     * @return string string needed to descript
     */
    @Override
    protected String describe() {
        return "using Drill-Peck";
    }
}
