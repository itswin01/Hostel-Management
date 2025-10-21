package hostel.models;

import jakarta.persistence.*;

@Entity
@Table(name = "WARDEN")
public class Warden {
    
    @Id
    @Column(name = "WARDENNO")
    private Long wardenNo;
    
    @Column(name = "WARDENNAME")
    private String wardenName;
    
    @Column(name = "WARDENPHONENO")
    private String wardenPhoneNo;
    
    @Column(name = "WARDENMAILID")
    private String wardenMailId;

    // Constructors
    public Warden() {}

    public Warden(Long wardenNo, String wardenName, String wardenPhoneNo, String wardenMailId) {
        this.wardenNo = wardenNo;
        this.wardenName = wardenName;
        this.wardenPhoneNo = wardenPhoneNo;
        this.wardenMailId = wardenMailId;
    }

    // Getters and Setters
    public Long getWardenNo() {
        return wardenNo;
    }

    public void setWardenNo(Long wardenNo) {
        this.wardenNo = wardenNo;
    }

    public String getWardenName() {
        return wardenName;
    }

    public void setWardenName(String wardenName) {
        this.wardenName = wardenName;
    }

    public String getWardenPhoneNo() {
        return wardenPhoneNo;
    }

    public void setWardenPhoneNo(String wardenPhoneNo) {
        this.wardenPhoneNo = wardenPhoneNo;
    }

    public String getWardenMailId() {
        return wardenMailId;
    }

    public void setWardenMailId(String wardenMailId) {
        this.wardenMailId = wardenMailId;
    }
}