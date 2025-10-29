-- ===== SAMPLE DATA FOR HOSTEL ROOM SYSTEM =====

-- Clear existing data (optional - be careful!)
-- DELETE FROM allocation;
-- DELETE FROM student;
-- DELETE FROM room;
-- DELETE FROM hostel;

-- Insert Hostels
INSERT INTO hostel (hostelno, hostelname, totalrooms) VALUES (1, 'North Hostel', 50);
INSERT INTO hostel (hostelno, hostelname, totalrooms) VALUES (2, 'South Hostel', 40);
INSERT INTO hostel (hostelno, hostelname, totalrooms) VALUES (3, 'East Hostel', 30);

-- Insert Rooms for North Hostel (Hostel 1)
INSERT INTO room (roomno, hostelno, roomtype, roomcategory, totalcapacity, capacity) VALUES (101, 1, 'AC', '1', 1, 1);
INSERT INTO room (roomno, hostelno, roomtype, roomcategory, totalcapacity, capacity) VALUES (102, 1, 'NonAC', '2', 2, 2);
INSERT INTO room (roomno, hostelno, roomtype, roomcategory, totalcapacity, capacity) VALUES (103, 1, 'AC', '3', 3, 3);
INSERT INTO room (roomno, hostelno, roomtype, roomcategory, totalcapacity, capacity) VALUES (104, 1, 'NonAC', '4', 4, 4);

-- Insert Rooms for South Hostel (Hostel 2)
INSERT INTO room (roomno, hostelno, roomtype, roomcategory, totalcapacity, capacity) VALUES (201, 2, 'AC', '1', 1, 1);
INSERT INTO room (roomno, hostelno, roomtype, roomcategory, totalcapacity, capacity) VALUES (202, 2, 'NonAC', '2', 2, 2);
INSERT INTO room (roomno, hostelno, roomtype, roomcategory, totalcapacity, capacity) VALUES (203, 2, 'AC', '3', 3, 2); -- 1 occupied
INSERT INTO room (roomno, hostelno, roomtype, roomcategory, totalcapacity, capacity) VALUES (204, 2, 'NonAC', '4', 4, 0); -- Full

-- Insert Rooms for East Hostel (Hostel 3)
INSERT INTO room (roomno, hostelno, roomtype, roomcategory, totalcapacity, capacity) VALUES (301, 3, 'AC', '1', 1, 1);
INSERT INTO room (roomno, hostelno, roomtype, roomcategory, totalcapacity, capacity) VALUES (302, 3, 'NonAC', '2', 2, 2);

-- Insert Students
INSERT INTO student (digitalid, studentname, department, year) VALUES (1, 'Alice Johnson', 'CSE', 1);
INSERT INTO student (digitalid, studentname, department, year) VALUES (2, 'Bob Smith', 'IT', 2);
INSERT INTO student (digitalid, studentname, department, year) VALUES (3, 'Carol Davis', 'ECE', 3);
INSERT INTO student (digitalid, studentname, department, year) VALUES (4, 'David Wilson', 'MECH', 4);
INSERT INTO student (digitalid, studentname, department, year) VALUES (5, 'Eva Brown', 'CSE', 1);
INSERT INTO student (digitalid, studentname, department, year) VALUES (6, 'Frank Miller', 'IT', 2);

-- Insert some allocations
INSERT INTO allocation (allocation_id, student_id, room_id, start_date, is_active, created_by) 
VALUES (allocation_seq.nextval, 3, 203, SYSDATE, 'Y', 'admin');

INSERT INTO allocation (allocation_id, student_id, room_id, start_date, is_active, created_by) 
VALUES (allocation_seq.nextval, 4, 204, SYSDATE, 'Y', 'admin');

COMMIT;

-- Verify the data
SELECT '=== HOSTELS ===' AS "" FROM DUAL;
SELECT * FROM hostel;

SELECT '=== ROOMS ===' AS "" FROM DUAL;
SELECT r.roomno, h.hostelname, r.roomtype, r.roomcategory, r.totalcapacity, r.capacity 
FROM room r JOIN hostel h ON r.hostelno = h.hostelno;

SELECT '=== STUDENTS ===' AS "" FROM DUAL;
SELECT digitalid, studentname, department, year FROM student;

SELECT '=== ALLOCATIONS ===' AS "" FROM DUAL;
SELECT a.allocation_id, s.studentname, r.roomno, h.hostelname, a.start_date, a.is_active 
FROM allocation a 
JOIN student s ON a.student_id = s.digitalid 
JOIN room r ON a.room_id = r.roomno 
JOIN hostel h ON r.hostelno = h.hostelno;