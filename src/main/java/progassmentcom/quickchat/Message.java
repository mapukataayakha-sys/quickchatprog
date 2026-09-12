package progassmentcom.quickchat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Message {
    String messageID;
    int messageNumber;
    String recipient;
    String messageContent;
    String messageHash;
    boolean messageSent;
    boolean messageReceived;
    boolean messageRead;

    public Message(String recipient, String messageContent) {
        this.recipient = recipient;
        this.messageContent = messageContent;
        this.messageNumber = 0;
        this.messageSent = false;
        this.messageReceived = false;
        this.messageRead = false;
        
        // Generate a random 10-digit ID for each message
        Random random = new Random();
        this.messageID = String.format("%010d", random.nextInt(1_000_000_000));
    }

    public boolean checkMessageID() {
        // Verify that message ID was generated as a 10-digit string
        return messageID != null && messageID.length() == 10;
    }

    public String checkRecipientCell() {
        /**
         * Validates recipient cell phone number using regex pattern.
         * Pattern: International code (+) followed by 1-3 country digits, then 9-10 local digits
         * Reference: Regular Expression Patterns for Phone Numbers
         * https://stackoverflow.com/questions/14894993/validate-phone-number-with-regex
         */
        String pattern = "^\\+\\d{1,3}\\d{9,10}$";
        if (recipient != null && recipient.matches(pattern)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String createMessageHash() {
        // Hash format: first 2 digits of ID : message number : first + last word of message
        String firstTwoDigits = (messageID != null && messageID.length() >= 2)
                ? messageID.substring(0, 2)
                : "00";
        
        String[] words = (messageContent == null || messageContent.trim().isEmpty())
                ? new String[]{"MESSAGE"}
                : messageContent.trim().split("\\s+");
        
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        messageHash = firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord;
        return messageHash;
    }

    public String checkMessageLength() {
        if (messageContent == null) {
            return "Message exceeds 250 characters by 250; please reduce the size.";
        }
        if (messageContent.length() <= 250) {
            return "Message ready to send.";
        }
        // Calculate how many characters over the limit
        int exceededBy = messageContent.length() - 250;
        return "Message exceeds 250 characters by " + exceededBy + "; please reduce the size.";
    }

    public String sentMessage(int userChoice) {
        // Return the appropriate response based on user's send choice
        switch (userChoice) {
            case 1:
                messageSent = true;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    /**
     * Stores message to JSON file using Google's Gson library.
     * JSON serialization allows messages to be persisted and retrieved later.
     * Reference: https://github.com/google/gson
     * Gson provides simple API for converting Java objects to JSON and vice versa
     */
    public void storeToJSON(String filename) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);
            
            FileWriter writer = new FileWriter(filename, true);
            writer.write(json + "\n");
            writer.close();
            
            System.out.println("Message stored to " + filename);
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public void printMessage() {
        System.out.println("Message ID: " + messageID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + messageContent);
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageNumber(int num) {
        this.messageNumber = num;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public boolean isMessageSent() {
        return messageSent;
    }

    public void setMessageReceived(boolean received) {
        this.messageReceived = received;
    }

    public void setMessageRead(boolean read) {
        this.messageRead = read;
    }

    public boolean isMessageReceived() {
        return messageReceived;
    }

    public boolean isMessageRead() {
        return messageRead;
    }
}