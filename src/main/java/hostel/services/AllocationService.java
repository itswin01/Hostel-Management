package hostel.services;

import hostel.models.Allocation;
import hostel.models.Room;
import hostel.models.Student;
import hostel.exception.AllocationException;
import hostel.util.HibernateUtil;

import org.hibernate.Session;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

public class AllocationService {

    // ADD THIS METHOD for your UI table
    public List<Room> getAvailableRooms() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Room WHERE capacity > 0";
            List<Room> availableRooms = session.createQuery(hql, Room.class).list();
            session.close();
            return availableRooms;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Student> getStudentsWithoutRooms() {
    try {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT s FROM Student s WHERE s.digitalId NOT IN " +
                    "(SELECT a.student.digitalId FROM Allocation a WHERE a.isActive = 'Y')";
        List<Student> students = session.createQuery(hql, Student.class).list();
        session.close();
        return students;
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
        }
    }

    public List<Student> getStudentsWithRooms() {
    try {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT DISTINCT s FROM Student s " +
                    "JOIN Allocation a ON s.digitalId = a.student.digitalId " +
                    "WHERE a.isActive = 'Y'";
        List<Student> students = session.createQuery(hql, Student.class).list();
        session.close();
        return students;
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
        }
    }
    @Transactional
    public void allocate(Long studentId, Long roomId, String admin) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();

            // Step 1: Check if student already has an active allocation
            // FIXED: Changed query to match your actual column names
            Long count = em.createQuery(
                            "SELECT COUNT(a) FROM Allocation a WHERE a.student.digitalId = :sid AND a.isActive = 'Y'", 
                            Long.class)
                           .setParameter("sid", studentId)
                           .getSingleResult();

            if (count > 0) {
                throw new AllocationException("Student already allocated to a room.");
            }

            // Step 2: Get room (removed gender check since your Room entity doesn't have gender)
            Room room = em.find(Room.class, roomId);
            if (room == null) {
                throw new AllocationException("Room not found.");
            }

            // Step 3: Check current occupancy - FIXED to use capacity field
            if (room.getCapacity() <= 0) {
                throw new AllocationException("Room is already full.");
            }

            // Step 4: Get student
            Student student = em.find(Student.class, studentId);
            if (student == null) {
                throw new AllocationException("Student not found.");
            }

            // Step 5: Create and persist allocation
            Allocation allocation = new Allocation();
            allocation.setStudent(student);
            allocation.setRoom(room);
            allocation.setStartDate(LocalDate.now());
            allocation.setIsActive("Y");
            allocation.setCreatedBy(admin);

            em.persist(allocation);
            
            // Step 6: Update room capacity
            room.setCapacity(room.getCapacity() - 1);
            em.merge(room);
            
            transaction.commit();
            
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new AllocationException("Allocation failed: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}