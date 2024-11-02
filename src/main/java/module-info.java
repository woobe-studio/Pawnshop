module com.example.pawnshop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pawnshop to javafx.fxml;
    exports com.example.pawnshop;
}