package hostel.controllers;

import javafx.beans.property.SimpleStringProperty;
import hostel.services.AllocationService;
import hostel.models.Room;
import hostel.models.Student;
import hostel.util.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import java.util.List;

public class AllocateController {

    @FXML private TextField registerNoField;
    @FXML private TextArea studentDetails;
    @FXML private TableView<Room> roomsTable;
    @FXML private TableColumn<Room, String> colHostel;
    @FXML private TableColumn<Room, Long> colRoomNo;
    @FXML private TableColumn<Room, String> colType;
    @FXML private TableColumn<Room, Integer> colCapacity;
    @FXML private TableColumn<Room, Integer> colAvailable;
    @FXML private Label statusLabel;

    // NEW FIELDS FOR STUDENTS TABLE
    @FXML private TableView<Student> studentsTable;
    @FXML private TableColumn<Student, Long> colStudentId;
    @FXML private TableColumn<Student, String> colStudentName;
    @FXML private TableColumn<Student, String> colDepartment;
    @FXML private TableColumn<Student, Integer> colYear;

    @FXML private TableView<Student> allocatedStudentsTable;
    @FXML private TableColumn<Student, Long> colAllocatedStudentId;
    @FXML private TableColumn<Student, String> colAllocatedStudentName;
    @FXML private TableColumn<Student, String> colAllocatedDepartment;
    @FXML private TableColumn<Student, Integer> colAllocatedYear;
    @FXML private TableColumn<Student, String> colRoomAllocated;

    private AllocationService allocationService = new AllocationService();

    // Initialize method - sets up table columns
    @FXML
    public void initialize() {
        // Set up ROOMS table columns
        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("totalCapacity"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        
        // FIXED: Use getHostelName() method
        colHostel.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            if (room.getHostel() != null && room.getHostel().getHostelName() != null) {
                return new SimpleStringProperty(room.getHostel().getHostelName());
            } else {
                return new SimpleStringProperty("Main Hostel");
            }
        });

        // NEW: Setup STUDENTS WITHOUT ROOMS table columns
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("digitalId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        // NEW: Setup ALLOCATED STUDENTS table columns
        colAllocatedStudentId.setCellValueFactory(new PropertyValueFactory<>("digitalId"));
        colAllocatedStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAllocatedDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colAllocatedYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        
        // Custom column for room number
        colRoomAllocated.setCellValueFactory(cellData -> {
            Student student = cellData.getValue();
            try {
                Session session = HibernateUtil.getSessionFactory().openSession();
                String hql = "SELECT a.room.roomNo FROM Allocation a WHERE a.student.digitalId = :studentId AND a.isActive = 'Y'";
                Long roomNo = session.createQuery(hql, Long.class)
                                   .setParameter("studentId", student.getDigitalId())
                                   .uniqueResult();
                session.close();
                return new SimpleStringProperty(roomNo != null ? roomNo.toString() : "N/A");
            } catch (Exception e) {
                return new SimpleStringProperty("N/A");
            }
        });

        // Load both student tables on startup
        loadStudentsWithoutRooms();
        loadStudentsWithRooms();
        
        // FIXED: Wait for scene to be available, then load CSS
        roomsTable.sceneProperty().addListener((observable, oldValue, newScene) -> {
            if (newScene != null) {
                try {
                    String cssPath = getClass().getResource("/fxml/app.css").toExternalForm();
                    newScene.getStylesheets().add(cssPath);
                    System.out.println("✅ CSS LOADED SUCCESSFULLY: " + cssPath);
                } catch (Exception e) {
                    System.out.println("❌ CSS LOADING FAILED: " + e.getMessage());
                }
            }
        });
    }

    // NEW METHOD: Load students without allocated rooms
    private void loadStudentsWithoutRooms() {
        try {
            List<Student> studentsWithoutRooms = allocationService.getStudentsWithoutRooms();
            studentsTable.getItems().setAll(studentsWithoutRooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // NEW METHOD: Load students with allocated rooms
    private void loadStudentsWithRooms() {
        try {
            List<Student> studentsWithRooms = allocationService.getStudentsWithRooms();
            allocatedStudentsTable.getItems().setAll(studentsWithRooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSearchStudent(ActionEvent event) {
        try {
            String registerNo = registerNoField.getText();
            if (registerNo.isEmpty()) {
                statusLabel.setText("Please enter a register number");
                return;
            }

            // Get available rooms
            List<Room> availableRooms = allocationService.getAvailableRooms();
            roomsTable.getItems().setAll(availableRooms);

            // Get student details
            Long studentId = Long.parseLong(registerNo);
            Student student = getStudentById(studentId);
            if (student != null) {
                studentDetails.setText("Name: " + student.getStudentName() + "\n" +
                                     "Department: " + student.getDepartment() + "\n" +
                                     "Year: " + student.getYear());
                statusLabel.setText("Found " + availableRooms.size() + " available rooms");
            } else {
                studentDetails.setText("Student not found");
                statusLabel.setText("Student not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void allocateStudent(ActionEvent event) {
        try {
            Room selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
            if (selectedRoom == null) {
                statusLabel.setText("Please select a room first");
                return;
            }

            String registerNo = registerNoField.getText();
            if (registerNo.isEmpty()) {
                statusLabel.setText("Please enter a register number");
                return;
            }

            Long studentId = Long.parseLong(registerNo);
            Long roomId = selectedRoom.getRoomNo();
            
            // Allocate the room (admin can be "system" for now)
            allocationService.allocate(studentId, roomId, "system");
            
            statusLabel.setText("Room allocated successfully!");
            new Alert(Alert.AlertType.INFORMATION, "Room allocated successfully!").show();
            
            // Refresh available rooms AND both student tables
            onSearchStudent(event);
            loadStudentsWithoutRooms(); // Refresh students without rooms
            loadStudentsWithRooms();    // Refresh students with rooms
            
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Allocation failed: " + e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    private Student getStudentById(Long studentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, studentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}