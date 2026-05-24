package com.tafur;

import java.util.Random;
import java.util.Scanner;

public class BlackJack {
    private Random random;
    private Scanner scanner;

    public BlackJack() {
        random = new Random();
        scanner = new Scanner(System.in);
    }

    public void play(Player player, double betAmount) {
        System.out.println("\n===== BLACKJACK =====");

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

            // Initialize scores
            int computerTotal = 0;
            int playerTotal = 0;

            // Computer's initial roll (hidden from player)
            int computerRoll1 = rollDice();
            int computerRoll2 = rollDice();
            computerTotal = computerRoll1 + computerRoll2;

            System.out.println("The dealer has rolled the dice (total hidden).");

            // Player's initial roll
            int playerRoll1 = rollDice();
            int playerRoll2 = rollDice();
            playerTotal = playerRoll1 + playerRoll2;

            System.out
                    .println("You rolled a " + playerRoll1 + " and a " + playerRoll2 + " (total: " + playerTotal + ")");

            // Player's turn
            boolean playerBusted = false;
            boolean playerStands = false;

            while (!playerBusted && !playerStands && playerTotal < 21) {
                System.out.print("Would you like to roll again? (yes/no): ");
                String response = scanner.nextLine().toLowerCase();

                if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
                    int roll = rollDice();
                    playerTotal += roll;
                    System.out.println("You rolled a " + roll + " (new total: " + playerTotal + ")");

                    if (playerTotal > 21) {
                        System.out.println("Bust! Your total is over 21.");
                        playerBusted = true;
                    }
                } else {
                    playerStands = true;
                }
            }

            // Reveal computer's initial roll
            System.out.println("\nDealer reveals initial roll: " + computerRoll1 + " and " + computerRoll2 + " (total: "
                    + computerTotal + ")");

            // Computer's turn (only if player didn't bust)
            if (!playerBusted) {
                // Computer tries to beat the player or get closer to 21
                while (computerTotal < 17 && computerTotal < playerTotal) {
                    int roll = rollDice();
                    computerTotal += roll;
                    System.out.println("Dealer rolls a " + roll + " (new total: " + computerTotal + ")");
                }
            }

            // Determine winner
            double winnings = determineWinner(playerTotal, computerTotal, betAmount);

            // Update player's balance with winnings
            if (winnings > 0) {
                player.updateBalance(winnings);
                totalWon += winnings;
                System.out.println("You won $" + winnings + "!");
            } else {
                System.out.println("You lost this round.");
            }

            System.out.println("Current balance: $" + player.getBalance());

            // Ask if player wants to play again
            System.out.print("Would you like to play another round? (yes/no): ");
            String response = scanner.nextLine();
            continuePlaying = response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y");
        }

        // Display summary of blackjack session
        System.out.println("\n===== BLACKJACK SUMMARY =====");
        System.out.println("Total bet: $" + totalBet);
        System.out.println("Total won: $" + totalWon);
        double netProfit = totalWon - totalBet;
        if (netProfit >= 0) {
            System.out.println("Net profit: $" + netProfit);
        } else {
            System.out.println("Net loss: $" + (-netProfit));
        }
    }

    private int rollDice() {
        return random.nextInt(7) + random.nextInt(7); // Roll two dice (1-6 each)
    }

    private double determineWinner(int playerTotal, int computerTotal, double betAmount) {
        System.out.println("\n===== ROUND RESULTS =====");
        System.out.println("Your total: " + playerTotal);
        System.out.println("Dealer's total: " + computerTotal);

        // Player busts
        if (playerTotal > 21) {
            System.out.println("You bust! Dealer wins.");
            return 0;
        }

        // Computer busts
        if (computerTotal > 21) {
            System.out.println("Dealer busts! You win!");
            return betAmount * 2; // Win double the bet
        }

        // Compare totals
        if (playerTotal > computerTotal) {
            System.out.println("You win!");
            return betAmount * 2; // Win double the bet
        } else if (playerTotal < computerTotal) {
            System.out.println("Dealer wins.");
            return 0;
        } else {
            System.out.println("It's a tie!");
            return betAmount; // Return the original bet (push)
        }
    }
}