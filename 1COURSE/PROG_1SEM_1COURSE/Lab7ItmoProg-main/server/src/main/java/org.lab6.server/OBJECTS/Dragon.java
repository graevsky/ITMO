package org.lab6.server.OBJECTS;



import org.lab6.server.ENUM.DragonCharacter;
import org.lab6.server.ENUM.DragonColor;
import org.lab6.server.ENUM.DragonType;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This j.ava file provides constructor of Dragon
 */
public class Dragon implements Comparable<Dragon> {

    private static final AtomicLong counter = new AtomicLong();
    private String name;
    private Coordinates coordinates;
    private Long age;
    private DragonColor color;
    private DragonType type;
    private DragonCharacter character;
    private long id;
    private LocalDateTime creationDate;
    private DragonHead head;

    /**
     * This is the dragon constructor
     *
     * @param name        name of dragon
     * @param coordinates coordinates of dragon
     * @param age         age of dragon
     * @param color       color of dragon
     * @param type        type of dragon
     * @param character   character of dragon
     * @param head        size of head of dragon
     */

    public Dragon(String name, Coordinates coordinates, Long age, DragonColor color, DragonType type, DragonCharacter character, DragonHead head) {

        id = counter.incrementAndGet();

        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.head = head;
        this.creationDate = LocalDateTime.now();
    }

    /**
     * returns id of dragon
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Id setter
     *
     * @param id sets dragon id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Name getter
     *
     * @return name returns name of dragon
     */
    public String getName() {
        return name;
    }

    /**
     * Coordinates getter
     *
     * @return coordinates returns coordinates of dragon
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Creation date getter
     *
     * @return createionDate returns date of creation of dragon
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Creation date setter
     *
     * @param creationDate generates creation date of dragon
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Age getter
     *
     * @return age returns age of dragon
     */
    public Long getAge() {
        return age;
    }

    /**
     * Color getter
     *
     * @return color returns color of dragon
     */
    public DragonColor getColor() {
        return color;
    }

    /**
     * Type getter
     *
     * @return type returns type of dragon
     */
    public DragonType getType() {
        return type;
    }

    /**
     * Character getter
     *
     * @return charater  returns charater of dragon
     */
    public DragonCharacter getCharacter() {
        return character;
    }

    /**
     * Head getter
     *
     * @return head returns size of head of dragon
     */
    public DragonHead getHead() {
        return head;
    }

    /**
     * Head size setter
     *
     * @param head set head of dragon
     */

    public void setHead(DragonHead head) {
        this.head = head;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }
    public void setAge(Long age){
        this.age =  age;
    }

    public void setColor(DragonColor color){
        this.color = color;
    }
    public void setType(DragonType type){
        this.type =type;
    }
    public void setCharacter(DragonCharacter character){
        this.character = character;
    }



    /**
     * Head suze comparator
     *
     * @param other the object to be compared.
     * @return float between size of dragons
     */
    @Override
    public int compareTo(Dragon other) {
        return -Float.compare(this.getHead().getSize(), other.getHead().getSize());
    }

}
