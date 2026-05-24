package com.tafur;

import java.util.Scanner;
import java.util.Random;

public class SlotMachine {
    // learned static from my IT 168 class im taking concurrently
    private static final String[] SYMBOLS = { "Cherries", "Oranges", "Plums", "Bells", "Melons", "Bars" };
    private Random random;
    private Scanner scanner;

    public SlotMachine() {
        random = new Random();
        scanner = new Scanner(System.in);
    }

    public void play(Player player, double betAmount) {
        System.out.println("\n===== SLOT MACHINE =====");

        boolean continuePlaying = true;
        double initialBet = betAmount;
        double totalBet = 0;
        double totalWon = 0;

        while (continuePlaying) {
            if (player.getBalance() < betAmount) {
                System.out.println("Insufficient funds to continue playing with current bet amount.");
                break;
            }

            // Deduct bet amount from player's balance
            player.updateBalance(-betAmount);
            totalBet += betAmount;

            System.out.println("\nYou bet $" + betAmount);
            System.out.println("Spinning the reels...");

            // Generate random symbols
            String[] results = new String[3];
            for (int i = 0; i < 3; i++) {
                int index = random.nextInt(SYMBOLS.length);
                results[i] = SYMBOLS[index];
            }

            // Display results
            System.out.println("| " + results[0] + " | " + results[1] + " | " + results[2] + " |");

            // Calculate winnings
            double winnings = calculateWinnings(results, betAmount);

            // Update player's balance with winnings
            if (winnings > 0) {
                player.updateBalance(winnings);
                totalWon += winnings;
                System.out.println("Congratulations! You won $" + winnings);
            } else {
                System.out.println("Sorry, you didn't win anything this time.");
            }

            System.out.println("Current balance: $" + player.getBalance());

            // Ask if player wants to play again
            System.out.print("Would you like to spin again? (yes/no): ");
            String response = scanner.nextLine();
            continuePlaying = response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y");
        }

        // Display summary of slot machine session
        System.out.println("\n===== SLOT MACHINE SUMMARY =====");
        System.out.println("Total bet: $" + totalBet);
        System.out.println("Total won: $" + totalWon);
        double netProfit = totalWon - totalBet;
        if (netProfit >= 0) {
            System.out.println("Net profit: $" + netProfit);
        } else {
            System.out.println("Net loss: $" + (-netProfit));
        }
    }

    private double calculateWinnings(String[] results, double betAmount) {
        // Count occurrences of each symbol
        String symbol1 = results[0];
        String symbol2 = results[1];
        String symbol3 = results[2];

        // Check for matches
        if (symbol1.equals(symbol2) && symbol2.equals(symbol3)) {
            // All three match
            return betAmount * 3;
        } else if (symbol1.equals(symbol2) || symbol1.equals(symbol3) || symbol2.equals(symbol3)) {
            // Two match
            return betAmount * 2;
        } else {
            // No matches
            return 0;
        }
    }
}