module src.main.java.com.example.project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens src.main.java.com.example.project3 to javafx.fxml;
    exports src.main.java.com.example.project3;
}