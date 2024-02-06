package org.example.Actions;

import org.example.Human.Human;

import java.util.ArrayList;

public class PanicAction extends AbstractAction {
    protected ArrayList<Human> humanList;

    public PanicAction(ArrayList<Human> humanList) {
        super("PanicAction", "Action for panic");
        this.humanList = humanList;
    }

    public void execute(Human human) {
        System.out.println("Panic was caused by " + human.getName());
        for (Human human1 : humanList) {
            System.out.println((human1.getJob() == null) ? human1.getName() + " is doing nothing 10 times faster than before!" : human1.getName() + " is doing " + human1.getJob().getJobName() + " 10 times faster than before!");
        }
    }
}
