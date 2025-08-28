public class Finally {
    static void methodA(){
        methodB();
    }
    static void methodB(){
        methodC();
    }
    static void methodC(){
        System.out.println("this is last point");
    }
    public static void main(String[] args) {
        int[] arr = {2,3};
        /*try{
            System.out.println(arr[5]);
            methodA(); //this will not run at all.

        }
        catch(ArrayIndexOutOfBoundsException aIdx){
            System.out.println("this is beyond array size");
        }
        finally{
            System.out.println("ye part toh chalega hi");
        }*/

        //------------------------------------------------------------------
        int[] array = new int[5];
        try{
            System.out.println(array[6]);
        }
        finally{
            System.out.println("this program should be stopped here but it didn't - Finally block!");
        }
    }
}
