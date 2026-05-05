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
 * Represents the librarian who manages the library system.
 *
 * INHERITANCE: Librarian extends Person
 *   - id and name are INHERITED from Person (not re-declared here)
 *   - Librarian adds its own fields: catalog, multimedia, members, borrowRecords
 *
 * OVERLOADING: addBook() and addMultimedia() have multiple versions
 *   - addBook(id, title, author)         — without genre
 *   - addBook(id, title, author, genre)  — with genre
 *   - addMultimedia(id, title, type)          — without duration
 *   - addMultimedia(id, title, type, duration) — with duration
 *
 * OVERRIDING: getInfo() overrides Person.getInfo()
 *   - Returns librarian-specific details (catalog size, member count)
 *
 * Associations:
 *   - catalog[]      : array of Books in the library
 *   - multimedia[]   : array of Multimedia in the library
 *   - members[]      : array of all registered Members
 *   - borrowRecords[]: array of all BorrowRecord transactions
 */
public class Librarian extends Person {

    // catalog holds all Books objects in the library
    private Books[]        catalog;
    private int            catalogCount;

    // multimedia holds all Multimedia objects in the library
    private Multimedia[]   multimedia;
    private int            multimediaCount;

    // members holds all registered Member objects
    private Member[]       members;
    private int            memberCount;

    // borrowRecords holds all borrow/return transactions
    private BorrowRecord[] borrowRecords;
    private int            recordCount;

    // Maximum capacity constants
    private static final int MAX_BOOKS      = 100;
    private static final int MAX_MULTIMEDIA = 50;
    private static final int MAX_MEMBERS    = 50;
    private static final int MAX_RECORDS    = 200;

    /**
     * Creates a new Librarian and automatically loads initial data.
     * Calls the parent (Person) constructor using super() to set id and name.
     *
     * @param id   unique identifier for the librarian (e.g. "L001")
     * @param name full name of the librarian
     */
    public Librarian(String id, String name) {
        // super() calls Person's constructor to set id and name
        super(id, name);

        // Initialize all arrays
        this.catalog       = new Books[MAX_BOOKS];
        this.catalogCount  = 0;

        this.multimedia      = new Multimedia[MAX_MULTIMEDIA];
        this.multimediaCount = 0;

        this.members     = new Member[MAX_MEMBERS];
        this.memberCount = 0;

        this.borrowRecords = new BorrowRecord[MAX_RECORDS];
        this.recordCount   = 0;

        // Load starting data from each class
        loadInitialData();
    }

    /**
     * Loads starting data by calling getInitialBooks(), getInitialMultimedia(),
     * and getInitialMembers() from their respective classes.
     */
    private void loadInitialData() {
        // Load initial books
        Books[] initialBooks = Books.getInitialBooks();
        for (int i = 0; i < initialBooks.length; i++) {
            catalog[catalogCount++] = initialBooks[i];
        }

        // Load initial multimedia
        Multimedia[] initialMedia = Multimedia.getInitialMultimedia();
        for (int i = 0; i < initialMedia.length; i++) {
            multimedia[multimediaCount++] = initialMedia[i];
        }

        // Load initial members
        Member[] initialMembers = Member.getInitialMembers();
        for (int i = 0; i < initialMembers.length; i++) {
            members[memberCount++] = initialMembers[i];
        }
    }

    // ── Book Management ───────────────────────────────────────────────────────

    /**
     * OVERLOADING — Adds a new book WITHOUT genre.
     * Genre will default to "General" inside the Books constructor.
     *
     * @param itemId unique ID for the new book
     * @param title  title of the new book
     * @param author author of the new book
     * @return the created Books object, or null if the operation failed
     */
    public Books addBook(String itemId, String title, String author) {
        if (catalogCount >= MAX_BOOKS) {
            System.out.println("  [FAILED] Catalog is full.");
            return null;
        }
        if (findBookById(itemId) != null) {
            System.out.println("  [FAILED] Book ID \"" + itemId + "\" already exists.");
            return null;
        }

        // Create a new Books object — genre defaults to "General"
        Books book = new Books(itemId, title, author);
        catalog[catalogCount++] = book;
        System.out.println("  [ADDED] " + book.getInfo());
        return book;
    }

