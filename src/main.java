/**
 * @author      masjohncook
 * @version     0.0.2
 * @copyright   (C) Copyright 2026
 * @license     None
 * @maintainer  masjohncook
 * @email       mas.john.cook@gmail.com
 * @status      None
 */


import java.util.Scanner;

import LibraryBorrowingSystem.Librarian;
import LibraryBorrowingSystem.LibraryItem;
import LibraryBorrowingSystem.Member;

/**
 * Entry point for the Library Borrowing System.
 *
 * CHANGES from the first version (v 0.0.1):
 *   - Added menu options for Multimedia (Add, View)
 *   - Borrow/Return now supports both Books and Multimedia
 *   - Search now searches across all items (books + multimedia)
 */
public class main {

    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("  Library Borrowing System");
        System.out.println("============================================");
        System.out.println("  Author      : masjohncook");
        System.out.println("  Version     : 0.0.2");
        System.out.println("  Copyright   : (C) Copyright 2026");
        System.out.println("============================================\n");

        Scanner sc = new Scanner(System.in);

        // Create the Librarian — constructor auto-loads books, multimedia, members
        Librarian librarian = new Librarian("L001", "Mrs. Smith");

        System.out.println("=== Welcome, " + librarian.getName() + "! ===\n");

        // Show inheritance and overriding proof on startup
        System.out.println("--- [Inheritance + Overriding Demo] ---");
        System.out.println(librarian.getInfo()); // Librarian overrides Person.getInfo()
        System.out.println("----------------------------------------\n");

        int choice = -1;

        while (choice != 0) {

            System.out.println("\n============================================");
            System.out.println("       LIBRARY BORROWING SYSTEM v0.0.2     ");
            System.out.println("============================================");
            System.out.println(" --- Book Management ---");
            System.out.println("  1.  Add Book (without genre)");
            System.out.println("  2.  Add Book (with genre)");
            System.out.println("  3.  Remove Book");
            System.out.println("  4.  Update Book");
            System.out.println("  5.  View All Books");
            System.out.println(" --- Multimedia Management ---");
            System.out.println("  6.  Add Multimedia (without duration)");
            System.out.println("  7.  Add Multimedia (with duration)");
            System.out.println("  8.  View All Multimedia");
            System.out.println(" --- Member Management ---");
            System.out.println("  9.  Register Member");
            System.out.println("  10. Remove Member");
            System.out.println("  11. Update Member Name");
            System.out.println("  12. View All Members");
            System.out.println(" --- Borrow & Return ---");
            System.out.println("  13. Borrow Item (Book or Multimedia)");
            System.out.println("  14. Return Item");
            System.out.println("  15. Search Item by Title");
            System.out.println("  16. View All Borrow Records");
            System.out.println("--------------------------------------------");
            System.out.println("  0.  Exit");
            System.out.println("============================================");
            System.out.print("Enter choice: ");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // consume leftover newline
            } else {
                sc.nextLine(); // discard invalid input
                System.out.println("  Please enter a number from the menu.");
                continue;
            }

