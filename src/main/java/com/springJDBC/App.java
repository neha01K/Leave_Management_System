//comments here are made for my own understanding

package com.springJDBC;

import com.springJDBC.dao.StudentDao;
import com.springJDBC.entities.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println( "My program is starting..." );

        //with XML
        /*ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("config.xml");*/

        //without XML
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(JdbcConfig.class);
        StudentDao studentDao = applicationContext.getBean("studentDaoImpl", StudentDao.class);

        /*JdbcTemplate template = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);

        String query = "insert into student(id, name, city) values(?,?,?)";

        int result = template.update(query,144,"Babita Ji","Chennai");
        System.out.println("Number of rows affected: "+result);*/

        System.out.print("Enter Student ID: ");
        int id = Integer.parseInt(reader.readLine());
        System.out.print("Enter Student Name: ");
        String name = reader.readLine();
        System.out.print("Enter Student City: ");
        String city = reader.readLine();

        Student student = new Student();

        student.setId(id);
        student.setName(name);
        student.setCity(city);

        int insertionCount = studentDao.insert(student);

        System.out.println("Number of rows Inserted: "+insertionCount);

        /*
        Student student = new Student();
        student.setId(987);
        student.setName("Bhide");
        student.setCity("Mumbai");

        int result=studentDao.insert(student);


        System.out.println("Rows affected: "+ result);*/

        /*System.out.print("Enter Id value: ");
        int idValue = Integer.parseInt(reader.readLine());

        int result = studentDao.delete(idValue);
        System.out.println("Number of rows Deleted: "+result);*/

        System.out.print("Enter Student ID: ");
        int studentID = Integer.parseInt(reader.readLine());
        Student studentRepresentation =studentDao.getStudent(studentID);
        System.out.println(studentRepresentation);

        System.out.println("All Students:");

        List<Student> students  = studentDao.getAllStudent();
        for(Student s: students){
            System.out.println(s);
        }
    }
}
