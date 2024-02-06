package org.example.Moves.StatusMove;
import ru.ifmo.se.pokemon.*;

/**
 * Protect extends Status Move. rotect prevents any attacks targeted at the user from striking, for the duration of the turn. It has priority +4 so will activate before most other moves. The move functions identically to Detect.
 */
public class Protect extends StatusMove{
    /**
     * Default constructor with type,damage,accuracy
     */
    public Protect(){
        super(Type.NORMAL,0.0,0.0,4,1);
    }

    /**
     * Applies self effects to the pokemon.
     * @param pokemon it the pokemon, that will get some effects.
     */
    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.DEFENSE,3);
        pokemon.setMod(Stat.SPECIAL_DEFENSE,2);
    }

    /**
     *
     * @return string that describes this move
     */
    @Override
    protected String describe() {
        return "using Protect";
    }
}
