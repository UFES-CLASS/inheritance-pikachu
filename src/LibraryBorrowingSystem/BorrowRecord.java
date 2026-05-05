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
 * Represents a single borrow transaction in the library system.
 *
 * CHANGE from v0.0.1: The "book" field has been changed from Books to LibraryItem
 * so that borrow records can now track BOTH books and multimedia items.
 *
 * A BorrowRecord is created every time a member borrows an item, and it is
 * updated when the item is returned. It links a Member and a LibraryItem
 * together with date information.
 *
 * Attributes:
 *   - recordId   : unique identifier for this record (e.g. "REC001")
 *   - member     : the member who borrowed the item (association -> Member)
 *   - item       : the item that was borrowed (association -> LibraryItem)
 *   - borrowDate : the date the item was borrowed
 *   - returnDate : the date the item was returned ("-" if not yet returned)
 *   - returned   : true if the item has been returned, false otherwise
 */
public class BorrowRecord {

    // recordId is the unique ID for this transaction, e.g. "REC001"
    private String recordId;

    // member holds a reference to the Member who borrowed the item
    // Association: BorrowRecord -> Member
    private Member member;

    // item holds a reference to the LibraryItem that was borrowed
    // Changed from Books to LibraryItem so it works for Books AND Multimedia
    // Association: BorrowRecord -> LibraryItem
    private LibraryItem item;

    // borrowDate stores the date when the item was taken out
    private String borrowDate;

    // returnDate stores the date when the item was brought back
    // Set to "-" by default until the item is actually returned
    private String returnDate;

    // returned is a flag: false means item is still out, true means it was returned
    private boolean returned;

    /**
     * Creates a new borrow record when a member takes an item.
     *
     * @param recordId   unique identifier for this record
     * @param member     the member borrowing the item
     * @param item       the item being borrowed
     * @param borrowDate the date the item is borrowed
     */
    public BorrowRecord(String recordId, Member member, LibraryItem item, String borrowDate) {
        this.recordId   = recordId;
        this.member     = member;
        this.item       = item;
        this.borrowDate = borrowDate;
        this.returnDate = "-";    // not returned yet
        this.returned   = false;  // not returned yet
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    /** Returns the unique record ID. */
    public String getRecordId() {
        return recordId;
    }

    /** Returns the member associated with this record. */
    public Member getMember() {
        return member;
    }

    /** Returns the item associated with this record. */
    public LibraryItem getItem() {
        return item;
    }

    /** Returns the date the item was borrowed. */
    public String getBorrowDate() {
        return borrowDate;
    }

    /** Returns the return date, or "-" if not yet returned. */
    public String getReturnDate() {
        return returnDate;
    }

    /** Returns true if the item has been returned. */
    public boolean isReturned() {
        return returned;
    }

    // ── Setters ───────────────────────────────────────────────────────────────

    /** Updates the record ID. */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /** Updates the member linked to this record. */
    public void setMember(Member member) {
        this.member = member;
    }

    /** Updates the item linked to this record. */
    public void setItem(LibraryItem item) {
        this.item = item;
    }

    /** Updates the borrow date. */
    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    /** Updates the return date when the item is handed back. */
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    /** Marks whether the item has been returned. */
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    /**
     * Returns a readable summary of this borrow record.
     * Example: Record[REC001] Member: Alice | Item: 1984 | Borrowed: 2026-04-28 | Returned: -
     */
    public String toString() {
        return "Record[" + recordId + "]"
             + " Member: "    + member.getName()
             + " | Item: "    + item.getTitle()
             + " | Borrowed: " + borrowDate
             + " | Returned: " + returnDate;
    }
}
