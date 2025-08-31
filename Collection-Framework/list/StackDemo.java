package list;

import java.util.Stack;

public class StackDemo {
    public static void main(String[] args) {

        Stack<String> stack = new Stack<>();

        stack.push("Vishal");
        stack.push("rohan");
        stack.push("manoj");
        stack.push("parveen");

        System.out.println(stack);

        stack.pop();

        System.out.println(stack);

        System.out.println(stack.peek());

    }
}
