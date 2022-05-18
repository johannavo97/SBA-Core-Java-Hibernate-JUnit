package sba.sms.services;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import java.util.List;

public class CourseService implements CourseI {

    @Override
    public void createCourse(Course course) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            s.persist(course);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx!=null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            Course c = s.get(Course.class,courseId);
            if(c == null)
                throw new HibernateException("Did not find Course");
            else
                return c;

        } catch (HibernateException exception) {
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return new Course();
    }

    @Override
    public List<Course> getAllCourses() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Course> c = s.createQuery("from Course",Course.class).getResultList();
        s.close();
        return c;
    }
}
