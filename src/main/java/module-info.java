module com.example.sae4_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sae4_project to javafx.fxml;
    opens com.example.sae4_project.Controller to javafx.fxml;
    exports com.example.sae4_project;
    exports com.example.sae4_project.Controller;
}