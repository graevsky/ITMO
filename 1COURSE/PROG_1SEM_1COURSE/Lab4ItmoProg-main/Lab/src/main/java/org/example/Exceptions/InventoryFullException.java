package org.example.Exceptions;

public class InventoryFullException extends RuntimeException {
    public InventoryFullException() {
        super("My inventory is full!");
    }
}
