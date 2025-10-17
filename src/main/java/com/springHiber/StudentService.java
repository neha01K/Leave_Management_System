package com.springHiber;

import com.springHiber.entities.Student;
import com.springHiber.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

public class StudentService {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public void saveStudent(Student student) {

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            session.persist(student);
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Student getById(int studentId) {

        try (Session session = sessionFactory.openSession()) {
            Student student = session.find(Student.class, studentId);
            return student;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Student updateStudent(Student student, int studentId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Student oldStudent = session.find(Student.class, studentId);

            if (oldStudent != null) {
                oldStudent.setFatherName(student.getFatherName());
                oldStudent.setCollege(student.getCollege());
                oldStudent.setAbout(student.getAbout());
                oldStudent.setPhone(student.getPhone());
                oldStudent.setCertificates(student.getCertificates());

                oldStudent = session.merge(oldStudent);
            }
            transaction.commit();
            return oldStudent;
        }
    }

    public void delete(int studentId) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Student student = session.find(Student.class, studentId);
            if(student!=null){
                session.remove(student);
            }
            transaction.commit();
        }
    }

    //get all students using hql
    public List<Student> getAllStudentsHQL(){
        try(Session session = sessionFactory.openSession()){
            String hqlQuery = "FROM Student";
            Query<Student> query = session.createQuery(hqlQuery, Student.class);
            return query.list();
        }
    }

    public Student getStudentByName(String studentName){
        try(Session session = sessionFactory.openSession()){
            String hqlQuery = "FROM Student WHERE name=:nameOfStudent";

            Query<Student> query = session.createQuery(hqlQuery, Student.class);
            query.setParameter("nameOfStudent", studentName);
            return query.uniqueResult();
        }
    }

    public List<Student> getStudentByCollegeCriteria(String college){
        try(Session session = sessionFactory.openSession()){

            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);

            Root<Student> root = query.from(Student.class);
            query.select(root).where(criteriaBuilder.equal(root.get("college"),college));
            Query<Student> query2 = session.createQuery(query);
            return query2.getResultList();
        }
    }

    public List<Student> getStudentByPagination(int pageNo, int pageSize){
        try(Session session = sessionFactory.openSession()){
            String pagiQuery = "FROM Student";
            Query<Student> query = session.createQuery(pagiQuery, Student.class);
            query.setFirstResult((pageNo-1)*pageSize);
            query.setMaxResults(pageSize);
            return query.list();
        }
    }
}