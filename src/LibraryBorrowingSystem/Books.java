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
 * Represents a book in the library collection.
 *
 * INHERITANCE: Books extends LibraryItem
 *   - itemId, title, available are INHERITED from LibraryItem (not re-declared here)
 *   - Books adds its own fields: author and genre
 *
 * OVERRIDING: getInfo() overrides LibraryItem.getInfo()
 *   - Returns book-specific details (author, genre) instead of the generic version
 *
 * Attributes (own):
 *   - author : the name of the book's author
 *   - genre  : the genre/category of the book
 */
public class Books extends LibraryItem {

    // author and genre are fields specific to Books
    // itemId, title, available come from LibraryItem (inherited)
    private String author;
    private String genre;

    /**
     * Creates a new book with the given details.
     * Calls the parent (LibraryItem) constructor using super()
     * to set itemId and title.
     *
     * @param itemId unique book identifier (e.g. "B001")
     * @param title  title of the book
     * @param author author of the book
     */
    public Books(String itemId, String title, String author) {
        // super() calls LibraryItem's constructor to set itemId, title, available
        super(itemId, title);

        // Set the book-specific fields
        this.author = author;
        this.genre  = "General"; // default genre if not specified
    }

    /**
     * Returns the pre-defined initial book data for the library.
     * Keeps starting data inside the Books class so each class
     * is responsible for its own default data.
     *
     * @return array of Books objects pre-filled with default catalog entries
     */
    public static Books[] getInitialBooks() {
        // Create an array that can hold 5 Books objects
        Books[] initial = new Books[5];

        // Fill each slot with a pre-defined book
        initial[0] = new Books("B001", "The Great Gatsby",       "F. Scott Fitzgerald");
        initial[1] = new Books("B002", "To Kill a Mockingbird",  "Harper Lee");
        initial[2] = new Books("B003", "1984",                   "George Orwell");
        initial[3] = new Books("B004", "Brave New World",        "Aldous Huxley");
        initial[4] = new Books("B005", "The Catcher in the Rye", "J.D. Salinger");

        return initial;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    /** Returns the author of this book. */
    public String getAuthor() {
        return author;
    }

    /** Returns the genre of this book. */
    public String getGenre() {
        return genre;
    }

    // ── Setters ───────────────────────────────────────────────────────────────

    /** Updates the author of this book. */
    public void setAuthor(String author) {
        this.author = author;
    }

    /** Updates the genre of this book. */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    // NOTE: setTitle() and setAvailable() are INHERITED from LibraryItem
    // No need to rewrite them here

    /**
     * OVERRIDING LibraryItem.getInfo()
     * Returns a book-specific description including author and genre.
     * Example: [B001] "The Great Gatsby" by F. Scott Fitzgerald | Genre: General (Available)
     */
    @Override
    public String getInfo() {
        String status = available ? "Available" : "Borrowed";
        return "[" + itemId + "] \"" + title + "\""
             + " by " + author
             + " | Genre: " + genre
             + " (" + status + ")";
    }

    /**
     * Returns a readable summary when printed.
     * Calls the overridden getInfo() above.
     */
    public String toString() {
        return getInfo();
    }
}
