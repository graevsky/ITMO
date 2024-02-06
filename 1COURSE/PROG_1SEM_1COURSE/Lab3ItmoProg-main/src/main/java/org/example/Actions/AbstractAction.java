package org.example.Actions;

import org.example.Actions.Executors.ActionExecutor;
import org.example.Human.Human;

public abstract class AbstractAction implements ActionInterface {
    protected String name;
    protected String description;
    protected ActionExecutor executor;

    public AbstractAction(String name, String description, ActionExecutor executor) {
        this.name = name;
        this.description = description;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Human executeAction(Human human) {
        return executor.executeAction(this, human);
    }
}