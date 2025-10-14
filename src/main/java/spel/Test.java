package spel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spelConfig.xml");

        SpelDemo spelDemoObj  = applicationContext.getBean("spelDemo", SpelDemo.class);

        System.out.println(spelDemoObj);
    }
}
