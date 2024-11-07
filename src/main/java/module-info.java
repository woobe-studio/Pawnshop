module com.example.pawnshop {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.pawnshop to javafx.fxml;
    exports com.example.pawnshop.controller;  // Export controller package
    exports com.example.pawnshop.model;      // Export model package
    exports com.example.pawnshop.view;       // Export view package
}
