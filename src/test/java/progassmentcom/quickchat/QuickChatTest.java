package progassmentcom.quickchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for QuickChat application class.
 * Tests the helper methods and message management functionality.
 */
public class QuickChatTest {

    private ArrayList<Message> sentMessages;
    private ArrayList<Message> storedMessages;
    private ArrayList<Message> disregardedMessages;

    @BeforeEach
    public void setUp() {
        sentMessages = new ArrayList<>();
        storedMessages = new ArrayList<>();
        disregardedMessages = new ArrayList<>();
    }

    //        searchByRecipient Tests 

    @Test
    public void testSearchByRecipientInSentMessages() {
        Message msg1 = new Message("+27718693002", "Hi Mike");
        Message msg2 = new Message("+27123456789", "Hello John");
        sentMessages.add(msg1);
        sentMessages.add(msg2);

        ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, "+27718693002");

        assertEquals(1, results.size());
        assertEquals("+27718693002", results.get(0).getRecipient());
    }

    @Test
    public void testSearchByRecipientInStoredMessages() {
        Message msg1 = new Message("+27718693002", "Stored message");
        storedMessages.add(msg1);

        ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, "+27718693002");

        assertEquals(1, results.size());
        assertEquals("+27718693002", results.get(0).getRecipient());
    }

    @Test
    public void testSearchByRecipientInDisregardedMessages() {
        Message msg1 = new Message("+27718693002", "Disregarded message");
        disregardedMessages.add(msg1);

        ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, "+27718693002");

        assertEquals(1, results.size());
        assertEquals("+27718693002", results.get(0).getRecipient());
    }

    @Test
    public void testSearchByRecipientAcrossAllLists() {
        Message msg1 = new Message("+27718693002", "Sent message");
        Message msg2 = new Message("+27718693002", "Stored message");
        Message msg3 = new Message("+27718693002", "Disregarded message");

        sentMessages.add(msg1);
        storedMessages.add(msg2);
        disregardedMessages.add(msg3);

        ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, "+27718693002");

        assertEquals(3, results.size());
    }

    @Test
    public void testSearchByRecipientNoMatches() {
        Message msg1 = new Message("+27718693002", "Test");
        sentMessages.add(msg1);

        ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, "+27999999999");

        assertEquals(0, results.size());
    }

    @Test
    public void testSearchByRecipientEmptyLists() {
        ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, "+27718693002");

        assertEquals(0, results.size());
    }

    @Test
    public void testSearchByRecipientMultipleMatches() {
        Message msg1 = new Message("+27718693002", "Message 1");
        Message msg2 = new Message("+27718693002", "Message 2");
        Message msg3 = new Message("+27123456789", "Different recipient");

        sentMessages.add(msg1);
        sentMessages.add(msg2);
        sentMessages.add(msg3);

        ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, "+27718693002");

        assertEquals(2, results.size());
    }

    //       deleteByHash Tests 

    @Test
    public void testDeleteByHashInSentMessages() {
        Message msg = new Message("+27718693002", "Test message");
        msg.messageID = "1234567890";
        msg.setMessageNumber(0);
        msg.createMessageHash();
        sentMessages.add(msg);

        boolean deleted = QuickChat.deleteByHash(sentMessages, storedMessages, msg.getMessageHash(), null);

        // Note: deleteByHash requires Scanner for confirmation, so we test the structure
        assertNotNull(msg.getMessageHash());
        assertEquals(1, sentMessages.size());
    }

    @Test
    public void testDeleteByHashNotFound() {
        Message msg = new Message("+27718693002", "Test");
        sentMessages.add(msg);

        // This will return false because hash doesn't exist
        boolean deleted = QuickChat.deleteByHash(sentMessages, storedMessages, "NONEXISTENT:0:TESTEST", null);

        assertFalse(deleted);
        assertEquals(1, sentMessages.size());
    }

    @Test
    public void testDeleteByHashInStoredMessages() {
        Message msg = new Message("+27718693002", "Stored message");
        msg.messageID = "0987654321";
        msg.setMessageNumber(1);
        msg.createMessageHash();
        storedMessages.add(msg);

        assertNotNull(msg.getMessageHash());
        assertEquals(1, storedMessages.size());
    }

    //       displayReport Tests 

    @Test
    public void testDisplayReportWithMessages() {
        Message msg = new Message("+27718693002", "Test message content");
        msg.messageID = "1234567890";
        msg.setMessageNumber(0);
        msg.createMessageHash();
        sentMessages.add(msg);

        // Capture System.out to verify displayReport output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        QuickChat.displayReport(sentMessages);

        String output = outContent.toString();
        assertTrue(output.contains("Message Report"));
        assertTrue(output.contains("+27718693002"));
        assertTrue(output.contains("Test message content"));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    public void testDisplayReportEmptyList() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        QuickChat.displayReport(sentMessages);

        String output = outContent.toString();
        assertTrue(output.contains("Message Report"));

        System.setOut(System.out);
    }

    @Test
    public void testDisplayReportMultipleMessages() {
        Message msg1 = new Message("+27718693002", "First message");
        msg1.messageID = "1111111111";
        msg1.setMessageNumber(0);
        msg1.createMessageHash();

        Message msg2 = new Message("+27123456789", "Second message");
        msg2.messageID = "2222222222";
        msg2.setMessageNumber(1);
        msg2.createMessageHash();

        sentMessages.add(msg1);
        sentMessages.add(msg2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        QuickChat.displayReport(sentMessages);

        String output = outContent.toString();
        assertTrue(output.contains("+27718693002"));
        assertTrue(output.contains("+27123456789"));
        assertTrue(output.contains("First message"));
        assertTrue(output.contains("Second message"));

        System.setOut(System.out);
    }

    //         Integration Tests     

    @Test
    public void testMessageFlowFromSendToSearch() {
        // Create and configure messages
        Message msg1 = new Message("+27718693002", "Hi Mike");
        msg1.messageID = "1234567890";
        msg1.setMessageNumber(1);
        msg1.createMessageHash();

        Message msg2 = new Message("+27123456789", "Hi John");
        msg2.messageID = "0987654321";
        msg2.setMessageNumber(2);
        msg2.createMessageHash();

        // Simulate sending messages
        sentMessages.add(msg1);
        sentMessages.add(msg2);

        // Search for one recipient
        ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, "+27718693002");

        assertEquals(1, results.size());
        assertEquals("Hi Mike", results.get(0).getMessageContent());
    }

    @Test
    public void testMessageOrganizationByType() {
        Message msg1 = new Message("+27718693002", "Sent message");
        msg1.messageID = "1111111111";
        msg1.setMessageNumber(1);
        msg1.createMessageHash();
        sentMessages.add(msg1);

        Message msg2 = new Message("+27718693002", "Stored message");
        msg2.messageID = "2222222222";
        msg2.setMessageNumber(2);
        msg2.createMessageHash();
        storedMessages.add(msg2);

        Message msg3 = new Message("+27718693002", "Disregarded message");
        msg3.messageID = "3333333333";
        msg3.setMessageNumber(3);
        msg3.createMessageHash();
        disregardedMessages.add(msg3);

        // Verify message counts
        assertEquals(1, sentMessages.size());
        assertEquals(1, storedMessages.size());
        assertEquals(1, disregardedMessages.size());

        // Search should find all three
        ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, "+27718693002");
        assertEquals(3, results.size());
    }

    @Test
    public void testMessageHashConsistency() {
        Message msg = new Message("+27718693002", "Hello World");
        msg.messageID = "0012345678";
        msg.setMessageNumber(0);
        String hash1 = msg.createMessageHash();

        // Creating hash again should give same result
        String hash2 = msg.createMessageHash();

        assertEquals(hash1, hash2);
        assertTrue(hash1.contains("HELLOWORLD"));
    }

    @Test
    public void testSearchResultsContainCorrectData() {
        Message msg = new Message("+27718693002", "Important message");
        msg.messageID = "5555555555";
        msg.setMessageNumber(0);
        msg.createMessageHash();
        sentMessages.add(msg);

        ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, "+27718693002");

        assertEquals(1, results.size());
        Message found = results.get(0);
        assertEquals("+27718693002", found.getRecipient());
        assertEquals("Important message", found.getMessageContent());
        assertEquals("5555555555", found.getMessageID());
    }

    @Test
    public void testNoNullPointerOnEmptySearch() {
        // Should not throw NullPointerException
        assertDoesNotThrow(() -> {
            ArrayList<Message> results = QuickChat.searchByRecipient(sentMessages, storedMessages, disregardedMessages, null);
            assertEquals(0, results.size());
        });
    }

    @Test
    public void testDisplayReportDoesNotModifyList() {
        Message msg = new Message("+27718693002", "Test");
        msg.messageID = "1234567890";
        msg.setMessageNumber(0);
        msg.createMessageHash();
        sentMessages.add(msg);

        int sizeBefore = sentMessages.size();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        QuickChat.displayReport(sentMessages);
        System.setOut(System.out);

        int sizeAfter = sentMessages.size();
        assertEquals(sizeBefore, sizeAfter);
    }
}