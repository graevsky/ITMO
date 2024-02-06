package org.example.Exceptions;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String nameOfItem) {
        super("Item not found in inventory: " + nameOfItem);
    }
}
