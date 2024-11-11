package com.example.pawnshop.view;

import com.example.pawnshop.model.Item;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private Scanner scanner = new Scanner(System.in);

    // ANSI escape codes for text colors
    private static final String RESET = "\033[0m";
    private static final String BOLD = "\033[1m";
    private static final String GREEN = "\033[32m";
    private static final String CYAN = "\033[36m";
    private static final String YELLOW = "\033[33m";
    private static final String RED = "\033[31m";
    private static final String BLUE = "\033[34m";
    private static final String MAGENTA = "\033[35m";
    private static final String WHITE = "\033[37m";

    private int selectedOption = 0;  // Track currently selected menu option
    private final String[] menuOptions = {
            "        Add Item        ",
            "       View Items       ",
            "       Update Item      ",
            "       Remove Item      ",
            "View Transaction History",
            "          Exit          "
    };

    // Display the main menu with highlight for the selected option
    public void displayMenu() {
        clearScreen();
        System.out.print(CYAN + "==================== Lombard Application ====================" + RESET + "\n");
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedOption) {
                // Highlight the selected option
                System.out.print("| " + RED + (i + 1) + ". " + menuOptions[i] + RESET + " |" + "\n");
            } else {
                // Normal color for other options
                System.out.print("| " + GREEN + (i + 1) + ". " + menuOptions[i] + RESET + " |" + "\n");
            }
        }
        System.out.print("============================================================\n");
        System.out.print(YELLOW + "Use 'W' to move up, 'S' to move down, and Enter to select." + RESET + "\n");
    }

    // Get user's choice with real-time 'W' and 'S' key navigation
    public int getChoice() {
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();

            switch (input) {
                case "W": // Move up
                    selectedOption = (selectedOption - 1 + menuOptions.length) % menuOptions.length;
                    playSound("enter.wav");
                    break;
                case "S": // Move down
                    selectedOption = (selectedOption + 1) % menuOptions.length;
                    playSound("enter.wav");
                    break;
                case "": // Enter key pressed
                    return selectedOption + 1; // Return the selected option
                default:
                    System.out.println(RED + "Invalid input. Use 'W' or 'S'." + RESET);
            }

            clearScreen();
            displayMenu();
        }
    }

    // Display a list of items with a bit of formatting
    public void displayItems(List<Item> items) {
        showLoading();
        if (items.isEmpty()) {
            System.out.println(RED + "No items available." + RESET);
        } else {
            System.out.println(BOLD + "==================== Items List ====================" + RESET);
            System.out.println("| " + BLUE + "ID" + RESET + "   | " + MAGENTA + "Name" + RESET + "    | " + CYAN + "Category" + RESET + " | " + GREEN + "Value" + RESET + "  | " + YELLOW + "Status" + RESET + " |");
            System.out.println("------------------------------------------------------------");
            for (Item item : items) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("| " + BLUE + item.getId() + RESET + "   | " + MAGENTA + item.getName() + RESET + "    | " + CYAN + item.getCategory() + RESET + " | " + GREEN + item.getValue() + RESET + "  | " + YELLOW + item.getStatus() + RESET + " |");
            }
            System.out.println("============================================================");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // Prompt the user for item details to add or update an item
    public Item getItemDetails() {
        System.out.print(YELLOW + "Enter ID: " + RESET);
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print(YELLOW + "Enter Name: " + RESET);
        String name = scanner.nextLine();
        System.out.print(YELLOW + "Enter Category: " + RESET);
        String category = scanner.nextLine();
        System.out.print(YELLOW + "Enter Value: " + RESET);
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return new Item(id, name, category, value, "Available");
    }

    // Prompt the user for an item ID (to update or remove an item)
    public int getItemId() {
        System.out.print(YELLOW + "Enter the ID of the item to update/remove: " + RESET);
        int itemId = scanner.nextInt();
        scanner.nextLine(); // Clear the leftover newline
        return itemId;
    }

    // Display a message to the user with a visual emphasis
    public void displayMessage(String message) {
        System.out.println(GREEN + message + RESET);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Display sorting options with a box
    public void displaySortMenu() {
        System.out.println(CYAN + "==================== Sort Options ====================" + RESET);
        System.out.println("| " + GREEN + "1." + RESET + "   Sort by ID (Ascending)     |");
        System.out.println("| " + GREEN + "2." + RESET + "   Sort by ID (Descending)    |");
        System.out.println("| " + GREEN + "3." + RESET + "  Sort by Price (Ascending)   |");
        System.out.println("| " + GREEN + "4." + RESET + "  Sort by Price (Descending)  |");
        System.out.println("| " + GREEN + "5." + RESET + "         Do not sort          |");
        System.out.println("| " + GREEN + "6." + RESET + "           Go Back            |");
        System.out.println("============================================================");
        System.out.print(YELLOW + "Choose a sorting option: " + RESET);
    }

    // Get sorting choice
    public int getSortChoice() {
        int itemId = scanner.nextInt();
        scanner.nextLine(); // Clear the leftover newline
        return itemId;
    }

    // Display transaction history in a box
    public void displayHistory(List<String> history) {
        showLoading();
        if (history.isEmpty()) {
            System.out.println(RED + "No history available." + RESET);
        } else {
            System.out.println(CYAN + "==================== Transaction History ====================" + RESET);
            for (String entry : history) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("| " + BLUE + entry + RESET + " |");
            }
            System.out.println("============================================================");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Simulate the clearing of the screen
    public void clearScreen() {
        try {
            // Create Robot instance to simulate key press
            Robot robot = new Robot();

            // Simulate pressing Ctrl + ; (this clears the screen in some terminals like IntelliJ)
            robot.keyPress(KeyEvent.VK_CONTROL);  // Press Ctrl
            robot.keyPress(KeyEvent.VK_SEMICOLON); // Press ;

            // Release the keys
            robot.keyRelease(KeyEvent.VK_SEMICOLON); // Release ;
            robot.keyRelease(KeyEvent.VK_CONTROL);  // Release Ctrl

            // You can use a delay to ensure the terminal clears properly
            Thread.sleep(50);  // Short delay to allow the screen to clear

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Simulate the loading progress with ASCII art
    public void showLoading() {
        String[] loadingStates = {
                "[          ]",
                "[=         ]",
                "[==        ]",
                "[===       ]",
                "[====      ]",
                "[=====     ]",
                "[======    ]",
                "[=======   ]",
                "[========  ]",
                "[========= ]",
                "[==========]"
        };
        playSound("loading.wav");
        clearScreen();
        // Simulate a 5-second loading progress with delays
        for (int i = 0; i < loadingStates.length; i++) {
            // Print the loading bar on the same line using \r (carriage return)
            System.out.print("\rLoading... " + loadingStates[i]);  // Overwrite the previous line
            try {
                Thread.sleep(300);  // Delay of 500ms (half a second) between each update
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        clearScreen();
        // After the progress is complete, print the completion message
        System.out.println();  // Move to the next line after the loading bar is complete
        System.out.println("Loading Complete!");
    }

    public static void playSound(String soundFilePath) {
        try {
            File soundFile = new File("sounds/" + soundFilePath);  // Specify the path to your .wav file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();  // Play the sound
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println(RED + "Error playing sound: " + e.getMessage() + RESET);
        }
    }
}
