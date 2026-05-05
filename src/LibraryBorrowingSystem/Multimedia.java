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
 * Represents a multimedia item (DVD, CD, etc.) in the library collection.
 *
 * INHERITANCE: Multimedia extends LibraryItem
 *   - itemId, title, available are INHERITED from LibraryItem (not re-declared here)
 *   - Multimedia adds its own fields: type and duration
 *
 * OVERRIDING: getInfo() overrides LibraryItem.getInfo()
 *   - Returns multimedia-specific details (type, duration) instead of the generic version
 *
 * Attributes (own):
 *   - type     : the type of multimedia (e.g. "DVD", "CD", "Blu-Ray")
 *   - duration : the length/duration of the item (e.g. "2 hours")
 */
public class Multimedia extends LibraryItem {

    // type and duration are fields specific to Multimedia
    // itemId, title, available come from LibraryItem (inherited)
    private String type;
    private String duration;

    /**
     * Creates a new Multimedia item with the given details.
     * Calls the parent (LibraryItem) constructor using super()
     * to set itemId and title.
     *
     * @param itemId   unique identifier (e.g. "MM01")
     * @param title    title of the multimedia item
     * @param type     type of multimedia (e.g. "DVD", "CD")
     * @param duration length of the item (e.g. "2 hours")
     */
    public Multimedia(String itemId, String title, String type, String duration) {
        // super() calls LibraryItem's constructor to set itemId, title, available
        super(itemId, title);

        // Set the multimedia-specific fields
        this.type     = type;
        this.duration = duration;
    }

    /**
     * Returns the pre-defined initial multimedia data for the library.
     *
     * @return array of Multimedia objects pre-filled with default entries
     */
    public static Multimedia[] getInitialMultimedia() {
        Multimedia[] initial = new Multimedia[2];

        initial[0] = new Multimedia("MM01", "Java Programming Tutorial", "DVD",  "3 hours");
        initial[1] = new Multimedia("MM02", "Classical Music Collection", "CD",   "60 minutes");

        return initial;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    /** Returns the type of this multimedia item. */
    public String getType() {
        return type;
    }

    /** Returns the duration of this multimedia item. */
    public String getDuration() {
        return duration;
    }

    // ── Setters ───────────────────────────────────────────────────────────────

    /** Updates the type. */
    public void setType(String type) {
        this.type = type;
    }

    /** Updates the duration. */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * OVERRIDING LibraryItem.getInfo()
     * Returns a multimedia-specific description including type and duration.
     * Example: [MM01] "Java Programming Tutorial" | Type: DVD | Duration: 3 hours (Available)
     */
    @Override
    public String getInfo() {
        String status = available ? "Available" : "Borrowed";
        return "[" + itemId + "] \"" + title + "\""
             + " | Type: "     + type
             + " | Duration: " + duration
             + " (" + status + ")";
    }

    /**
     * Returns a readable summary when printed.
     */
    public String toString() {
        return getInfo();
    }
}
