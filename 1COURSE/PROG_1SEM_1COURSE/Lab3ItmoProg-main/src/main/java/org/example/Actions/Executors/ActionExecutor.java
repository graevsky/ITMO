package org.example.Actions.Executors;

import org.example.Actions.AbstractAction;
import org.example.Human.Human;

public interface ActionExecutor {
    Human executeAction(AbstractAction action, Human human);
}
