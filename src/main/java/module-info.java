module com.example.guil {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.logging;


    opens com.example.guil to javafx.fxml;
    exports com.example.guil;
}