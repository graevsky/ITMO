package org.example.Moves.PhysicalMoves;
import ru.ifmo.se.pokemon.*;

/**
 * Class Jump Kick extends Physical Move.Jump Kick deals damage, however, if it misses the user keeps going and crashes, losing 1⁄2 of its HP.
 */
public class Jump_Kick extends PhysicalMove{
    /**
     * default constructor with type,damage,accuracy
     */
    public Jump_Kick(){
        super(Type.FIGHTING,100,95);

    }

    /**
     *
     * @param att is attack pokemon
     * @param def is defence pokemon
     * @return base damage for the attack/defense pokemon
     */
    @Override
    protected double calcBaseDamage(Pokemon att, Pokemon def) {
        double damage = super.calcBaseDamage(att, def);//level and power
        if(checkAccuracy(att,def)){ // if attack happened
            return damage;
        }
        else{
            return 0.5*att.getHP();/*поидее нужен макс хп...*/
        }

        /*selft дальше автоматом судя по attack?*/
    }

    /**
     *
     * @return string needed to describe this move
     */
    @Override
    protected String describe() {
        return "using Jump-Kick";
    }
}
