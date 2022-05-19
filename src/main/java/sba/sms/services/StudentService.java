package sba.sms.services;

import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentI {

    /**
     * reads the student table in database
     * @return database data as a List<Student>
     */
    @Override
    public List<Student> getAllStudents() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Student> students = s.createQuery("from Student",Student.class).getResultList();
        s.close();
        return students;
    }

    @Override
    public void createStudent(Student student) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            s.persist(student);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx!=null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
    }

    /**
     * takes a Student’s email as a String and parses the student list for a Student with that email and returns a Student Object.
     * @param email - student's email to be parsed
     * @return the student list of a Student with respective `email`
     */
    @Override
    public Student getStudentByEmail(String email) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            Student e = s.get(Student.class,email);
            if(e == null)
                throw new HibernateException("Did not find Student");
            else
                return e;

        } catch (HibernateException exception) {
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return new Student();
    }

    /**
     * This method takes two parameters: the first one is the user email and the second one is the password from the user input.
     * @param email - email student uses to log in
     * @param password - password student uses to log in
     * @return `true` if a student was found; else `false`
     */
    @Override
    public boolean validateStudent(String email, String password) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            tx.commit();
            return getStudentByEmail(email).getPassword().equals(password);
        } catch (HibernateException exception) {
            if (tx!=null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return false;
    }

    /**
     * After a successful student validation, this method takes a Student’s email and a Course ID.
     * It checks in the join table (i.e. Student_Course) generated by JPA to find if a Student with that Email is currently attending a Course with that ID.
     * If the Student is not attending that Course, register the student to that course; otherwise not
     * @param email - email student uses to log in
     * @param courseId - id of course student wishes to register to
     */
    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            Student student = s.get(Student.class, email);
            Course c = s.get(Course.class,courseId);
            c. addStudent(student);
            s.merge(c);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx!=null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
    }

    /**
     * This method takes a Student’s Email as a parameter and would find all the courses a student is registered.
     * @param email - student's email to be parsed
     * @return list of courses student has registered to
     */
    @Override
    public List<Course> getStudentCourses(String email) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            TypedQuery<Course> q = s.createNamedQuery("findStudentCourses");
            q.setParameter("s", s);
            return q.getResultList();

        } catch (HibernateException exception) {
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return new ArrayList<>();
    }
}
