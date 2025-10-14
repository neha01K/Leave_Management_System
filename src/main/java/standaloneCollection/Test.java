package standaloneCollection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("standAloneCollectionconfig.xml");

        Person person = applicationContext.getBean("person", Person.class);

        System.out.println(person.getFriends());
        System.out.println(person.getFriends().getClass().getName());

    }
}
