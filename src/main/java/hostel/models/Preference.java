package hostel.models;

import jakarta.persistence.*;

@Entity
@Table(name = "PREFERENCE")
public class Preference {
    
    @Id
    @Column(name = "PREFERENCENO")
    private Long preferenceNo;
    
    @Column(name = "PROOMTYPE")
    private String roomType;  // "AC" or "NonAC"
    
    @Column(name = "PROOMCATEGORY")
    private String roomCategory;  // "1", "2", "3", "4"

    // Constructors
    public Preference() {}

    public Preference(Long preferenceNo, String roomType, String roomCategory) {
        this.preferenceNo = preferenceNo;
        this.roomType = roomType;
        this.roomCategory = roomCategory;
    }

    // Getters and Setters
    public Long getPreferenceNo() {
        return preferenceNo;
    }

    public void setPreferenceNo(Long preferenceNo) {
        this.preferenceNo = preferenceNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }
}