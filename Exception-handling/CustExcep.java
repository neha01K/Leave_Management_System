class InvalidAmountException extends Exception {
    public InvalidAmountException(String message){
        super(message);
    }
}
public class CustExcep {
    public static void main(String[] args) {
        try{
            deposit(-1000);
        }
        catch(InvalidAmountException e){
            System.out.println(e.getMessage());
        }
    }
    static void deposit(int amount) throws InvalidAmountException{
        if(amount<=0)
            throw new InvalidAmountException("Don't kidding me!");
    }
}
