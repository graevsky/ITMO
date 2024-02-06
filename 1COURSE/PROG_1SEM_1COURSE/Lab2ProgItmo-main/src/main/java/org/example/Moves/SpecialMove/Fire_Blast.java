package org.example.Moves.SpecialMove;
import ru.ifmo.se.pokemon.*;

/**
 * Class Fire Blast extends Special Move. Fire Blast deals damage and has a 10% chance of burning the target.
 */
public class Fire_Blast extends SpecialMove{
    /**
     * Default constructor with type,damage,accuracy
     */
    public Fire_Blast(){
        super(Type.FIRE,110,85);
    }

    /**
     * Applies damage to the opponent pokemon
     * @param pokemon is the pokemon, that will be damaged
     * @param v is the damage
     */
    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        super.applyOppDamage(pokemon, v);
    }

    /**
     * Needed to check, if the dragon will be bruned
     * @param pokemon is the dragon to be burned
     */
    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if(0.1 > Math.random()){
            Effect.burn(pokemon);
        }
    }

    /**
     *
     * @return string needed to describe this move
     */
    @Override
    protected String describe() {
        return "using Fire-Blast";
    }
}
