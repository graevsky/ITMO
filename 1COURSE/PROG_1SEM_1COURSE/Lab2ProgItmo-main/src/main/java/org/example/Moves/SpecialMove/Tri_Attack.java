package org.example.Moves.SpecialMove;
import ru.ifmo.se.pokemon.*;

/**
 * Tri Attack extends Special Move. Tri Attack deals damage and has a 20% chance of paralyzing, burning or freezing the target - i.e. a 6.67% chance of each status condition.
 */
public class Tri_Attack extends SpecialMove{
    /**
     * Default constructor with type,damage,accuracy
     */
    public Tri_Attack(){
        super(Type.NORMAL,80,100);
    }

    /**
     * Applies effects to the pokemon
     * @param pokemon is the pokemon, that will get some effects.
     */
    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if(0.2 > Math.random()){
            double a = Math.random();
            if(a < 0.33){
                Effect.freeze(pokemon);
            }else if(a < 0.667){
                Effect.paralyze(pokemon);
            }else {
                Effect.burn(pokemon);
            }

        }
    }

    /**
     * Damages the pokemon.
     * @param pokemon is the pokemon, that will be damaged.
     * @param v is the damage.
     */
    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        super.applyOppDamage(pokemon, v);
    }

    /**
     *
     * @return string that describes  this move
     */
    @Override
    protected String describe() {
        return "using Tri-Attack";
    }
}
