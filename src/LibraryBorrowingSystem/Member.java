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
 * Represents a library member who can borrow, return, and search for items.
 *
 * INHERITANCE: Member extends Person
 *   - id and name are INHERITED from Person (not re-declared here)
 *   - Member adds its own fields: borrowedItems and borrowCount
 *
 * OVERRIDING: getInfo() overrides Person.getInfo()
 *   - Returns member-specific details (borrow count) instead of the generic version
 *
 * Attributes (own):
 *   - borrowedItems : array of LibraryItem objects this member is currently borrowing
 *   - borrowCount   : how many items this member currently has borrowed
 */
public class Member extends Person {

    // borrowedItems holds the items this member is currently borrowing
    // Uses LibraryItem (not just Books) so members can borrow Books OR Multimedia
    // This is an association: Member -> LibraryItem[]
    private LibraryItem[] borrowedItems;

    // borrowCount tracks how many items the member is currently borrowing
    private int borrowCount;

    // MAX_BORROW limits how many items a member can borrow at once
    private static final int MAX_BORROW = 5;

    /**
     * Creates a new member with the given ID and name.
     * Calls the parent (Person) constructor using super() to set id and name.
     *
     * @param id   unique member identifier (e.g. "M001")
     * @param name full name of the member
     */
    public Member(String id, String name) {
        // super() calls Person's constructor to set id and name
        super(id, name);

        // Create an empty array with MAX_BORROW slots
        this.borrowedItems = new LibraryItem[MAX_BORROW];

        // No items borrowed yet
        this.borrowCount = 0;
    }

    /**
     * Returns the pre-defined initial member data.
     *
     * @return array of Member objects pre-filled with default members
     */
    public static Member[] getInitialMembers() {
        Member[] initial = new Member[3];

        initial[0] = new Member("M001", "Alice");
        initial[1] = new Member("M002", "Bob");
        initial[2] = new Member("M003", "Charlie");

        return initial;
    }

    /**
     * Borrows a library item (Book or Multimedia).
     * Fails if the item is already borrowed or this member has reached the limit.
     *
     * @param item the LibraryItem to borrow
     * @return true if the borrow was successful, false otherwise
     */
    public boolean borrowItem(LibraryItem item) {
        // Check if the item is available
        if (!item.isAvailable()) {
            System.out.println("  [FAILED] \"" + item.getTitle() + "\" is currently not available.");
            return false;
        }

        // Check if this member has reached their borrow limit
        if (borrowCount >= MAX_BORROW) {
            System.out.println("  [FAILED] " + name + " has reached the borrow limit (" + MAX_BORROW + ").");
            return false;
        }

        // Add the item to the borrowedItems array and increase the count
        borrowedItems[borrowCount++] = item;

        // Mark the item as no longer available
        item.setAvailable(false);

        System.out.println("  [SUCCESS] " + name + " borrowed \"" + item.getTitle() + "\".");
        return true;
    }

    /**
     * Returns a borrowed item back to the library.
     * Fails if this member does not currently have the given item.
     *
     * @param item the LibraryItem to return
     * @return true if the return was successful, false otherwise
     */
    public boolean returnItem(LibraryItem item) {
        // Loop through all items this member is currently borrowing
        for (int i = 0; i < borrowCount; i++) {
            if (borrowedItems[i] != null
                    && borrowedItems[i].getItemId().equals(item.getItemId())) {

                // Mark the item as available again
                item.setAvailable(true);

                // Replace this slot with the last item in the array to close the gap
                borrowedItems[i] = borrowedItems[--borrowCount];

                // Clear the last slot
                borrowedItems[borrowCount] = null;

                System.out.println("  [SUCCESS] " + name + " returned \"" + item.getTitle() + "\".");
                return true;
            }
        }

        System.out.println("  [FAILED] " + name + " does not have \"" + item.getTitle() + "\".");
        return false;
    }

    /**
     * Searches the library catalog for items whose title contains the keyword.
     * The search is case-insensitive. All matching items are printed.
     *
     * @param catalog     the full array of LibraryItem in the library
     * @param catalogSize the number of valid items in the catalog array
     * @param keyword     the search keyword to match against item titles
     */
    public void searchItem(LibraryItem[] catalog, int catalogSize, String keyword) {
        System.out.println("  Search results for \"" + keyword + "\":");

        boolean found = false;

        for (int i = 0; i < catalogSize; i++) {
            if (catalog[i] != null
                    && catalog[i].getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("    -> " + catalog[i].getInfo());
                found = true;
            }
        }

        if (!found) {
            System.out.println("    No items found matching \"" + keyword + "\".");
        }
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    // NOTE: getId() and getName() are INHERITED from Person — not rewritten here

    /** Returns the unique member ID. Same as getId() from Person. */
    public String getMemberId() {
        return id; // id is inherited from Person (protected)
    }

    /** Returns the array of items currently borrowed by this member. */
    public LibraryItem[] getBorrowedItems() {
        return borrowedItems;
    }

    /** Returns the number of items currently borrowed. */
    public int getBorrowCount() {
        return borrowCount;
    }

    /**
     * OVERRIDING Person.getInfo()
     * Returns a member-specific description including borrow count.
     * Example: Member[M001] Alice (borrowing: 1 item(s))
     */
    @Override
    public String getInfo() {
        return "Member[" + id + "] " + name
             + " (borrowing: " + borrowCount + " item(s))";
    }

    /**
     * Returns a readable summary when printed.
     */
    public String toString() {
        return getInfo();
    }
}
