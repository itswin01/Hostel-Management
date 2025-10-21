package hostel.models;

import jakarta.persistence.*;

@Entity
@Table(name = "HOSTEL_EXECUTIVE")
public class HostelExecutive {
    
    @Id
    @Column(name = "HOSTELEXECUTIVENO")
    private Long hostelExecutiveNo;
    
    @Column(name = "HENAME")
    private String heName;
    
    @Column(name = "HEPHONENO")
    private String hePhoneNo;
    
    @Column(name = "HEMAILID")
    private String heMailId;

    // Constructors
    public HostelExecutive() {}

    public HostelExecutive(Long hostelExecutiveNo, String heName, String hePhoneNo, String heMailId) {
        this.hostelExecutiveNo = hostelExecutiveNo;
        this.heName = heName;
        this.hePhoneNo = hePhoneNo;
        this.heMailId = heMailId;
    }

    // Getters and Setters
    public Long getHostelExecutiveNo() {
        return hostelExecutiveNo;
    }

    public void setHostelExecutiveNo(Long hostelExecutiveNo) {
        this.hostelExecutiveNo = hostelExecutiveNo;
    }

    public String getHeName() {
        return heName;
    }

    public void setHeName(String heName) {
        this.heName = heName;
    }

    public String getHePhoneNo() {
        return hePhoneNo;
    }

    public void setHePhoneNo(String hePhoneNo) {
        this.hePhoneNo = hePhoneNo;
    }

    public String getHeMailId() {
        return heMailId;
    }

    public void setHeMailId(String heMailId) {
        this.heMailId = heMailId;
    }
}