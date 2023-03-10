package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;
import java.security.Principal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private  AccountService accountService;
    private TransferService transferService;

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else{
            accountService = new AccountService(API_BASE_URL, currentUser);
            transferService = new TransferService(API_BASE_URL, currentUser);
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                consoleService.printMessage("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {

        Account account = accountService.showBalance();
        consoleService.printMessage("Your current account balance is: " + account.getBalance());

		
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
       Transfer[] transfer = transferService.listOfTransfers();
       consoleService.printTransferHistoryMenu(transfer, currentUser);


	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

    /*
    break this method down to smaller methods
     */
	private void sendBucks() {

        User[] users = transferService.listUsers();
        int sendToAccount = -1;
        double amountToSend = 0.00;
        double currentBalance = accountService.getCurrentBalance(accountService.showBalance());
        double negativeCurrentBalance = 0.00;



        while (sendToAccount != 0 && users != null) {
                consoleService.printTransferMenu(users);
                sendToAccount = Integer.parseInt(consoleService.promptForString("Enter ID of user you are sending to (0 to cancel):\n"));
                if (sendToAccount == 0) {
                    consoleService.printMainMenu();
                } else if (sendToAccount != currentUser.getUser().getId())
                    amountToSend = consoleService.promptForDouble("Enter amount :");
                   Transfer transfer = new Transfer();
                    if(amountToSend < currentBalance && amountToSend >  negativeCurrentBalance){
                        transfer.setTransferTypeId(2);
                        transfer.setTransferStatusId(2);
                        transfer.setAccountFrom(currentUser.getUser().getId());
                        transfer.setAccountTo(sendToAccount);
                        transfer.setAmount(amountToSend);
                        transferService.createTransfer(transfer);
                        break;

                    }

             else {
                consoleService.printErrorMessage();
            }
             consoleService.pause();
        }
        consoleService.printMainMenu();
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
