/**
 * @author      masjohncook
 * @version     0.0.2
 * @copyright   (C) Copyright 2026
 * @license     None
 * @maintainer  masjohncook
 * @email       mas.john.cook@gmail.com
 * @status      None
 */
package LibraryBorrowingSystem;

/**
 * Parent class representing any person in the library system.
 *
 * This class is the BASE CLASS (superclass) for both Member and Librarian.
 * It holds the shared attributes that every person has: an ID and a name.
 * By putting these here, we avoid writing the same code twice in both classes.
 *
 * INHERITANCE: Member extends Person, Librarian extends Person
 *
 * Attributes:
 *   - id   : unique identifier (protected so subclasses can access it directly)
 *   - name : full name of the person
 */
public class Person {

    // protected means only this class AND its subclasses (Member, Librarian)
    // can access these fields directly — other classes still cannot
    protected String id;
    protected String name;

    /**
     * Creates a new Person with the given ID and name.
     *
     * @param id   unique identifier for this person
     * @param name full name of this person
     */
    public Person(String id, String name) {
        this.id   = id;
        this.name = name;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    /** Returns the unique ID of this person. */
    public String getId() {
        return id;
    }

    /** Returns the name of this person. */
    public String getName() {
        return name;
    }

    // ── Setters ───────────────────────────────────────────────────────────────

    /** Updates the ID of this person. */
    public void setId(String id) {
        this.id = id;
    }

    /** Updates the name of this person. */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a basic description of this person.
     * OVERRIDING: Subclasses (Member, Librarian) will override this
     * to return their own specific description instead.
     */
    public String getInfo() {
        return "Person[" + id + "] " + name;
    }

    /**
     * Returns a readable summary when printed.
     * Calls getInfo() so subclasses that override getInfo() will
     * automatically get their own version here too.
     */
    public String toString() {
        return getInfo();
    }
}
