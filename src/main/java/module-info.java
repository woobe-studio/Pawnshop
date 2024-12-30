module com.example.pawnshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop; // Retain this if you are using AWT or Swing components from java.desktop

    // Export packages that are used by other modules or frameworks
    exports com.example.pawnshop.main;       // Export main package for application entry
    exports com.example.pawnshop.controller;  // Export controller package
    exports com.example.pawnshop.model;      // Export model package
    exports com.example.pawnshop.view;       // Export view package

    // Open main package to JavaFX modules
    opens com.example.pawnshop.main to javafx.graphics, javafx.fxml;
}
