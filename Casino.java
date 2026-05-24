package com.tafur;

import java.util.Scanner;

public class Casino {
    private Player player;
    private Scanner scanner;
    private SlotMachine slotMachine;
    private BlackJack blackjack;

    public Casino() {
        scanner = new Scanner(System.in);
        slotMachine = new SlotMachine();
        blackjack = new BlackJack();
    }

    public void start() {
        System.out.println("Welcome to the Casino Game System!");

        // Get player name
        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();
        player = new Player(name);

        // Display welcome message
        System.out.println("\nWelcome, " + player.getName() + "!");

        // Initial deposit
        makeInitialDeposit();

        boolean continuePlaying = true;
        while (continuePlaying) {
            displayGameMenu();
            int gameChoice = getGameChoice();
            double betAmount = getBetAmount();

            // Check if player has enough money
            if (betAmount > player.getBalance()) {
                System.out.println("Insufficient funds! Your balance is $" + player.getBalance());
                if (promptForAdditionalDeposit()) {
                    makeAdditionalDeposit();
                    continue;
                } else {
                    System.out.println("You chose not to add more funds.");
                    continue;
                }
            }

            // Play the selected game
            switch (gameChoice) {
                case 1:
                    slotMachine.play(player, betAmount);
                    break;
                case 2:
                    blackjack.play(player, betAmount);
                    break;
            }

            // Display account information
            displayAccountSummary();

            // Ask to continue playing
            continuePlaying = promptToContinue();
        }

        // Final summary
        System.out.println("\n===== GAME OVER =====");
        System.out.println("Thank you for playing, " + player.getName() + "!");
        System.out.println("Total deposited: $" + player.getTotalDeposited());
        double netProfit = player.getBalance() - player.getTotalDeposited();
        if (netProfit >= 0) {
            System.out.println("Total winnings: $" + netProfit);
        } else {
            System.out.println("Total losses: $" + (-netProfit));
        }
        System.out.println("Final balance: $" + player.getBalance());
        scanner.close();
    }

    private void makeInitialDeposit() {
        double deposit = 0;
        boolean validDeposit = false;

        while (!validDeposit) {
            System.out.println("Please make an initial deposit (minimum $50, maximum $1,500): ");
            try {
                deposit = Double.parseDouble(scanner.nextLine());

                if (deposit >= 50 && deposit <= 1500) {
                    validDeposit = true;
                    player.deposit(deposit);
                    System.out.println(
                            "Initial deposit of $" + deposit + " accepted. Your balance is $" + player.getBalance());
                } else {
                    System.out.println("Please enter an amount between $50 and $1,500.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private void makeAdditionalDeposit() {
        double deposit = 0;
        boolean validDeposit = false;

        while (!validDeposit) {
            System.out.println("Enter deposit amount (maximum $1,500): ");
            try {
                deposit = Double.parseDouble(scanner.nextLine());

                if (deposit > 0 && deposit <= 1500) {
                    validDeposit = true;
                    player.deposit(deposit);
                    System.out.println(
                            "Deposit of $" + deposit + " accepted. Your new balance is $" + player.getBalance());
                } else {
                    System.out.println("Please enter an amount between $1 and $1,500.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }

    private void displayGameMenu() {
        System.out.println("\n===== GAME MENU =====");
        System.out.println("1: Slot Machine");
        System.out.println("2: Blackjack");
        System.out.println("Current balance: $" + player.getBalance());
    }

    private int getGameChoice() {
        int choice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            System.out.print("Enter your game choice (1 or 2): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1 || choice == 2) {
                    validChoice = true;
                } else {
                    System.out.println("Please enter 1 for Slot Machine or 2 for Blackjack.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice.");
            }
        }

        return choice;
    }

    private double getBetAmount() {
        double bet = 0;
        boolean validBet = false;

        while (!validBet) {
            System.out.print("Enter your bet amount: $");
            try {
                bet = Double.parseDouble(scanner.nextLine());

                if (bet > 0) {
                    validBet = true;
                } else {
                    System.out.println("Bet amount must be greater than $0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }

        return bet;
    }

    private boolean promptForAdditionalDeposit() {
        System.out.print("Would you like to make an additional deposit? (yes/no): ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y");
    }

    private boolean promptToContinue() {
        System.out.print("Do you want to continue playing? (yes/no): ");
        String response = scanner.nextLine();
        return response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y");
    }

    private void displayAccountSummary() {
        System.out.println("\n===== ACCOUNT SUMMARY =====");
        System.out.println("Total deposited: $" + player.getTotalDeposited());
        double netProfit = player.getBalance() - player.getTotalDeposited();
        if (netProfit >= 0) {
            System.out.println("Total winnings: $" + netProfit);
        } else {
            System.out.println("Total losses: $" + (-netProfit));
        }
        System.out.println("Current balance: $" + player.getBalance());
    }
}
