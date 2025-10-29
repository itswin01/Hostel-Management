-- Creating Tables

CREATE TABLE STUDENT (
    DigitalID NUMBER PRIMARY KEY, 
    StudentName VARCHAR2(20), 
    Department VARCHAR2(10), 
    Year NUMBER, 
    
    
);

CREATE TABLE HOSTEL (
    HostelNo NUMBER PRIMARY KEY, 
    WardenNo NUMBER, 
    HostelExecutiveNo NUMBER, 
    TotalRooms NUMBER
);



CREATE TABLE ROOM (
    RoomNo NUMBER PRIMARY KEY, 
    HostelNo NUMBER, 
    RoomType VARCHAR2(6), 
    RoomCategory VARCHAR2(15), 
    TotalCapacity NUMBER, 
    Capacity NUMBER
);

CREATE TABLE WARDEN (
    WardenNo NUMBER PRIMARY KEY, 
    WardenName VARCHAR2(20), 
    WardenPhoneNo VARCHAR2(15), 
    WardenMailID VARCHAR2(50)
);

CREATE TABLE HOSTEL_EXECUTIVE (
    HostelExecutiveNo NUMBER PRIMARY KEY, 
    HEName VARCHAR2(20), 
    HEPhoneNo VARCHAR2(15), 
    HEMailID VARCHAR2(50)
);

-- Adding Foreign Keys

ALTER TABLE STUDENT ADD FOREIGN KEY (RoomNo) REFERENCES ROOM(RoomNo);
ALTER TABLE STUDENT ADD FOREIGN KEY (HostelNo) REFERENCES HOSTEL(HostelNo);
ALTER TABLE STUDENT ADD FOREIGN KEY (PreferenceNo) REFERENCES PREFERENCE(PreferenceNo);
ALTER TABLE HOSTEL ADD FOREIGN KEY (WardenNo) REFERENCES WARDEN(WardenNo);
ALTER TABLE HOSTEL ADD FOREIGN KEY (HostelExecutiveNo) REFERENCES HOSTEL_EXECUTIVE(HostelExecutiveNo);
ALTER TABLE ROOM ADD FOREIGN KEY (HostelNo) REFERENCES HOSTEL(HostelNo);

-- Adding Constraints

ALTER TABLE STUDENT ADD CONSTRAINT dept_check CHECK (Department IN ('CSE','IT','ECE','MECH','EEE','CIVIL','CHEM','BME'));
ALTER TABLE STUDENT ADD CONSTRAINT year_check CHECK (Year IN (1,2,3,4));
ALTER TABLE HOSTEL ADD CONSTRAINT hostelno_check CHECK (HostelNo IN (1,2,3,4,5,6));
ALTER TABLE ROOM ADD CONSTRAINT roomtype_check CHECK (RoomType IN ('AC','NonAC'));
ALTER TABLE ROOM ADD CONSTRAINT roomcategory_check CHECK (RoomCategory IN ('1','2','3','4'));

-- Trigger for checking if room is full or not

CREATE OR REPLACE TRIGGER allocateroom
BEFORE INSERT ON STUDENT
FOR EACH ROW
BEGIN
    UPDATE ROOM
    SET Capacity = Capacity + 1
    WHERE RoomNo = :NEW.RoomNo AND Capacity < TotalCapacity;
END;
/

-- Procedure for add a person into room

CREATE OR REPLACE PROCEDURE allocate_room(
    p_digitalid IN NUMBER,
    p_roomno IN NUMBER
) AS
    v_capacity NUMBER;
    v_total NUMBER;
BEGIN
    SELECT Capacity, TotalCapacity INTO v_capacity, v_total FROM ROOM WHERE RoomNo = p_roomno;
    IF v_capacity < v_total THEN
        UPDATE ROOM SET Capacity = Capacity + 1 WHERE RoomNo = p_roomno;
        UPDATE STUDENT SET RoomNo = p_roomno WHERE DigitalID = p_digitalid;
        DBMS_OUTPUT.PUT_LINE('Room allocated successfully.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Room is full.');
    END IF;
END;
/

-- View for displaying the Student's hostel details

CREATE OR REPLACE VIEW student_hostel_view AS
SELECT s.StudentName, s.Department, h.HostelNo, r.RoomNo, w.WardenName
FROM STUDENT s
JOIN HOSTEL h ON s.HostelNo = h.HostelNo
JOIN ROOM r ON s.RoomNo = r.RoomNo
JOIN WARDEN w ON h.WardenNo = w.WardenNo;