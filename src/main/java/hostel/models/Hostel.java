package hostel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
@Table(name = "HOSTEL")  // ← ADD THIS
public class Hostel {

    @Id
    @Column(name = "HOSTELNO")  // ← MAP TO ACTUAL COLUMN NAME
    private int hostelNo;  // ← RENAME FIELD TO MATCH COLUMN

    @Column(name = "HOSTELNAME")
    private String hostelName;

    @Column(name = "TOTALROOMS")
    private int totalRooms;

    @OneToMany(mappedBy = "hostel")
    private List<Room> rooms;

    // --- Constructors ---
    public Hostel() {}

    public Hostel(String hostelName, int totalRooms) {
        this.hostelName = hostelName;
        this.totalRooms = totalRooms;
    }

    // --- Getters and Setters ---
    public int getHostelNo() {  // ← RENAME GETTER
        return hostelNo;
    }

    public void setHostelNo(int hostelNo) {  // ← RENAME SETTER
        this.hostelNo = hostelNo;
    }

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return hostelName + " (" + totalRooms + " rooms)";
    }
}