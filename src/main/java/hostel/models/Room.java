package hostel.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ROOM")
public class Room {

    @Id
    @Column(name = "ROOMNO")
    private Long roomNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "HOSTELNO")
    private Hostel hostel;

    @Column(name = "ROOMTYPE")
    private String roomType;  // "AC" or "NonAC"

    @Column(name = "ROOMCATEGORY")
    private String roomCategory;  // "1", "2", "3", "4"

    @Column(name = "TOTALCAPACITY")
    private Integer totalCapacity;

    // FIXED: This should map to CAPACITY column (current available capacity)
    @Column(name = "CAPACITY")
    private Integer capacity;

    // --- Getters and Setters ---
    public Long getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Long roomNo) {
        this.roomNo = roomNo;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
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

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    // FIXED: Getter/Setter for capacity
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    // FIXED: Updated logic for available space
    public boolean hasAvailableSpace() {
        return capacity > 0; // Room is available if capacity > 0
    }

    // Helper method to get current occupancy
    public Integer getCurrentOccupancy() {
        return totalCapacity - capacity;
    }

    @Override
    public String toString() {
        return String.format("Room[No: %d, Type: %s, Category: %s, Available: %d/%d]", 
                           roomNo, roomType, roomCategory, capacity, totalCapacity);
    }
}