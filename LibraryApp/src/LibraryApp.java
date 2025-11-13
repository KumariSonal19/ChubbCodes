import java.util.*;
import java.time.*;

public class LibraryApp {
   
    static class Book {
        final int id;
        final String title;
        final String author;
        final String isbn;
        final int totalCopies;
        int copiesAvailable;

        Book(int id, String title, String author, String isbn, int copies) {
            if (copies <= 0) throw new IllegalArgumentException("Copies must be > 0");
            this.id = id; this.title = title; this.author = author; this.isbn = isbn;
            this.totalCopies = copies; this.copiesAvailable = copies;
        }
        @Override public String toString() {
            return String.format("#%d | %s by %s | ISBN:%s | %d/%d available",
                    id, title, author, isbn, copiesAvailable, totalCopies);
        }
    }

    static class Member {
        final int id;
        final String name;
        final String email;
        Member(int id, String name, String email) {
            this.id = id; this.name = name; this.email = email;
        }
        @Override public String toString() { return String.format("#%d | %s <%s>", id, name, email); }
    }

    static class Loan {
        final int loanId;
        final int bookId;
        final int memberId;
        final LocalDate borrowDate;
        LocalDate returnDate; // null => active

        Loan(int loanId, int bookId, int memberId, LocalDate borrowDate) {
            this.loanId = loanId; this.bookId = bookId; this.memberId = memberId;
            this.borrowDate = borrowDate;
        }
        boolean active() { return returnDate == null; }
        @Override public String toString() {
            return String.format("Loan#%d | Book:%d | Member:%d | Borrowed:%s | Returned:%s",
                    loanId, bookId, memberId, borrowDate, returnDate == null ? "-" : returnDate.toString());
        }
    }

    static class LibraryService {
        private final Map<Integer, Book> books = new HashMap<>();
        private final Map<Integer, Member> members = new HashMap<>();
        private final Map<Integer, Loan> loans = new HashMap<>();
        private final Map<Integer, List<Integer>> memberActiveLoanIds = new HashMap<>();

        private int nextBookId = 1;
        private int nextMemberId = 1;
        private int nextLoanId = 1;

        private final int maxLoansPerMember;

        LibraryService(int maxLoansPerMember) { this.maxLoansPerMember = Math.max(1, maxLoansPerMember); }

        public Book addBook(String title, String author, String isbn, int copies) {
            // ensure no duplicate ISBN with same title-author
            for (Book b : books.values()) {
                if (b.isbn.equalsIgnoreCase(isbn))
                    throw new DuplicateBookException("Book with ISBN already exists: " + isbn);
            }
            Book b = new Book(nextBookId++, title, author, isbn, copies);
            books.put(b.id, b);
            return b;
        }

        public Member registerMember(String name, String email) {
            Member m = new Member(nextMemberId++, name, email);
            members.put(m.id, m);
            memberActiveLoanIds.put(m.id, new ArrayList<>());
            return m;
        }

        public Loan borrowBook(int memberId, int bookId) {
            Member m = getMember(memberId);
            Book b = getBook(bookId);
            List<Integer> active = memberActiveLoanIds.get(memberId);
            if (active.size() >= maxLoansPerMember)
                throw new MemberLimitExceededException("Member reached max active loans: " + maxLoansPerMember);
            if (b.copiesAvailable <= 0)
                throw new BookNotAvailableException("No copies available for book #" + b.id);

            b.copiesAvailable--;
            Loan loan = new Loan(nextLoanId++, bookId, memberId, LocalDate.now());
            loans.put(loan.loanId, loan);
            active.add(loan.loanId);
            return loan;
        }

        public void returnBook(int loanId) {
            Loan loan = loans.get(loanId);
            if (loan == null) throw new BusinessException("Loan not found: " + loanId);
            if (!loan.active()) throw new BusinessException("Loan already returned.");
            Book b = getBook(loan.bookId);
            b.copiesAvailable++;
            loan.returnDate = LocalDate.now();
            memberActiveLoanIds.get(loan.memberId).remove(Integer.valueOf(loan.loanId));
        }

        public Collection<Book> listBooks() { return books.values(); }
        public Collection<Member> listMembers() { return members.values(); }
        public Collection<Loan> listLoans() { return loans.values(); }

        private Book getBook(int id) {
            Book b = books.get(id);
            if (b == null) throw new BusinessException("Book not found: " + id);
            return b;
        }
        private Member getMember(int id) {
            Member m = members.get(id);
            if (m == null) throw new BusinessException("Member not found: " + id);
            return m;
        }
    }

   
    public static void main(String[] args) {
        LibraryService svc = new LibraryService(5);
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Library Management System ===");
        loop:
        while (true) {
            System.out.println("\n1) Add Book  2) Register Member  3) Borrow  4) Return  5) List Books  6) List Members  7) List Loans  0) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Title: "); String title = sc.nextLine().trim();
                        System.out.print("Author: "); String author = sc.nextLine().trim();
                        System.out.print("ISBN: "); String isbn = sc.nextLine().trim();
                        System.out.print("Copies: "); int copies = Integer.parseInt(sc.nextLine().trim());
                        Book b = svc.addBook(title, author, isbn, copies);
                        System.out.println("Added " + b);
                    }
                    case "2" -> {
                        System.out.print("Name: "); String name = sc.nextLine().trim();
                        System.out.print("Email: "); String email = sc.nextLine().trim();
                        Member m = svc.registerMember(name, email);
                        System.out.println("Registered " + m);
                    }
                    case "3" -> {
                        System.out.print("Member ID: "); int mid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Book ID: "); int bid = Integer.parseInt(sc.nextLine().trim());
                        Loan loan = svc.borrowBook(mid, bid);
                        System.out.println("Borrowed: " + loan);
                    }
                    case "4" -> {
                        System.out.print("Loan ID: "); int lid = Integer.parseInt(sc.nextLine().trim());
                        svc.returnBook(lid);
                        System.out.println("Returned. Loan #" + lid + " closed.");
                    }
                    case "5" -> svc.listBooks().forEach(System.out::println);
                    case "6" -> svc.listMembers().forEach(System.out::println);
                    case "7" -> svc.listLoans().forEach(System.out::println);
                    case "0" -> { System.out.println("Goodbye!"); break loop; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (BusinessException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e);
            }
        }
        sc.close();
    }
}
