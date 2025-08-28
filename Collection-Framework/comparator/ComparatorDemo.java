package compartor;

import java.util.ArrayList;
import java.util.Comparator;

public class ComparatorDemo {
    public static void main(String[] args) {
        ArrayList<Student> arrList = new ArrayList<>();
        arrList.add(new Student("Ramesh", 11));
        arrList.add(new Student("Mahesh", 21));
        arrList.add(new Student("Suresh", 11));
        arrList.add(new Student("Mukesh", 46));
        arrList.add(new Student("Latesh", 5));

        //using comparator with lambda expression
        arrList.sort(Comparator.comparingInt(Student::getRollno).thenComparing(Student::getName));

//arrList.sort(Comparator.comparingInt(stu->stu.getRollno()).thenComparing(stu->stu.getName()));

        arrList.sort(Comparator.comparingInt(stu->stu.rollno));


        System.out.println(arrList);

    }
}
class Student{
     String name;
     int rollno;

    public Student(String name,int rollno ){
        this.name = name;
        this.rollno = rollno;
    }

    @Override
    public String toString(){
        return "Student{Name: "+ name+ ", Roll No: "+rollno+"}\n";
    }

    public String getName(){
        return this.name;
    }

    public int getRollno(){
        return this.rollno;
    }
}
