package hostel.dao;

import hostel.models.Student;
import hostel.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StudentDao {
    
    // Test database connection
    public boolean testConnection() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("✅ Database connection SUCCESSFUL!");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Database connection FAILED: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public void saveStudent(Student student) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
            System.out.println("Student saved successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}