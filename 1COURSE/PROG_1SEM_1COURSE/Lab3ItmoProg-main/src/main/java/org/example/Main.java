package org.example;

import org.example.Actions.*;
import org.example.Human.Human;
import org.example.Locations.Location;
import org.example.Things.Bottle;

import org.example.Things.Telescope;


public class Main {
    public static void main(String[] args) {
        Location home = new Location("Home", "Based home");
        Location observatory = new Location("Observatory", "A place to observe the sky");

        Human steklyash = new Human("Steklyash", 35,new Location("StartLocation","just"));
        System.out.println();
        Bottle bottle = new Bottle("Bottle", "A glass bottle", 10);
        steklyash.addThing(bottle);

        steklyash.setCurrentLocation(home);
        // Print inventory before crushing bottles
        System.out.println();
        System.out.println("Inventory before crushing bottles:");
        steklyash.getInventory();
        System.out.println();
        System.out.println();

        System.out.println("Current location is " + steklyash.getCurrentLocation().getLocationName());

        // Crush bottles into glass shards
        CrushBottlesAction crushBottlesAction = new CrushBottlesAction();
        crushBottlesAction.executeAction(steklyash);



        // Create telescope
        CreateTelescopeAction createTelescopeAction = new CreateTelescopeAction("Celestron AstroFi 130");
         createTelescopeAction.executeAction(steklyash);


        // Print inventory after crushing bottles and creating a telescope
        System.out.println("\nInventory after crushing bottles and creating a telescope:");
        steklyash.getInventory();
        System.out.println();
        System.out.println();

        steklyash.setCurrentLocation(observatory);


        // 3. Watch the moon using the telescope
        Telescope telescopeFromInventory = (Telescope) steklyash.getItemFromInventory(Telescope.class);
        if (telescopeFromInventory != null) {
            CelestialBody body = CelestialBody.getRandomCelestialBody();

            WatchBodyAction watchBodyAction = new WatchBodyAction(telescopeFromInventory, body);
            watchBodyAction.executeAction(steklyash);
        } else {
            System.out.println("No telescope found in Steklyash's inventory");
        }
    }
}
///TODO:put description inside of the classes