package withoutXML;

import org.springframework.stereotype.Component;

//@Component
public class Employee {

    public void work(){
        System.out.println("Employee is working hard!");
    }

    @Override
    public String toString() {
        return "Employee{}";
    }
}
