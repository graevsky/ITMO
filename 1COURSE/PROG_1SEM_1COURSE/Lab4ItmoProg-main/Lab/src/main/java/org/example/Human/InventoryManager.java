package org.example.Human;

import org.example.Exceptions.InventoryFullException;
import org.example.Things.AbstractThing;

import java.util.ArrayList;

public class InventoryManager {
    private final ArrayList<InventoryItem> inventory = new ArrayList<>();

    public void addThing(AbstractThing thing) {
        int MAX_INVENTORY_SIZE = 10;
        if (inventory.size() >= MAX_INVENTORY_SIZE) {
            throw new InventoryFullException();
        }
        InventoryItem item = new InventoryItem(thing);
        inventory.add(item);
    }

    public void removeFromInventory(AbstractThing thing) {
        inventory.remove(new InventoryItem(thing));
    }

    public AbstractThing getItemFromInventory(Class<? extends AbstractThing> itemClass) {
        for (InventoryItem item : inventory) {
            if (item.getThing().getClass().equals(itemClass)) {
                return item.getThing();
            }
        }
        return null;
    }

    public void getInventory() {
        for (InventoryItem inventoryItem : inventory) {
            System.out.println("Item: " + inventoryItem.getThing().getName() + ", amount: " + inventoryItem.getThing().getAmount() + "; ");
        }
    }

    public ArrayList<AbstractThing> getInventoryArray() {
        ArrayList<AbstractThing> inventoryArray = new ArrayList<>();
        for (InventoryItem item : inventory) {
            inventoryArray.add(item.getThing());
        }
        return inventoryArray;
    }

    ///TODO:NON STATIC CLASS HERE
    private class InventoryItem {
        private final AbstractThing thing;

        public InventoryItem(AbstractThing thing) {
            this.thing = thing;
        }

        public AbstractThing getThing() {
            return thing;
        }
    }
}
