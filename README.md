# QuickChat

A secure Java command-line messaging application with user authentication and message management features.

## Overview

QuickChat is a lightweight terminal-based chat application that provides users with secure registration and login functionality, along with comprehensive messaging capabilities including message creation, storage, searching, and deletion.

## Features

### User Management
- **User Registration**: Secure registration with validation for:
  - Username formatting (must contain underscore and max 5 characters)
  - Password complexity (minimum 8 characters, uppercase letter, number, and special character)
  - Cell phone number validation (international format support)
- **User Login**: Authenticate with username and password

### Messaging
- **Send Messages**: Compose and send messages to other users with recipient validation
- **Message Validation**: 
  - Recipient cell phone number format checking
  - Message length validation (maximum 250 characters)
  - Automatic message hash generation for tracking
- **View Messages**: 
  - Display recently sent messages
  - View stored messages
- **Search Messages**: Search messages by recipient
- **Delete Messages**: Remove messages by hash with confirmation
- **Message Reports**: View detailed statistics and reports on all messages

## Project Structure

```
quickchatprog/
├── src/
│   ├── main/java/progassmentcom/quickchat/
│   │   ├── Login.java          # User authentication and validation
│   │   ├── Message.java        # Message creation and management
│   │   └── QuickChat.java      # Main application and menu system
│   └── test/java/progassmentcom/quickchat/
│       ├── LoginTest.java      # Login class tests
│       ├── MessageTest.java    # Message class tests
│       └── QuickChatTest.java  # QuickChat integration tests
├── pom.xml                      # Maven configuration
└── README.md                    # This file
```

## Class Descriptions

### Login.java
Handles user authentication and validation:
- `registerUser()` - Validates and registers a new user
- `loginUser(username, password)` - Authenticates user credentials
- `checkUserName()` - Validates username format
- `checkPasswordComplexity()` - Validates password requirements
- `checkCellPhoneNumber()` - Validates international phone format

### Message.java
Manages message creation and tracking:
- `checkRecipientCell()` - Validates recipient cell phone number
- `checkMessageLength()` - Ensures message doesn't exceed 250 characters
- `createMessageHash()` - Generates unique hash for message tracking
- `printMessage()` - Displays message details
- Getters and setters for message properties

### QuickChat.java
Main application controller with menu system:
- `main(String[] args)` - Application entry point
- `runApplication()` - Main menu loop
- `sendMessages()` - Message sending workflow
- `searchByRecipient()` - Find messages by recipient
- `deleteByHash()` - Remove messages by hash
- `displayReport()` - Show message statistics

## Requirements

- Java 21 or higher
- Maven 3.6.0 or higher

## Dependencies

- JUnit Jupiter 5.10.3 (for testing)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/mapukataayakha-sys/quickchatprog.git
   cd quickchatprog
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

## Usage

### Running the Application

```bash
mvn exec:java@default
```

Or compile and run directly:
```bash
mvn clean package
java -cp target/QuickChat-1.0-SNAPSHOT.jar progassmentcom.quickchat.QuickChat
```

### Application Flow

1. **Start**: Application displays welcome message
2. **Register**: Enter username, password, and cell phone number
   - Username: Must contain underscore, max 5 characters
   - Password: Minimum 8 characters with uppercase, number, and special character
   - Phone: International format (e.g., +27838968976)
3. **Login**: Authenticate with registered credentials
4. **Menu Options**:
   - Send Messages
   - View Recently Sent Messages
   - View Stored Messages
   - Search by Recipient
   - Delete by Hash
   - View Report
   - Quit

## Testing

Run all tests:
```bash
mvn test
```

Current test coverage includes:
- User registration and login validation (17 test cases)
- Message creation and validation
- Application integration tests

Test results are available in `target/surefire-reports/`

## Validation Rules

### Username
- Must contain an underscore (`_`)
- Maximum 5 characters in length

### Password
- Minimum 8 characters
- At least one uppercase letter
- At least one number
- At least one special character

### Cell Phone Number
- Must start with international code (e.g., `+27`, `+1`, etc.)
- Validated format for international calls

### Messages
- Maximum 250 characters
- Recipient must have valid phone number format

## Message Hash

Each message is assigned a unique hash for easy tracking and deletion. The hash is generated based on:
- Message ID (10-digit random number)
- Message content
- Recipient information

## License

This project is provided as-is for educational purposes.

## Author

mapukataayakha-sys

## Contributing

For contributions, please fork the repository and submit pull requests with detailed descriptions of changes.