    /**
     * OVERLOADING — Adds a new book WITH genre.
     * Same method name as above but accepts one extra parameter (genre).
     *
     * @param itemId unique ID for the new book
     * @param title  title of the new book
     * @param author author of the new book
     * @param genre  genre/category of the book
     * @return the created Books object, or null if the operation failed
     */
    public Books addBook(String itemId, String title, String author, String genre) {
        if (catalogCount >= MAX_BOOKS) {
            System.out.println("  [FAILED] Catalog is full.");
            return null;
        }
        if (findBookById(itemId) != null) {
            System.out.println("  [FAILED] Book ID \"" + itemId + "\" already exists.");
            return null;
        }

        // Create a Books object with the provided genre
        Books book = new Books(itemId, title, author);
        book.setGenre(genre); // set genre using the setter
        catalog[catalogCount++] = book;
        System.out.println("  [ADDED] " + book.getInfo());
        return book;
    }

    /**
     * Removes a book from the catalog by its ID.
     * Fails if the book is not found or is currently borrowed.
     *
     * @param itemId the ID of the book to remove
     * @return true if removed successfully, false otherwise
     */
    public boolean removeBook(String itemId) {
        for (int i = 0; i < catalogCount; i++) {
            if (catalog[i].getItemId().equals(itemId)) {

                if (!catalog[i].isAvailable()) {
                    System.out.println("  [FAILED] Cannot remove a book that is currently borrowed.");
                    return false;
                }

                // Shift remaining entries left to fill the gap
                for (int j = i; j < catalogCount - 1; j++) {
                    catalog[j] = catalog[j + 1];
                }
                catalog[--catalogCount] = null;

                System.out.println("  [REMOVED] Book ID \"" + itemId + "\" removed.");
                return true;
            }
        }
        System.out.println("  [FAILED] Book ID \"" + itemId + "\" not found.");
        return false;
    }

    /**
     * Updates the title and/or author of an existing book.
     * Fields left blank are not changed.
     *
     * @param itemId    the ID of the book to update
     * @param newTitle  new title (leave empty to keep current)
     * @param newAuthor new author (leave empty to keep current)
     * @return true if updated successfully, false otherwise
     */
    public boolean updateBook(String itemId, String newTitle, String newAuthor) {
        Books book = findBookById(itemId);

        if (book == null) {
            System.out.println("  [FAILED] Book ID \"" + itemId + "\" not found.");
            return false;
        }

        if (!newTitle.trim().isEmpty())  book.setTitle(newTitle.trim());
        if (!newAuthor.trim().isEmpty()) book.setAuthor(newAuthor.trim());

        System.out.println("  [UPDATED] " + book.getInfo());
        return true;
    }

    /**
     * Searches the catalog for a book by its ID.
     *
     * @param itemId the book ID to look for
     * @return the matching Books object, or null if not found
     */
    public Books findBookById(String itemId) {
        for (int i = 0; i < catalogCount; i++) {
            if (catalog[i].getItemId().equals(itemId)) {
                return catalog[i];
            }
        }
        return null;
    }

    // ── Multimedia Management ─────────────────────────────────────────────────

    /**
     * OVERLOADING — Adds a new multimedia item WITHOUT duration.
     *
     * @param itemId unique ID (e.g. "MM03")
     * @param title  title of the multimedia item
     * @param type   type (e.g. "DVD", "CD")
     * @return the created Multimedia object, or null if failed
     */
    public Multimedia addMultimedia(String itemId, String title, String type) {
        if (multimediaCount >= MAX_MULTIMEDIA) {
            System.out.println("  [FAILED] Multimedia catalog is full.");
            return null;
        }
        if (findMultimediaById(itemId) != null) {
            System.out.println("  [FAILED] Multimedia ID \"" + itemId + "\" already exists.");
            return null;
        }

        // Duration defaults to "N/A" when not provided
        Multimedia item = new Multimedia(itemId, title, type, "N/A");
        multimedia[multimediaCount++] = item;
        System.out.println("  [ADDED] " + item.getInfo());
        return item;
    }

