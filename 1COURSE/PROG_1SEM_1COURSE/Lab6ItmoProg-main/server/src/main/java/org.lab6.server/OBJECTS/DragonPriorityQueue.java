package org.lab6.server.OBJECTS;

import java.util.PriorityQueue;

/**
 * This dragon queue needed to save queue initialization date
 */
public class DragonPriorityQueue extends PriorityQueue<Dragon> {
    /**
     * Init date of collection
     */
    private final java.util.Date initDate;

    /**
     * dragon priority queue constructor(added inite date)
     */
    public DragonPriorityQueue() {
        super();
        initDate = new java.util.Date();
    }

    /**
     * Getter, which returns creatuon date of collection
     *
     * @return initDate
     */
    public java.util.Date getInitDate() {
        return initDate;
    }
}
