package com.springHiber;
import com.springHiber.entities.Certificate;
import com.springHiber.entities.Student;
import com.springHiber.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Student student = new Student();
        student.setName("Manoj Vajyapee");
        student.setAbout("Hardworking Actor");
        student.setFatherName("Manjot");
        student.setPhone("3333344444");
        student.setActive(true);
        student.setCollege("IIT Roorkee");

        Certificate certificate = new Certificate();
        certificate.setTitle("Java Certificate");
        certificate.setAbout("This is a Java Certificate");
        certificate.setLink("link");
        certificate.setStudent(student);

        Certificate certificate1 = new Certificate();
        certificate1.setTitle("Python Certificate");
        certificate1.setAbout("This is a Python Certificate");
        certificate1.setLink("link");
        certificate1.setStudent(student);

        student.getCertificates().add(certificate);
        student.getCertificates().add(certificate1);

        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.persist(student);
            System.out.println("Student saved successfully");
            transaction.commit();
        }
        catch(Exception exception){
            if(transaction!=null)
                transaction.rollback();
            exception.printStackTrace();

        }
        finally{
            session.close();
        }
    }
}
