package list;

import java.util.ArrayList;
import java.util.Iterator;

//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

public class ArrListPracticeQue {
    public static void main(String[] args) {

        //Printing the Smallest and Largest element in the list
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(2);
        arr.add(5);
        arr.add(26);
        arr.add(9);
        arr.add(34);
        arr.add(99);
        arr.add(2);
        arr.add(1);

        /*//Largest
        int largestNum = arr.get(0);
        int smallestNum = arr.get(0);

        Iterator<Integer> it = arr.iterator();
        while (it.hasNext()) {
            int current = it.next();
            if (largestNum < current) largestNum = current;
            if (smallestNum > current) smallestNum = current;

        }
        System.out.println("Largest Number: "+largestNum);
        System.out.println("Smallest Number: "+smallestNum);
*/
        //---------------------------------------------------------------------------

        /*Filtering: Create an ArrayList of Integers and populate it with a mix of even and odd numbers.
        Iterate through the list and create a new ArrayList containing only the even numbers.*/

        ArrayList<Integer> a = new ArrayList<>();
        for(Integer i: arr){
            if(i%2==0) a.add(i);
        }
        System.out.println(a);


        //---------------------------------------------------------------------------

        /*Searching: Create an ArrayList of custom objects, such as a Book class with title and author properties.
        Populate the list with several books. Write a method to search for a book by its title and
        return the Book object if found.*/

        ArrayList<Book> book = new ArrayList<>();
        book.add(new Book("Rework","Jason Fried"));
        book.add(new Book("The Subtle art of not giving F*","Mark Manson"));
        book.add(new Book("Pride and Prejudice","Jane Austen"));

        String bookTitle = "Pride and Prejudice";
        Book b = findBookbyTitle(book, bookTitle);
        if(b!=null){
            System.out.println("Book Found!\nBook name: "+ b.getTitle()+", Book Author: "+b.getAuthor());
        }
        else{
            System.out.println("Book with title: "+ bookTitle+ ". Not Found");
        }

    }
    public static Book findBookbyTitle(ArrayList<Book> books, String searchtitle) {
        for(Book book: books){
            if(book.getTitle()==searchtitle){
                return book;
            }
        }
        return null;
    }
}

class Book {
    private String title;
    private String author;

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }


}
