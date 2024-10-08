package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: Send TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }



    public double promptForDouble(String prompt) {
        System.out.println(prompt);
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }


    public void printTransferMenu(User[] users, AuthenticatedUser currentUser) {
        System.out.println("-------------------------------------------");
        System.out.println("Users ");
        System.out.println("ID               Name");
        System.out.println("-------------------------------------------");
        for (User user : users) {
           if (user.getId() != currentUser.getUser().getId())
                System.out.println(user.getId() + "             " + user.getUsername());

        }
        System.out.println("---------");

    }

    public void printTransferHistoryMenu(Transfer[] transfers, AuthenticatedUser currentUser) {
        String fromUser = "";
        String toUser = "";
        System.out.println("-----------------------------------------------------");
        System.out.println("Transfers ");
        System.out.println("ID               From/To             Amount");
        System.out.println("-----------------------------------------------------");
        for (Transfer transfer : transfers) {
            if (currentUser.getUser().getId() != transfer.getUserFromId()) {
                fromUser = transfer.getUsernameFrom();
                System.out.println(transfer.getId() + "            From: " + fromUser + "            $ " + transfer.getAmount());
            } else if (currentUser.getUser().getId() != transfer.getUserToId()) {
                toUser = transfer.getUsernameTo();
                System.out.println(transfer.getId() + "            To:  " + toUser + "             $ " + transfer.getAmount());
            }

        }
        System.out.println("---------");
    }

    public void printTransferDetailsMenu(Transfer[] transfers, int id) {
        String statusId = "";
        String typeId = "";

        System.out.println("-----------------------------------------------------");
        System.out.println("Transfers Details ");
        System.out.println("-----------------------------------------------------");
        for (Transfer transfer : transfers) {
            if (id == transfer.getId() && id != 0) {
                System.out.println("ID:  " + transfer.getId());
                System.out.println("From:  " + transfer.getUsernameFrom());
                System.out.println("To:     " + transfer.getUsernameTo());
                if (transfer.getTransferTypeId() == 2) {
                    typeId = "Send";
                }
                System.out.println("Type:   " + typeId);
                if (transfer.getTransferStatusId() == 2) {
                    statusId = "Approved";
                }
                System.out.println("Status:  " + statusId);
                System.out.println("Amount:   $" + transfer.getAmount());

            }
        }
    }


    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

}
