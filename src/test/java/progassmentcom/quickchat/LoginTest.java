package progassmentcom.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for Login class.
 * Tests the refactored Login implementation with constructor-based initialization.
 */
public class LoginTest {

    //          assertEquals Tests 

    @Test
    public void testUsernameCorrectlyFormatted() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.setFirstName("Kyle");
        login.setLastName("Smith");

        String result = login.registerUser();
        assertEquals("User successfully registered.", result);
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login login = new Login("kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976");

        String result = login.registerUser();
        assertEquals("Username is not correctly formatted; please ensure that your username "
                   + "contains an underscore and is no more than five characters in length.", result);
    }

    @Test
    public void testPasswordMeetsComplexity() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.setFirstName("Kyle");
        login.setLastName("Smith");

        String result = login.registerUser();
        assertEquals("User successfully registered.", result);

        // Direct password check
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Login login = new Login("kyl_1", "password", "+27838968976");

        String result = login.registerUser();
        assertEquals("Password is not correctly formatted; please ensure that the password "
                   + "contains at least eight characters, a capital letter, a number, and a special character.", result);
    }

    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.setFirstName("Kyle");
        login.setLastName("Smith");

        String result = login.registerUser();
        assertEquals("User successfully registered.", result);
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "08966553");

        String result = login.registerUser();
        assertEquals("Cell phone number incorrectly formatted or does not contain international code.", result);
    }

    //         assertTrue / assertFalse Tests 

    @Test
    public void testCheckUserNameTrue() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testCheckUserNameFalse() {
        Login login = new Login("kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.checkUserName());
    }

    @Test
    public void testCheckPasswordComplexityTrue() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordComplexityFalse() {
        Login login = new Login("kyl_1", "password", "+27838968976");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneNumberTrue() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneNumberFalse() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    //       Login and Status Tests 

    @Test
    public void testLoginSuccessful() {
        // Create a registered user
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.registerUser();

        // Attempt login with correct credentials
        boolean loginSuccess = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(loginSuccess);
    }

    @Test
    public void testLoginFailed() {
        // Create a user with credentials
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        // Attempt login with wrong credentials
        boolean loginSuccess = login.loginUser("wrong", "wrong");
        assertFalse(loginSuccess);
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.setFirstName("Kyle");
        login.setLastName("Smith");
        login.registerUser();

        // Attempt successful login
        boolean loginSuccess = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        String status = login.returnLoginStatus(loginSuccess);
        
        assertEquals("Welcome Kyle, Smith it is great to see you again.", status);
    }

    @Test
    public void testReturnLoginStatusSuccessFirstNameOnly() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.setFirstName("Kyle");
        login.registerUser();

        // Attempt successful login
        boolean loginSuccess = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        String status = login.returnLoginStatus(loginSuccess);
        
        assertEquals("Welcome Kyle, it is great to see you again.", status);
    }

    @Test
    public void testReturnLoginStatusFailure() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.setFirstName("Kyle");
        login.setLastName("Smith");

        // Attempt failed login
        boolean loginSuccess = login.loginUser("wrong", "wrong");
        String status = login.returnLoginStatus(loginSuccess);
        
        assertEquals("Username or password incorrect, please try again.", status);
    }

    @Test
    public void testGettersWork() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        assertEquals("kyl_1", login.getUsername());
        assertEquals("Ch&&sec@ke99!", login.getPassword());
        assertEquals("+27838968976", login.getCellPhoneNumber());
    }
}