package hostel.models;

import jakarta.persistence.*;

@Entity
@Table(name = "STUDENT")
public class Student {
    
    @Id
    @Column(name = "DIGITALID")
    private Long digitalId;  // Primary Key (matches database)
    
    @Column(name = "STUDENTNAME")
    private String studentName;
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "YEAR")
    private Integer year;
    
    @Column(name = "PARENTNAME")
    private String parentName;
    
    @Column(name = "STUDENTPHONENO")
    private String studentPhoneNo;
    
    @Column(name = "PARENTPHONENO") 
    private String parentPhoneNo;
    
    @Column(name = "STUDENTMAILID")
    private String studentMailId;
    
    @Column(name = "ADDRESS")
    private String address;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOMNO")
    private Room room;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOSTELNO")
    private Hostel hostel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFERENCENO")
    private Preference preference;

    // --- Constructors ---
    public Student() {}

    public Student(Long digitalId, String studentName, String department, Integer year, 
                  String parentName, String studentPhoneNo, String parentPhoneNo, 
                  String studentMailId, String address) {
        this.digitalId = digitalId;
        this.studentName = studentName;
        this.department = department;
        this.year = year;
        this.parentName = parentName;
        this.studentPhoneNo = studentPhoneNo;
        this.parentPhoneNo = parentPhoneNo;
        this.studentMailId = studentMailId;
        this.address = address;
    }

    // --- Getters and Setters ---
    public Long getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(Long digitalId) {
        this.digitalId = digitalId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getStudentPhoneNo() {
        return studentPhoneNo;
    }

    public void setStudentPhoneNo(String studentPhoneNo) {
        this.studentPhoneNo = studentPhoneNo;
    }

    public String getParentPhoneNo() {
        return parentPhoneNo;
    }

    public void setParentPhoneNo(String parentPhoneNo) {
        this.parentPhoneNo = parentPhoneNo;
    }

    public String getStudentMailId() {
        return studentMailId;
    }

    public void setStudentMailId(String studentMailId) {
        this.studentMailId = studentMailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    @Override
    public String toString() {
        return String.format("Student[ID: %d, Name: %s, Dept: %s, Year: %d]", 
                           digitalId, studentName, department, year);
    }
}