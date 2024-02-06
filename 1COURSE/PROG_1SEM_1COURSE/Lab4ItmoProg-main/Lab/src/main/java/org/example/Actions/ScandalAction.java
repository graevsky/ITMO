package org.example.Actions;

import org.example.Human.Human;

public class ScandalAction extends AbstractAction {

    protected Human opponent;

    public ScandalAction(Human opponent) {
        super("Scandal", "Action to make scandal");
        this.opponent = opponent;
    }

    public void execute(Human human) {
        System.out.println();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        human.removeFriend(opponent);
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        human.addFriend(opponent);
    }
}
