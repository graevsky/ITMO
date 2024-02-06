package org.example.Moves.SpecialMove;

import ru.ifmo.se.pokemon.*;

/**
 * Class Dragon Rage extends Special Move.
 * Only deals 40 HP damage to the opponent.
 */

public class Dragon_Rage extends SpecialMove{
    /**
     * default constructor with type,damage,accuracy
     */
    public Dragon_Rage(){
        super(Type.DRAGON,0,100);
    }
    /*checkAccuracy Для определенных атак требует перопределения, например, когда атака всегда успешна.
     */

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    /**
     * Applies damage to the pokemon
     * @param pokemon is the pokemon,that will be damaged
     * @param v default damage(changed to 40 hp)
     */

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        super.applyOppDamage(pokemon, 40);
    }

    /**
     *
     * @return string needed to describe this move
     */
    @Override
    protected String describe() {
        return "using Dragon-Rage";
    }
}
