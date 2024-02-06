package org.example;

import org.example.Actions.*;
import org.example.Animals.AbstractAnimal;
import org.example.Animals.Dog;
import org.example.Animals.Juke;
import org.example.Buildings.AbstractBuilding;
import org.example.Buildings.Building;
import org.example.Clothes.Clothes;
import org.example.Enums.CelestialBody;
import org.example.Enums.ClothesTypesEnum;
import org.example.Exceptions.ItemNotFoundException;
import org.example.Human.Human;
import org.example.Jobs.AbstractJob;
import org.example.Jobs.Job;
import org.example.Locations.Location;
import org.example.Locations.Streets.AbstractStreet;
import org.example.Locations.Streets.Street;
import org.example.Things.*;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        Location city = new Location("City", "Main city");
        Location field = new Location("Field", "Just a field");
        Location observatory = new Location("Observatory", "-");
        AbstractStreet mainStreet = new Street("Ulitsa Kolokolchikov", city);

        AbstractBuilding mainHouse = new Building("dom 19", mainStreet);

        AbstractStreet secondStreet = new Street("Ulisa Margaritok", city);
        AbstractBuilding secondHouse = new Building("dom 18", secondStreet);

        //ArrayList<Human> humanList = new ArrayList<>();

        //common clothes
        Clothes defaultShoes = new Clothes(ClothesTypesEnum.SHOES, "Aboba", "Black");
        Clothes defaultJeans = new Clothes(ClothesTypesEnum.LEGS, "Aboba", "Blue");
        Clothes defaultHat = new Clothes(ClothesTypesEnum.HEAD, "Aboba", "Red");
        Clothes defaultJacket = new Clothes(ClothesTypesEnum.BODY, "Aboba", "Blue");

        ArrayList<Clothes> defaultClothes = new ArrayList<>();
        defaultClothes.add(defaultJeans);
        defaultClothes.add(defaultHat);
        defaultClothes.add(defaultJacket);
        defaultClothes.add(defaultShoes);

        //Znaika
        AbstractJob beingSmart = new Job("Smart AF", "Knows everything");
        ArrayList<Clothes> znaikaClothes = new ArrayList<>();

        Clothes znaikaJeans = new Clothes(ClothesTypesEnum.LEGS, "BLACKED", "Black");
        Clothes znaikaJacket = new Clothes(ClothesTypesEnum.BODY, "BLACKED", "Black");

        znaikaClothes.add(defaultHat);
        znaikaClothes.add(defaultShoes);
        znaikaClothes.add(znaikaJeans);
        znaikaClothes.add(znaikaJacket);

        Human znaika = new Human("Znaika", 228, city, znaikaClothes, null, mainHouse, beingSmart);

        AbstractThing book = new Book("LEV HUDOI.VOINA I VOINA", 69, "PEACE OF PEACE", new Size(13, 13, 13));

        znaika.addThing(book);
        ReadBookAction readBookAction = new ReadBookAction();

        znaika.performAction(readBookAction);

        //humanList.add(znaika);

        //PILULKIN
        AbstractJob healPPL = new Job("+10HP", "Heal people");
        ArrayList<Clothes> pilulkinClothes = new ArrayList<>();

        Clothes pilulkinJacket = new Clothes(ClothesTypesEnum.BODY, "ABOBUS", "White");
        Clothes pilulkinHat = new Clothes(ClothesTypesEnum.HEAD, "OBAMA", "White");

        pilulkinClothes.add(pilulkinJacket);
        pilulkinClothes.add(pilulkinHat);
        pilulkinClothes.add(defaultShoes);
        pilulkinClothes.add(defaultJeans);

        Human pilulkin = new Human("Pilulkin", 288, city, pilulkinClothes, null, mainHouse, healPPL);


        //Vintik
        AbstractJob mech = new Job("Fix traxxxxtor", "Fix tractors");


        Human vintik = new Human("Vintik", 11, city, defaultClothes, null, mainHouse, mech);

        Human shpuntik = new Human("Shpuntik", 11, city, defaultClothes, null, mainHouse, mech);

        vintik.addFriend(shpuntik);

        shpuntik.addFriend(vintik);


        //Siropchik

        AbstractJob drinkSyrop = new Job("Alcoholic", "Drinks sugar syrop");
        Human siropchik = new Human("Siropchik", 123, city, defaultClothes, null, mainHouse, drinkSyrop);


        //Pulka
        AbstractJob killNothing = new Job("Kill nothing", "Kill air");
        AbstractAnimal dogPulka = new Dog("Bulka");

        AbstractThing rifle = new Rifle("Benelli M4", 1, "Gang bang", new Size(50, 600, 40));

        Human pulka = new Human("Pulka", 50, city, defaultClothes, dogPulka, mainHouse, killNothing);

        pulka.addThing(rifle);

        ShootRifleAction fireRifle = new ShootRifleAction();

        pulka.performAction(fireRifle);

        //humanList.add(pulka);

        //Tubik
        AbstractJob paint = new Job("Painter", "Paints paints");

        Human tubik = new Human("Tubik", 99, city, defaultClothes, null, mainHouse, paint);


        //Guslya
        AbstractJob playMusic = new Job("Musician", "Plays music");

        Human guslya = new Human("Guslya", 19, city, defaultClothes, null, mainHouse, playMusic);


        //Toropizhka
        AbstractJob sprinter = new Job("Sprinter", "Like Splinter");

        Human toropizhka = new Human("Toropizhka", 28, city, defaultClothes, null, mainHouse, sprinter);


        //Vorchun
        AbstractJob vorchaD = new Job("VorchaD", "-");

        Human vorchun = new Human("Vorchun", 113, city, defaultClothes, null, mainHouse, vorchaD);


        //Molchun
        AbstractJob silentio = new Job("Silencer", "He s silent, like PBS-3");

        Human molchun = new Human("Molchun", 56, city, defaultClothes, null, mainHouse, silentio);


        //Ponchik
        AbstractJob sugarDaddy = new Job("Sugar destroyer", "He eats sugar");

        Human ponchik = new Human("Ponchik", 23, city, defaultClothes, null, mainHouse, sugarDaddy);


        //Rasteryaika
        AbstractJob looseThings = new Job("Looser", "He lost everything");

        Human rasteryaika = new Human("Rasteryaika", 23, city, defaultClothes, null, mainHouse, looseThings);


        //Steklyash
        AbstractJob astronomy = new Job("Astronomy", "-");
        Human steklyash = new Human("Steklyash", 35, city, defaultClothes, null, mainHouse, astronomy);

        System.out.println();
        System.out.println();
        AbstractThing bottle = new Bottle("Cokey Cola", "NOT Dr. Pebba", 40, new Size(10, 10, 45));
        steklyash.addThing(bottle);
        /*steklyash.addThing(bottle);
        steklyash.addThing(bottle);
        steklyash.addThing(bottle);
        steklyash.addThing(bottle);
        steklyash.addThing(bottle);
        steklyash.addThing(bottle);
        steklyash.addThing(bottle);
        steklyash.addThing(bottle);*/



        CrushBottlesAction crushBottlesAction = new CrushBottlesAction();
        steklyash.performAction(crushBottlesAction);
        System.out.println();
        System.out.println();
        steklyash.crushBottlesAndCollectShards();
        System.out.println();
        System.out.println();
        WatchCloserAction watchCloserAction = new WatchCloserAction();
        steklyash.performAction(watchCloserAction);
        System.out.println();
        System.out.println();
        CreateTelescopeAction createTelescopeAction = new CreateTelescopeAction();
        steklyash.performAction(createTelescopeAction);
        steklyash.setCurrentLocation(observatory);
        Telescope telescopeFromInventory;
        try {
            telescopeFromInventory = (Telescope) steklyash.getItemFromInventory(Telescope.class);
            CelestialBody body = CelestialBody.getRandomCelestialBody();
            WatchBodyAction watchMoonAction = new WatchBodyAction(telescopeFromInventory, body);
            steklyash.performAction(watchMoonAction);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println();
        ///TODO:ANON CLASS
        ActionInterface anonAction = new ActionInterface() {
            @Override
            public String getName() {
                return "Anon action";
            }

            @Override
            public void execute(Human human) {
                System.out.println("Anon action for " + human.getName());
            }

            @Override
            public String getDescription() {
                return "Anon action";
            }
        };
        anonAction.execute(steklyash);
        System.out.println();
        System.out.println();

        ///TODO:STATINC INC CLASS
        AbstractThing.Coords coordinate = bottle.getCoords();
        int x = coordinate.getX();
        int y = coordinate.getY();
        int z = coordinate.getZ();
        System.out.println("Bottle \"COORDS\". X: " + x + ", Y: " + y + ", Z: " + z);

        System.out.println();


        //Neznaika
        AbstractJob knowsNothing = new Job("Stoopeth", "He is stoopeth AF");

        ArrayList<Clothes> neznaikaClothes = new ArrayList<>();
        Clothes neznaikaHat = new Clothes(ClothesTypesEnum.HEAD, "By Slyapnik", "Blue");
        Clothes neznaikaJeans = new Clothes(ClothesTypesEnum.LEGS, "By Slyapnik", "Yellow");
        Clothes neznaikaJacket = new Clothes(ClothesTypesEnum.BODY, "By Slyapnik", "Orange");

        neznaikaClothes.add(neznaikaHat);
        neznaikaClothes.add(neznaikaJacket);
        neznaikaClothes.add(neznaikaJeans);
        neznaikaClothes.add(defaultShoes);

        Human neznaika = new Human("Neznaika", 666, city, neznaikaClothes, null, mainHouse, knowsNothing);

        AbstractAction walkAround = new WalkAroundAction(city, field);
        neznaika.performAction(walkAround);

        //humanList.add(neznaika);


        //Gunk
        Human gunk = new Human("Gunka", 333, city, defaultClothes, null, secondHouse, null);

        neznaika.addFriend(gunk);

        gunk.addFriend(neznaika);

        AbstractAction scandal = new ScandalAction(gunk);

        neznaika.performAction(scandal);

        //humanList.add(gunk);


        //MAIN STORY

        System.out.println();
        System.out.println();
        System.out.println();

        neznaika.setCurrentLocation(field);

        Juke juk = new Juke("ABOBIUM");

        juk.attackEntity(neznaika);

        WatchBodyAction watchSunNezna = new WatchBodyAction(CelestialBody.SUN);

        neznaika.performAction(watchSunNezna);
        System.out.println("\"WTF\"- said Neznayika");
        neznaika.setCurrentLocation(observatory);

        neznaika.setCurrentLocation(city);


        RunAction run = new RunAction();
        neznaika.performAction(run);
        WatchSunCloserAction watchSunCloserAction = new WatchSunCloserAction(steklyash.getHumanArrayList());
        System.out.println();
        System.out.println();
        neznaika.performAction(watchSunCloserAction);
        System.out.println();
        System.out.println();
        PanicAction panicAction = new PanicAction(steklyash.getHumanArrayList());

        neznaika.performAction(panicAction);

        MassMoveAction massMoveAction = new MassMoveAction(steklyash.getHumanArrayList(), observatory);

        neznaika.performAction(massMoveAction);

        System.out.println();
        System.out.println();

        MassLaught massLaught = new MassLaught(steklyash.getHumanArrayList());

        neznaika.performAction(massLaught);


    }
}


///TODO:ARRAYLISt людей сделать отдельно, чтобы люди добавлялись в него автоматически при создании, т е создание человека возвращало человека, но добавляло его в аррейлист
///TODO:нормальные имена работ