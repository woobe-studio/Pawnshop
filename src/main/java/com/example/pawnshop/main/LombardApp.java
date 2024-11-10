package com.example.pawnshop.main;

import com.example.pawnshop.controller.LombardController;
import com.example.pawnshop.model.LombardRepository;
import com.example.pawnshop.model.TransactionHistory;
import com.example.pawnshop.view.ConsoleView;

public class LombardApp {

    public static void main(String[] args) {
        LombardRepository repository = new LombardRepository();
        ConsoleView view = new ConsoleView();
        TransactionHistory transactionHistory = new TransactionHistory();
        LombardController controller = new LombardController(repository, view, transactionHistory);

        while (true) {
            view.displayMenu();
            int choice = view.getChoice();

            switch (choice) {
                case 1:
                    controller.addItem();
                    break;
                case 2:
                    controller.viewItems();
                    break;
                case 3:
                    controller.updateItem();
                    break;
                case 4:
                    controller.removeItem();
                    break;
                case 5:
                    controller.exit();
                    return; // Exit the program
                case 6:
                    controller.viewTransactionHistory();
                    break;
                default:
                    view.displayMessage("Invalid choice. Please try again.");
            }
        }
    }
}
