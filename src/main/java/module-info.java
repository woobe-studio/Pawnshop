module com.example.pawnshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;  // Add this line to declare the dependency on org.json

    opens com.example.pawnshop to javafx.fxml;
    exports com.example.pawnshop.controller;  // Export controller package
    exports com.example.pawnshop.model;      // Export model package
    exports com.example.pawnshop.view;       // Export view package
}
