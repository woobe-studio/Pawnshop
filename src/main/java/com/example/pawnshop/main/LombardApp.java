package com.example.pawnshop.main;

import com.example.pawnshop.controller.LombardController;
import com.example.pawnshop.model.LombardRepository;
import com.example.pawnshop.model.TransactionHistory;
import com.example.pawnshop.model.Item;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class LombardApp extends Application {

    private LombardRepository repository = new LombardRepository();
    private TransactionHistory transactionHistory = new TransactionHistory();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pawn Shop Management");

        // UI Components
        TableView<Item> tableView = new TableView<>();
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField categoryField = new TextField();
        TextField valueField = new TextField();
        ChoiceBox<String> sortChoiceBox = new ChoiceBox<>();
        Button addButton = new Button("Add Item");
        Button updateButton = new Button("Update Item");
        Button removeButton = new Button("Remove Item");
        Button viewHistoryButton = new Button("View History");
        Button sortButton = new Button("Sort Items");

        LombardController controller = new LombardController(repository, tableView, idField, nameField, categoryField, valueField, sortChoiceBox, transactionHistory);

        // Setup UI Layout
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #f5f5f5;");

        // Setup TableView Columns
        TableColumn<Item, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));

        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategory()));

        TableColumn<Item, Double> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getValue()));

        TableColumn<Item, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));

        tableView.getColumns().addAll(idColumn, nameColumn, categoryColumn, valueColumn, statusColumn);
        tableView.setItems(FXCollections.observableArrayList(repository.getItems()));

        // Allow table columns to resize with the window
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Input Fields and Buttons
        HBox inputLayout = new HBox(10);
        inputLayout.setHgrow(idField, Priority.ALWAYS);
        inputLayout.setHgrow(nameField, Priority.ALWAYS);
        inputLayout.setHgrow(categoryField, Priority.ALWAYS);
        inputLayout.setHgrow(valueField, Priority.ALWAYS);

        idField.setPromptText("ID");
        nameField.setPromptText("Name");
        categoryField.setPromptText("Category");
        valueField.setPromptText("Value");

        inputLayout.getChildren().addAll(idField, nameField, categoryField, valueField);

        HBox buttonLayout = new HBox(10);
        buttonLayout.setSpacing(10);

        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        updateButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        removeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        viewHistoryButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: white;");
        sortButton.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white;");

        addButton.setOnAction(e -> {
            playSound("clickSound.wav");
            controller.addItem();
        });
        updateButton.setOnAction(e -> {
            playSound("clickSound.wav");
            controller.updateItem();
        });
        removeButton.setOnAction(e -> {
            playSound("clickSound.wav");
            controller.removeItem();
        });
        viewHistoryButton.setOnAction(e -> {
            playSound("clickSound.wav");
            controller.viewTransactionHistory();
        });
        sortButton.setOnAction(e -> {
            playSound("clickSound.wav");
            controller.viewItems();
        });

        buttonLayout.getChildren().addAll(addButton, updateButton, removeButton, viewHistoryButton, sortButton);

        // Row selection listener to update input fields
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Populate fields with the selected item's data
                idField.setText(String.valueOf(newValue.getId()));
                nameField.setText(newValue.getName());
                categoryField.setText(newValue.getCategory());
                valueField.setText(String.valueOf(newValue.getValue()));
                playSound("clickSound.wav"); // Play sound on item selection
            }
        });

        // Add components to main layout
        mainLayout.getChildren().addAll(new Label("Pawn Shop Management System"), tableView, inputLayout, sortChoiceBox, buttonLayout);

        // Create and set scene
        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(new File("styles/styles.css").toURI().toString()); // Applying the CSS stylesheet from the root directory

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to play sound
    public void playSound(String soundFilePath) {
        try {
            File soundFile = new File("sounds/" + soundFilePath);  // Specify the path to your .wav file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();  // Play the sound
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
