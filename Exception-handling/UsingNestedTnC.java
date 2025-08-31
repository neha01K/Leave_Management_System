public class UsingNestedTnC {
    public static void main(String[] args) {
        try {
            String cardNum = "1234";
            if (cardNum.length() != 16)
                throw new Exception("Invalid Card Number");


            try{
                int pin = 1234;
                int enteredPin = 4567;
                if(pin!=enteredPin){
                    throw  new Exception("Pin doesn't match");
                }
                System.out.println("Pin entered successfully. Now, accessing the amount.");

                try{
                    int balance = 5000;
                    int amount = 6000;
                    if(amount>balance){
                        throw new Exception("Insufficient balance");
                    }
                }
                catch(Exception e){
                    System.out.println("transaction error: "+ e.getMessage());
                }
            }
            catch(Exception e){
                System.out.println("Pin error: "+ e.getMessage());
            }
        }
        catch(Exception e){
            System.out.println("ATM error: "+ e.getMessage());
        }
    }
}
