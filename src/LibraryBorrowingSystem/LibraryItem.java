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
 * Parent class representing any item in the library collection.
 *
 * This class is the BASE CLASS (superclass) for both Books and Multimedia.
 * It holds the shared attributes that every library item has: an ID, a title,
 * and an availability status. By putting these here, we avoid writing the
 * same code twice in both Books and Multimedia.
 *
 * INHERITANCE: Books extends LibraryItem, Multimedia extends LibraryItem
 *
 * Attributes:
 *   - itemId    : unique identifier for the item (e.g. "B001", "MM01")
 *   - title     : title of the item
 *   - available : true if available to borrow, false if currently borrowed
 */
public class LibraryItem {

    // protected means only this class AND its subclasses (Books, Multimedia)
    // can access these fields directly
    protected String  itemId;
    protected String  title;
    protected boolean available;

    /**
     * Creates a new LibraryItem with the given ID and title.
     * All items start as available when first added.
     *
     * @param itemId unique identifier for this item
     * @param title  title of this item
     */
    public LibraryItem(String itemId, String title) {
        this.itemId     = itemId;
        this.title      = title;
        this.available  = true; // new items are always available
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    /** Returns the unique item ID. */
    public String getItemId() {
        return itemId;
    }

    /** Returns the title of this item. */
    public String getTitle() {
        return title;
    }

    /** Returns true if this item is currently available. */
    public boolean isAvailable() {
        return available;
    }

    // ── Setters ───────────────────────────────────────────────────────────────

    /** Updates the item ID. */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /** Updates the title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Sets the availability (true = on shelf, false = borrowed). */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Returns a basic description of this item.
     * OVERRIDING: Subclasses (Books, Multimedia) will override this
     * to return their own specific description instead.
     */
    public String getInfo() {
        String status = available ? "Available" : "Borrowed";
        return "[" + itemId + "] " + title + " (" + status + ")";
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
