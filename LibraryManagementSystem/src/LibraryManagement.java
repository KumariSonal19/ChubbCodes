import java.util.*;

class Book {
    String title;
    String author;
    boolean isBorrowed;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    @Override
    public String toString() {
        return title + " by " + author + (isBorrowed ? " [Borrowed]" : " [Available]");
    }
}

class Member {
    String name;
    int memberId;

    public Member(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return name + " (ID: " + memberId + ")";
    }
}

public class LibraryManagement {
    static List<Book> books = new ArrayList<>();
    static List<Member> members = new ArrayList<>();
    static Map<Integer, List<Book>> borrowedBooks = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Sample data
        books.add(new Book("Java Basics", "Author A"));
        books.add(new Book("Python Programming", "Author B"));
        books.add(new Book("Data Structures", "Author C"));

        members.add(new Member("Alice", 101));
        members.add(new Member("Bob", 102));

        while (true) {
            System.out.println("\nLibrary Menu: 1.Add Book 2.Add Member 3.Borrow Book 4.View Books 5.Exit");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Book title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    books.add(new Book(title, author));
                    System.out.println("Book added successfully!");
                    break;
                case 2:
                    System.out.print("Member name: ");
                    String name = sc.nextLine();
                    int id = members.size() + 101;
                    members.add(new Member(name, id));
                    System.out.println("Member added successfully with ID: " + id);
                    break;
                case 3:
                    System.out.print("Member ID: ");
                    int memberId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Book title to borrow: ");
                    String borrowTitle = sc.nextLine();
                    borrowBook(memberId, borrowTitle);
                    break;
                case 4:
                    viewBooks();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void borrowBook(int memberId, String title) {
        Member member = members.stream().filter(m -> m.memberId == memberId).findFirst().orElse(null);
        Book book = books.stream().filter(b -> b.title.equalsIgnoreCase(title)).findFirst().orElse(null);

        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        if (book.isBorrowed) {
            System.out.println("Book is already borrowed!");
            return;
        }

        book.isBorrowed = true;
        borrowedBooks.computeIfAbsent(memberId, k -> new ArrayList<>()).add(book);
        System.out.println(member.name + " borrowed " + book.title);
    }

    static void viewBooks() {
        System.out.println("\nBooks in Library:");
        for (Book b : books) {
            System.out.println(b);
        }
    }
}
