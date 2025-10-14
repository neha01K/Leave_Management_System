package withoutXML;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

        Employee employee = applicationContext.getBean("getEmployee", Employee.class);

        System.out.println(employee);
        employee.work();
    }
}
