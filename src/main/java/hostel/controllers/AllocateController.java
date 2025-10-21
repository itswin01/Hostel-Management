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

    private AllocationService allocationService = new AllocationService();

    // Initialize method - sets up table columns
    @FXML
    public void initialize() {
    // Set up table columns FIRST
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
            
            // Refresh available rooms
            onSearchStudent(event);
            
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