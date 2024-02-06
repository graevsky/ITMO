package org.example.Moves.PhysicalMoves;
import ru.ifmo.se.pokemon.*;

/**
 * class Brick Break extends Physical Move.
 * Just deal damage
 */
public class Brick_Break extends PhysicalMove{
    /**
     * defual constructor with type,damage,accuracy
     */
    public Brick_Break(){
        super(Type.FIGHTING,75,100);
    }

    /**
     *
     * @param pokemon is the pokemon,that will be damaged
     * @param v is the damage for the pokemon
     */
    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        super.applyOppDamage(pokemon, v);
    }

    /**
     * @return string needed to descript this move
     */
    @Override
    protected String describe() {
        return "using Brick-Break";
    }
}
