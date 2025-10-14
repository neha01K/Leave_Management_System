package stereotypeAnnotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("stereoConfig.xml");

        Student s1 = applicationContext.getBean("student", Student.class);
        System.out.println(s1);
        System.out.println("s1 object hashcode: "+s1.hashCode());

        Student s2 = applicationContext.getBean("student", Student.class);
        System.out.println("s2 object hashcode: "+s2.hashCode());
    }
}
