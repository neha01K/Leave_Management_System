class Student {
    public int age;

    Student(int age) {
        this.age = age;
    }
}

class School{

    public void promoteStudentAge(Student student){
        System.out.println("Student current age: "+ student.age);
        student.age++;
        System.out.println("Student age promoted: "+student.age);
    }
}

public class TightCouplingExample2 {
    public static void main(String[] args) {

        Student manMohan = new Student(12);
        School school = new School();
        school.promoteStudentAge(manMohan);
    }
}
