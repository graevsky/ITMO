package org.example.Jobs;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public abstract class AbstractJob implements JobInterface {
    protected String name;
    protected String description;

    public AbstractJob(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getJobDescription() {
        return description;
    }

    @Override
    public String getJobName() {
        return name;
    }
}
