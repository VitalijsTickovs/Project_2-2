module com.example.project_22 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project_22 to javafx.fxml;
    exports com.example.project_22;
}