package com.example.pawnshop.main;

import java.applet.Applet;

import com.example.pawnshop.controller.LombardController;
import com.example.pawnshop.model.LombardRepository;
import com.example.pawnshop.model.TransactionHistory;
import com.example.pawnshop.model.Item;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


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

        LombardController controller = new LombardController(repository, tableView, idField, nameField, categoryField, valueField, sortChoiceBox, transactionHistory);

        // Setup UI Layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

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
        Button addButton = new Button("Add Item");
        Button updateButton = new Button("Update Item");
        Button removeButton = new Button("Remove Item");
        Button viewHistoryButton = new Button("View History");
        Button sortButton = new Button("Sort Items");

        buttonLayout.getChildren().addAll(addButton, updateButton, removeButton, viewHistoryButton, sortButton);

        // Event Handlers for Buttons
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

        // Bind the table width to the main layout's width so that the table adjusts dynamically
        tableView.prefWidthProperty().bind(mainLayout.widthProperty());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

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