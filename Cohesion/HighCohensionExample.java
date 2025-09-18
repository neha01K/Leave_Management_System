class BookService {
    public void addBook(String bookTitle) {
        System.out.println("Book added: " + bookTitle);
    }

    public void issueBook(String bookTitle, String studentName) {
        System.out.println("Issued book: " + bookTitle + " to " + studentName);
    }
}

class FineService {
    public void calculateFine(int daysLate) {
        int fine = daysLate * 5;
        System.out.println("Fine: " + fine + " INR");
    }
}

class NotificationService {
    public void sendNotification(String studentName, String message) {
        System.out.println("Sending notification to " + studentName + ": " + message);
    }
}


public class HighCohensionExample {
    private BookService bookService;
    private FineService fineService;
    private NotificationService notificationService;

    public LibraryManagementSystem() {
        this.bookService = new BookService();
        this.fineService = new FineService();
        this.notificationService = new NotificationService();
    }

    public void runDemo() {
        bookService.addBook("Java Programming");
        bookService.issueBook("Java Programming", "Arundati");
        fineService.calculateFine(4);
        notificationService.sendNotification("Arundati", "Return the book on time!");
    }

    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.runDemo();
    }
}
