package com.game.game_application;

/**
 * The {@link Player} class represents a player in the game application who can send and receive messages.
 * This class implements the {@link Runnable} interface to allow running each player in a separate thread.
 *
 * @version 0.0.1
 * @author Deepak Kumar
 */
public class Player implements Runnable {
    private static final int MAX_MESSAGES = 10;
    private final String name;
    private Player otherPlayer;
    private int messageCounter = 0;

    /**
     * Constructs a new Player with the given name and the other player to communicate with.
     *
     * @param name        The name of this player.
     * @param otherPlayer The other player to communicate with.
     */
    public Player(String name, Player otherPlayer) {
        this.name = name;
        this.otherPlayer = otherPlayer;
    }

    /**
     * Sends a message to the other player. The message is concatenated with a message counter.
     * This method is synchronized to ensure thread safety.
     *
     * @param message The message to be sent.
     */
    public synchronized void sendMessage(String message) {
        if (messageCounter < MAX_MESSAGES) {
            messageCounter++;
            String newMessage = message + " " + messageCounter;
            System.out.println(name + " sent message: " + newMessage);
            otherPlayer.receiveMessage(newMessage);
        }
    }

    /**
     * Receives a message from the other player. The received message is concatenated with a message counter,
     * and then sent back to the other player.
     * This method is synchronized to ensure thread safety.
     *
     * @param message The message received from the other player.
     */
    public synchronized void receiveMessage(String message) {
        if (messageCounter < MAX_MESSAGES) {
            messageCounter++;
            String newMessage = message + " " + messageCounter;
            System.out.println(name + " received message: " + newMessage);
            otherPlayer.sendMessage(newMessage);
        }
    }

    /**
     * Runs the player thread. If this player is the initiator, it sends the first message.
     * This method is called when the thread starts.
     */
    @Override
    public void run() {
        if (name.equals("Initiator")) {
            sendMessage("Hello");
        }
    }

    /**
     * The main method to start the game application. It creates two players and starts their threads.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Player player1 = new Player("Initiator", null);
        Player player2 = new Player("Responder", player1);
        player1.otherPlayer = player2;

        new Thread(player1).start();
        new Thread(player2).start();
    }
}