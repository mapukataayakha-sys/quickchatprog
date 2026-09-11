package progassmentcom.quickchat;

import java.util.Random;

public class Message {
    String messageID;
    int messageNumber;
    String recipient;
    String messageContent;
    String messageHash;
    boolean messageSent;

    public Message(String recipient, String messageContent) {
        this.recipient = recipient;
        this.messageContent = messageContent;
        this.messageNumber = 0;
        this.messageSent = false;
        
        // Generate a random 10-digit ID for each message
        Random random = new Random();
        this.messageID = String.format("%010d", random.nextInt(1_000_000_000));
    }

    public boolean checkMessageID() {
        // Verify that message ID was generated as a 10-digit string
        return messageID != null && messageID.length() == 10;
    }

    public String checkRecipientCell() {
        // Cell number must start with + (international code) and be reasonable length
        if (recipient != null && recipient.startsWith("+") && recipient.length() <= 13) {
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
}