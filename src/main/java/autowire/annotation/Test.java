package autowire.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("autoconfigannotation.xml");

        //Employee emp = (Employee)applicationContext.getBean("employee");

        Employee emp = applicationContext.getBean("employee", Employee.class);

        System.out.println(emp.getAddress());
        System.out.println(emp);
    }
}