    /**
     * OVERLOADING — Adds a new multimedia item WITH duration.
     * Same method name but accepts one extra parameter (duration).
     *
     * @param itemId   unique ID (e.g. "MM03")
     * @param title    title of the multimedia item
     * @param type     type (e.g. "DVD", "CD")
     * @param duration length (e.g. "2 hours")
     * @return the created Multimedia object, or null if failed
     */
    public Multimedia addMultimedia(String itemId, String title, String type, String duration) {
        if (multimediaCount >= MAX_MULTIMEDIA) {
            System.out.println("  [FAILED] Multimedia catalog is full.");
            return null;
        }
        if (findMultimediaById(itemId) != null) {
            System.out.println("  [FAILED] Multimedia ID \"" + itemId + "\" already exists.");
            return null;
        }

        Multimedia item = new Multimedia(itemId, title, type, duration);
        multimedia[multimediaCount++] = item;
        System.out.println("  [ADDED] " + item.getInfo());
        return item;
    }

    /**
     * Searches the multimedia array for an item by its ID.
     *
     * @param itemId the multimedia ID to look for
     * @return the matching Multimedia object, or null if not found
     */
    public Multimedia findMultimediaById(String itemId) {
        for (int i = 0; i < multimediaCount; i++) {
            if (multimedia[i].getItemId().equals(itemId)) {
                return multimedia[i];
            }
        }
        return null;
    }

    /**
     * Returns a combined array of all LibraryItems (books + multimedia).
     * Used by Member.searchItem() to search across everything.
     *
     * @return array of all LibraryItem objects
     */
    public LibraryItem[] getAllItems() {
        LibraryItem[] all = new LibraryItem[catalogCount + multimediaCount];
        int idx = 0;
        for (int i = 0; i < catalogCount; i++)      all[idx++] = catalog[i];
        for (int i = 0; i < multimediaCount; i++)   all[idx++] = multimedia[i];
        return all;
    }

    /**
     * Returns the total number of items (books + multimedia).
     */
    public int getAllItemsCount() {
        return catalogCount + multimediaCount;
    }

    // ── Member Management ─────────────────────────────────────────────────────

    /**
     * Registers a new member into the system.
     *
     * @param memberId unique ID for the new member
     * @param name     full name of the new member
     * @return the created Member object, or null if failed
     */
    public Member registerMember(String memberId, String name) {
        if (memberCount >= MAX_MEMBERS) {
            System.out.println("  [FAILED] Member limit reached.");
            return null;
        }
        if (findMemberById(memberId) != null) {
            System.out.println("  [FAILED] Member ID \"" + memberId + "\" already exists.");
            return null;
        }

        Member member = new Member(memberId, name);
        members[memberCount++] = member;
        System.out.println("  [REGISTERED] " + member.getInfo());
        return member;
    }

