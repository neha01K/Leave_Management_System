public class Main{
    public static void main(String[] args) {
        int[] aNum = {20, 30, 40, 50};
        int[] bDen = {2,0,10,4};

        for(int i=0; i<aNum.length; i++){
            System.out.println(divide(aNum[i],bDen[i]));
        }
    }

    static int divide(int a, int b) throws ArithmeticException{
        try{
            return a/b;
        }
        catch(Exception e){
            System.out.println(e);
            return -1;
        }
    }
}