package org.example.Actions;

import org.example.Actions.Executors.CreateTelescopeExecutor;


import java.util.Objects;


public class CreateTelescopeAction extends AbstractAction {
    protected String telescopeName;

    public CreateTelescopeAction(String telescopeName) {
        super("CreateTelescope", "Creates telescope from glass shards", new CreateTelescopeExecutor());
        this.telescopeName = telescopeName;
    }

    public String getTelescopeName() {
        return telescopeName;
    }

    @Override
    public String toString(){
        return "Telescope";
    }
    @Override
    public int hashCode(){
        return Objects.hash(getName(), getDescription());
    }
}
