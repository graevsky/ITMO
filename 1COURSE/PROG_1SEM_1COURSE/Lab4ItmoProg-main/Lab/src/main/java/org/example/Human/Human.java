package org.example.Human;


import org.example.Actions.ActionInterface;
import org.example.Actions.CrushBottlesAction;
import org.example.Animals.AbstractAnimal;
import org.example.Buildings.AbstractBuilding;
import org.example.Clothes.Clothes;
import org.example.Exceptions.ItemNotFoundException;
import org.example.Jobs.AbstractJob;
import org.example.Locations.Location;
import org.example.Things.AbstractThing;
import org.example.Things.Bottle;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class Human extends AbstractHuman {
    public static ArrayList<Human> humanArrayList = new ArrayList<>();
    private final InventoryManager inventoryManager = new InventoryManager();
    protected ArrayList<Clothes> clothes;
    protected AbstractAnimal animal;
    protected AbstractThing favouriteThing;
    protected ArrayList<Human> friendList = new ArrayList<>();
    protected AbstractJob job;


    public Human(String name, int age, Location location, ArrayList<Clothes> clothes, AbstractAnimal animal, AbstractBuilding house, AbstractJob job) {
        super(name, age, location, house);
        this.clothes = clothes;
        this.animal = animal;
        this.job = job;
        System.out.println("Hooman - " + name + " with age " + age + " created successfully at " + location.getLocationName() + "!");
        humanArrayList.add(this);
    }

    public ArrayList<Human> getHumanArrayList() {
        return humanArrayList;
    }

    ///TODO:LOCAL CLASS HERE
    public void crushBottlesAndCollectShards() {
        class ShardsCollector {
            private int totalShards = 0;

            public void collectShards() {
                Bottle bottle;
                try {
                    bottle = (Bottle) getItemFromInventory(Bottle.class);
                } catch (ItemNotFoundException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                if (bottle != null) {
                    int shards = bottle.getAmount();
                    removeFromInventory(bottle);
                    totalShards += shards;
                    System.out.println("Collected " + shards + " shards from bottles.");
                } else {
                    System.out.println("No bottles found in inventory.");
                }
            }

            public int getTotalShards() {
                return totalShards;
            }
        }

        ShardsCollector shardsCollector = new ShardsCollector();
        CrushBottlesAction crushBottlesAction = new CrushBottlesAction();
        crushBottlesAction.execute(this);
        shardsCollector.collectShards();
        System.out.println("Total shards collected: " + shardsCollector.getTotalShards());
    }

    public void addFriend(Human friend) {
        friendList.add(friend);
        System.out.println(this.getName() + " and " + friend.getName() + " are friends now!");
    }

    public void removeFriend(Human notFriend) {
        friendList.remove(notFriend);
        System.out.println(this.getName() + " and " + notFriend.getName() + " are no longer friends!");
    }

    public String getFriends() {
        return this.getName() + " has friends " +
                friendList.stream().
                        map(Human::getName).
                        collect(Collectors.joining(", "));
    }

    public AbstractJob getJob() {
        return job;
    }

    public void setJob(AbstractJob job) {
        this.job = job;
    }

    public String getAnimal() {
        if (animal != null) {
            return name + " has a pet named " + animal.getAnimalName();
        } else {
            return name + " has no pet!";
        }
    }

    public void setAnimal(AbstractAnimal animal) {
        this.animal = animal;
    }

    public String getClothes() {
        return this.getName() + " is wearing " +
                clothes.stream().
                        map(Clothes::getClothesDescription).
                        collect(Collectors.joining(", "));
    }

    ///TODO:LOCAL CLASS HERE
    public void performActionLocal() {
    }

    public void performAction(ActionInterface action) {

        try {
            action.execute(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addThing(AbstractThing thing) {
        inventoryManager.addThing(thing);
    }

    public void removeFromInventory(AbstractThing thing) {
        inventoryManager.removeFromInventory(thing);
    }

    public AbstractThing getItemFromInventory(Class<? extends AbstractThing> itemClass) throws ItemNotFoundException {
        AbstractThing returnable = inventoryManager.getItemFromInventory(itemClass);
        if (returnable == null) {
            throw new ItemNotFoundException(itemClass.getSimpleName());
        }
        return returnable;
    }

    public void getInventory() {
        inventoryManager.getInventory();
    }

    public ArrayList<AbstractThing> getInventoryArray() {
        return inventoryManager.getInventoryArray();
    }
}