    /**
     * Removes a member from the system by their ID.
     * Fails if the member still has borrowed items.
     *
     * @param memberId the ID of the member to remove
     * @return true if removed successfully, false otherwise
     */
    public boolean removeMember(String memberId) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberId().equals(memberId)) {

                if (members[i].getBorrowCount() > 0) {
                    System.out.println("  [FAILED] Cannot remove a member who still has borrowed items.");
                    return false;
                }

                // Shift remaining entries left
                for (int j = i; j < memberCount - 1; j++) {
                    members[j] = members[j + 1];
                }
                members[--memberCount] = null;

                System.out.println("  [REMOVED] Member ID \"" + memberId + "\" removed.");
                return true;
            }
        }
        System.out.println("  [FAILED] Member ID \"" + memberId + "\" not found.");
        return false;
    }

    /**
     * Updates the name of an existing member.
     *
     * @param memberId the ID of the member to update
     * @param newName  the new name to assign
     * @return true if updated successfully, false otherwise
     */
    public boolean updateMember(String memberId, String newName) {
        Member member = findMemberById(memberId);

        if (member == null) {
            System.out.println("  [FAILED] Member ID \"" + memberId + "\" not found.");
            return false;
        }

        if (!newName.trim().isEmpty()) {
            member.setName(newName.trim()); // setName() is inherited from Person
            System.out.println("  [UPDATED] " + member.getInfo());
            return true;
        }

        System.out.println("  [FAILED] New name cannot be empty.");
        return false;
    }

    /**
     * Searches the members array for a member by their ID.
     *
     * @param memberId the member ID to look for
     * @return the matching Member object, or null if not found
     */
    public Member findMemberById(String memberId) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberId().equals(memberId)) {
                return members[i];
            }
        }
        return null;
    }

    // ── Borrow Record Management ──────────────────────────────────────────────

    /**
     * Creates and stores a new borrow record.
     * Record IDs are generated automatically (e.g. REC001, REC002, ...).
     *
     * @param member the member who is borrowing
     * @param item   the item being borrowed
     */
    public void recordBorrow(Member member, LibraryItem item) {
        if (recordCount >= MAX_RECORDS) return;

        // Generate a record ID with leading zeros (e.g. REC001)
        String recordId = "REC" + String.format("%03d", recordCount + 1);

        borrowRecords[recordCount++] = new BorrowRecord(recordId, member, item, "2026-04-28");
    }

    /**
     * Marks the open borrow record for the given member and item as returned.
     *
     * @param member the member who is returning the item
     * @param item   the item being returned
     */
    public void recordReturn(Member member, LibraryItem item) {
        for (int i = 0; i < recordCount; i++) {
            BorrowRecord r = borrowRecords[i];

            // Find the matching open record
            if (!r.isReturned()
                    && r.getMember().getMemberId().equals(member.getMemberId())
                    && r.getItem().getItemId().equals(item.getItemId())) {

                r.setReturnDate("2026-04-28");
                r.setReturned(true);
                return;
            }
        }
    }

    // ── Display Helpers ───────────────────────────────────────────────────────

    /** Prints all books currently in the catalog. */
    public void displayCatalog() {
        System.out.println("  ---- Book Catalog (" + catalogCount + " book(s)) ----");
        if (catalogCount == 0) {
            System.out.println("  (empty)");
        } else {
            for (int i = 0; i < catalogCount; i++) {
                System.out.println("  " + catalog[i].getInfo());
            }
        }
    }

    /** Prints all multimedia items in the library. */
    public void displayMultimedia() {
        System.out.println("  ---- Multimedia (" + multimediaCount + " item(s)) ----");
        if (multimediaCount == 0) {
            System.out.println("  (empty)");
        } else {
            for (int i = 0; i < multimediaCount; i++) {
                System.out.println("  " + multimedia[i].getInfo());
            }
        }
    }

    /** Prints all registered members. */
    public void displayMembers() {
        System.out.println("  ---- Registered Members (" + memberCount + " member(s)) ----");
        if (memberCount == 0) {
            System.out.println("  (empty)");
        } else {
            for (int i = 0; i < memberCount; i++) {
                System.out.println("  " + members[i].getInfo());
            }
        }
    }

    /** Prints all borrow records. */
    public void displayAllRecords() {
        System.out.println("  ---- Borrow Records (" + recordCount + " total) ----");
        if (recordCount == 0) {
            System.out.println("  (none yet)");
        } else {
            for (int i = 0; i < recordCount; i++) {
                System.out.println("  " + borrowRecords[i]);
            }
        }
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    // NOTE: getId() and getName() are INHERITED from Person

    /** Returns the librarian's ID. Same as getId() from Person. */
    public String getLibrarianId() {
        return id; // id is inherited from Person (protected)
    }

    /** Returns the full books catalog array. */
    public Books[] getCatalog() {
        return catalog;
    }

    /** Returns the number of books in the catalog. */
    public int getCatalogCount() {
        return catalogCount;
    }

    /** Returns the full multimedia array. */
    public Multimedia[] getMultimedia() {
        return multimedia;
    }

    /** Returns the number of multimedia items. */
    public int getMultimediaCount() {
        return multimediaCount;
    }

    /** Returns the full members array. */
    public Member[] getMembers() {
        return members;
    }

    /** Returns the number of registered members. */
    public int getMemberCount() {
        return memberCount;
    }

    /** Returns the full borrow records array. */
    public BorrowRecord[] getBorrowRecords() {
        return borrowRecords;
    }

    /** Returns the total number of borrow records logged. */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * OVERRIDING Person.getInfo()
     * Returns a librarian-specific description.
     * Example: Librarian[L001] Mrs. Smith | Books: 5 | Multimedia: 2 | Members: 3
     */
    @Override
    public String getInfo() {
        return "Librarian[" + id + "] " + name
             + " | Books: "     + catalogCount
             + " | Multimedia: " + multimediaCount
             + " | Members: "   + memberCount;
    }

    /**
     * Returns a readable summary when printed.
     */
    public String toString() {
        return "Librarian[" + id + "] " + name;
    }
}
