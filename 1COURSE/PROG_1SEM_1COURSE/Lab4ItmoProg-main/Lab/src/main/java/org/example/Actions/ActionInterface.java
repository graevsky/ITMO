package org.example.Actions;

import org.example.Human.Human;

public interface ActionInterface {
    String getName();

    void execute(Human human);

    String getDescription();
}
