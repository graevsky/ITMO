package org.example.Actions;


import org.example.Exceptions.ItemNotFoundException;
import org.example.Human.Human;
import org.example.Things.Rifle;

public class ShootRifleAction extends AbstractAction{
    public ShootRifleAction(){super("ShootRifle","Action to shoot rifle");}

    public void execute(Human human){
        Rifle rifle;
        try {
            rifle = (Rifle) human.getItemFromInventory(Rifle.class);
        }catch (ItemNotFoundException e){
            System.out.println(e.getMessage());
            return;
        }

        if(rifle != null){
            System.out.println("BANG BANG BATYA V ZDANII!!!");
        }else {
            System.out.println("No rifle to shoot");
        }
    }
}
