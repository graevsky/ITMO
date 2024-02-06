package org.example.Actions;

import org.example.Human.Human;

import java.util.ArrayList;

public class MassLaught extends AbstractAction {

    protected ArrayList<Human> humanArrayList;

    public MassLaught(ArrayList<Human> humanArrayList) {
        super("MassLaught", "Action for mass laught");
        this.humanArrayList = humanArrayList;
    }

    public void execute(Human human) {
        System.out.println("Everything, that was said by " + human.getName() + " was fake! " + human.getName() + " is a CLOWN!");
        System.out.println();
        for (Human human1 : humanArrayList) {
            System.out.println(human1.getName() + " said: HAHAHA!");
        }
    }
}
