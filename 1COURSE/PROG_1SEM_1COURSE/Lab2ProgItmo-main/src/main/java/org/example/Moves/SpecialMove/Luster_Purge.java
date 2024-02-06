package org.example.Moves.SpecialMove;
import ru.ifmo.se.pokemon.*;

/**
 * Luster Purge extends Special Move.Luster Purge deals damage and has a 50% chance of lowering the target's Special Defense by one stage.
 * Stats can be lowered to a minimum of -6 stages each.
 */
public class Luster_Purge extends SpecialMove{
    /**
     * Default constructor with type,damage,accuracy.
     */
    public Luster_Purge(){
        super(Type.PSYCHIC,70,100);
    }

    /**
     * Applies damage to the pokemon.
     * @param pokemon is the pokemon, that will be damaged.
     * @param v is the damage.
     */
    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        super.applyOppDamage(pokemon, v);
    }

    /**
     * Checks, if the dragon will be damaged.
     * @param pokemon is the pokemon,that will be damaged
     */
    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if(0.5 > Math.random()){
            pokemon.setMod(Stat.SPECIAL_DEFENSE,-1);
        }
    }

    /**
     * @return string needed to describe this move.
     */
    @Override
    protected String describe() {
        return "using Juster-Purge";
    }
}
