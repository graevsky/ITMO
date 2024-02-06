package org.example.Moves.PhysicalMoves;
import ru.ifmo.se.pokemon.*;

/**
 * class Horn Drill extendes Physical Move
 * If it hits, Horn Drill is guaranteed to make the opponent faint. Its accuracy increases if the user is a higher level than the target Pokémon, but fails if the target is higher level. Also requeires special formula to caltulate accuracy.
 */
public class Horn_Drill extends PhysicalMove {
    /**
     * default constructor with type,damage,accuracy
     */
    public Horn_Drill(){
        super(Type.NORMAL,0, 0);
    }
    double acc = 0;
    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        if(def.getLevel() > att.getLevel()){
            return false;//no hit
        }else{
            acc = (30+att.getLevel()-def.getLevel())*0.01;
        }
        return true;//hit
    }
    int accuracy = (int) (acc*100);



    /**
     *
     * @param att is attack pokemon
     * @param def is defence pokemon
     * @return if hits or no
     */


    /**
     *
     * @param pokemon is the pokemon,that will take some effects
     */
    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect e = (new Effect()).attack(0.0).turns((int)(Math.random() * 4.0 + 1.0));
        e.chance(acc);
        pokemon.setCondition(e);
    }

    /**
     *
     * @return string,that needed to descript this move
     */
    @Override
    protected String describe() {
        return "using Horn-Drill";
    }
}