            switch (choice) {

                // ── 1. Add Book (without genre) — OVERLOADING demo ───────────
                case 1:
                    System.out.print("  Book ID   : ");
                    String newId = sc.nextLine().trim();
                    System.out.print("  Title     : ");
                    String newTitle = sc.nextLine().trim();
                    System.out.print("  Author    : ");
                    String newAuthor = sc.nextLine().trim();

                    if (newId.isEmpty() || newTitle.isEmpty() || newAuthor.isEmpty()) {
                        System.out.println("  [FAILED] All fields are required.");
                    } else {
                        // Calls addBook(id, title, author) — 3-param version
                        librarian.addBook(newId, newTitle, newAuthor);
                    }
                    break;

                // ── 2. Add Book (with genre) — OVERLOADING demo ──────────────
                case 2:
                    System.out.print("  Book ID   : ");
                    String newId2 = sc.nextLine().trim();
                    System.out.print("  Title     : ");
                    String newTitle2 = sc.nextLine().trim();
                    System.out.print("  Author    : ");
                    String newAuthor2 = sc.nextLine().trim();
                    System.out.print("  Genre     : ");
                    String newGenre = sc.nextLine().trim();

                    if (newId2.isEmpty() || newTitle2.isEmpty()
                            || newAuthor2.isEmpty() || newGenre.isEmpty()) {
                        System.out.println("  [FAILED] All fields are required.");
                    } else {
                        // Calls addBook(id, title, author, genre) — 4-param OVERLOADED version
                        librarian.addBook(newId2, newTitle2, newAuthor2, newGenre);
                    }
                    break;

                // ── 3. Remove Book ────────────────────────────────────────────
                case 3:
                    librarian.displayCatalog();
                    System.out.print("  Enter Book ID to remove: ");
                    librarian.removeBook(sc.nextLine().trim());
                    break;

                // ── 4. Update Book ────────────────────────────────────────────
                case 4:
                    librarian.displayCatalog();
                    System.out.print("  Enter Book ID to update: ");
                    String updateBookId = sc.nextLine().trim();
                    System.out.print("  New Title  (press Enter to keep current): ");
                    String updatedTitle = sc.nextLine();
                    System.out.print("  New Author (press Enter to keep current): ");
                    String updatedAuthor = sc.nextLine();
                    librarian.updateBook(updateBookId, updatedTitle, updatedAuthor);
                    break;

                // ── 5. View All Books ─────────────────────────────────────────
                case 5:
                    librarian.displayCatalog();
                    break;

                // ── 6. Add Multimedia (without duration) — OVERLOADING demo ──
                case 6:
                    System.out.print("  Item ID   : ");
                    String mmId = sc.nextLine().trim();
                    System.out.print("  Title     : ");
                    String mmTitle = sc.nextLine().trim();
                    System.out.print("  Type (DVD/CD/etc): ");
                    String mmType = sc.nextLine().trim();

                    if (mmId.isEmpty() || mmTitle.isEmpty() || mmType.isEmpty()) {
                        System.out.println("  [FAILED] All fields are required.");
                    } else {
                        // Calls addMultimedia(id, title, type) — 3-param version
                        librarian.addMultimedia(mmId, mmTitle, mmType);
                    }
                    break;

                // ── 7. Add Multimedia (with duration) — OVERLOADING demo ─────
                case 7:
                    System.out.print("  Item ID   : ");
                    String mmId2 = sc.nextLine().trim();
                    System.out.print("  Title     : ");
                    String mmTitle2 = sc.nextLine().trim();
                    System.out.print("  Type (DVD/CD/etc): ");
                    String mmType2 = sc.nextLine().trim();
                    System.out.print("  Duration  : ");
                    String mmDuration = sc.nextLine().trim();

                    if (mmId2.isEmpty() || mmTitle2.isEmpty()
                            || mmType2.isEmpty() || mmDuration.isEmpty()) {
                        System.out.println("  [FAILED] All fields are required.");
                    } else {
                        // Calls addMultimedia(id, title, type, duration) — 4-param OVERLOADED version
                        librarian.addMultimedia(mmId2, mmTitle2, mmType2, mmDuration);
                    }
                    break;

                // ── 8. View All Multimedia ────────────────────────────────────
                case 8:
                    librarian.displayMultimedia();
                    break;

                // ── 9. Register Member ────────────────────────────────────────
                case 9:
                    System.out.print("  Member ID : ");
                    String newMemberId = sc.nextLine().trim();
                    System.out.print("  Name      : ");
                    String newMemberName = sc.nextLine().trim();

                    if (newMemberId.isEmpty() || newMemberName.isEmpty()) {
                        System.out.println("  [FAILED] All fields are required.");
                    } else {
                        librarian.registerMember(newMemberId, newMemberName);
                    }
                    break;

                // ── 10. Remove Member ─────────────────────────────────────────
                case 10:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID to remove: ");
                    librarian.removeMember(sc.nextLine().trim());
                    break;

                // ── 11. Update Member Name ────────────────────────────────────
                case 11:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID to update: ");
                    String updateMemberId = sc.nextLine().trim();
                    System.out.print("  New Name  : ");
                    String updatedName = sc.nextLine().trim();
                    librarian.updateMember(updateMemberId, updatedName);
                    break;

                // ── 12. View All Members ──────────────────────────────────────
                case 12:
                    librarian.displayMembers();
                    break;

                // ── 13. Borrow Item ───────────────────────────────────────────
                case 13:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID : ");
                    String borrowMemberId = sc.nextLine().trim();

                    Member borrower = librarian.findMemberById(borrowMemberId);

                    if (borrower == null) {
                        System.out.println("  [FAILED] Member not found.");
                    } else {
                        // Show all items (books + multimedia)
                        System.out.println("\n  -- Available Items --");
                        librarian.displayCatalog();
                        librarian.displayMultimedia();

                        System.out.print("\n  Enter Item ID (B001 for book, MM01 for multimedia): ");
                        String borrowItemId = sc.nextLine().trim();

                        // Try to find the item in books first, then multimedia
                        LibraryItem itemToBorrow = librarian.findBookById(borrowItemId);
                        if (itemToBorrow == null) {
                            itemToBorrow = librarian.findMultimediaById(borrowItemId);
                        }

                        if (itemToBorrow == null) {
                            System.out.println("  [FAILED] Item not found.");
                        } else {
                            // borrowItem() is the updated method in Member
                            if (borrower.borrowItem(itemToBorrow)) {
                                librarian.recordBorrow(borrower, itemToBorrow);
                            }
                        }
                    }
                    break;

                // ── 14. Return Item ───────────────────────────────────────────
                case 14:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID : ");
                    String returnMemberId = sc.nextLine().trim();

                    Member returner = librarian.findMemberById(returnMemberId);

                    if (returner == null) {
                        System.out.println("  [FAILED] Member not found.");
                    } else if (returner.getBorrowCount() == 0) {
                        System.out.println("  " + returner.getName() + " has no borrowed items.");
                    } else {
                        // Show what this member currently has
                        System.out.println("  Items borrowed by " + returner.getName() + ":");
                        LibraryItem[] borrowed = returner.getBorrowedItems();
                        for (int i = 0; i < returner.getBorrowCount(); i++) {
                            System.out.println("    " + borrowed[i].getInfo());
                        }

                        System.out.print("  Enter Item ID to return: ");
                        String returnItemId = sc.nextLine().trim();

                        // Search books then multimedia
                        LibraryItem itemToReturn = librarian.findBookById(returnItemId);
                        if (itemToReturn == null) {
                            itemToReturn = librarian.findMultimediaById(returnItemId);
                        }

                        if (itemToReturn == null) {
                            System.out.println("  [FAILED] Item not found.");
                        } else {
                            if (returner.returnItem(itemToReturn)) {
                                librarian.recordReturn(returner, itemToReturn);
                            }
                        }
                    }
                    break;

                // ── 15. Search Item by Title ──────────────────────────────────
                case 15:
                    librarian.displayMembers();
                    System.out.print("  Enter Member ID : ");
                    String searchMemberId = sc.nextLine().trim();

                    Member searcher = librarian.findMemberById(searchMemberId);

                    if (searcher == null) {
                        System.out.println("  [FAILED] Member not found.");
                    } else {
                        System.out.print("  Enter keyword   : ");
                        String keyword = sc.nextLine().trim();

                        // searchItem() now searches all items (books + multimedia)
                        searcher.searchItem(
                            librarian.getAllItems(),
                            librarian.getAllItemsCount(),
                            keyword
                        );
                    }
                    break;

                // ── 16. View All Borrow Records ───────────────────────────────
                case 16:
                    librarian.displayAllRecords();
                    break;

                // ── 0. Exit ───────────────────────────────────────────────────
                case 0:
                    System.out.println("\n  Goodbye! Library system closed.");
                    break;

                // ── Invalid Input ─────────────────────────────────────────────
                default:
                    System.out.println("  Invalid choice. Please enter a number from the menu.");
            }
        }

        sc.close();
    }
}
