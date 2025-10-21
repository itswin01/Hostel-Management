package hostel;

import hostel.dao.StudentDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // ADD THESE 3 LINES TO TEST DATABASE
        System.out.println("Testing database connection...");
        StudentDao studentDao = new StudentDao();
        studentDao.testConnection();
        // END OF ADDED LINES
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/allocate.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hostel Room Allocation System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}