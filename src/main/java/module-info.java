module com.example.sae4_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sae4_project to javafx.fxml;
    exports com.example.sae4_project.Controller;
    opens com.example.sae4_project.Controller to javafx.fxml;
    exports com.example.sae4_project.Application;
    opens com.example.sae4_project.Application to javafx.fxml;
    exports com.example.sae4_project.Entity;
    opens com.example.sae4_project.Entity to javafx.fxml;

}