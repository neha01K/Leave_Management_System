class Student{
    private int age;

    public Student(int age){
        this.age = age;
    }

    public void setAge(int age){
        if(age<0){
            System.out.println("Invalid! Age can't be negative");
        }
        this.age = age;
    }

    public int getAge(){
        return this.age;
    }
}

class School{

    void promoteStudent(Student student){
        System.out.println("Student current age: "+ student.getAge());
        student.setAge(student.getAge()+1);
        System.out.println("Student promoted age: "+ student.getAge());
    }
}


public class LooseCouplingExample2 {
    public static void main(String[] args) {
        Student suhani = new Student(15);
        School school = new School();
        school.promoteStudent(suhani);

    }
}
