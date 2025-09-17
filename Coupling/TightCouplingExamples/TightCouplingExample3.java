class Calculator {
    public static double add(double a, double b) {
        return a + b;
    }
}

class BillingSystem {
    public void generateBill(double price, double tax) {
        double totalAmount = Calculator.add(price, tax);
        System.out.println("Final Bill: " + totalAmount);
    }
}

public class TightCouplingExample3 {
    public static void main(String[] args) {
        BillingSystem billing = new BillingSystem();
        billing.generateBill(100.0, 5.0);
    }
}