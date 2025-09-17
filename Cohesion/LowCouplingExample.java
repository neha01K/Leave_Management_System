public class LowCouplingExample {

    public void addBook(String title) {
        System.out.println("Book added: " + title);
    }

    public void issueBook(String title, String studentName) {
        System.out.println("Issued book: " + title + " to " + studentName);
    }

    public void calculateFine(int daysLate) {
        int fine = daysLate * 5;
        System.out.println("Fine: " + fine + " INR");
    }

    public void sendNotification(String studentName, String message) {
        System.out.println("Sending notification to " + studentName + ": " + message);
    }

    public void generateReport() {
        System.out.println("Generating library report...");
    }

}
