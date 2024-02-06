package org.lab6.server.OBJECTS;

/**
 * This block describes dragon head
 */
public class DragonHead {

    private float size;

    /**
     * Needed to create dragon head
     *
     * @param size constructor
     */
    public DragonHead(float size) {
        this.size = size;
    }

    /**
     * Nedeed to get size of dragon head
     *
     * @return size returns size of dragon head
     */
    public float getSize() {
        return size;
    }

    /**
     * Updates dragon head size
     *
     * @param size needed to update dragon head size
     */
    public void setSize(float size) {
        this.size = size;
    }
}
