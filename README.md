# ISO 8583 Message Simulator

A comprehensive Spring Boot application for simulating ISO 8583 financial transaction messages with a user-friendly web interface.

## Features

- **Message Building**: Create ISO 8583 messages with common field definitions
- **Message Parsing**: Parse raw ISO 8583 messages and display field values
- **Web Interface**: Interactive web UI for message manipulation
- **Field Validation**: Proper field formatting based on ISO 8583 specifications
- **Bitmap Generation**: Automatic bitmap calculation for present fields
- **Sample Messages**: Pre-loaded sample messages for testing

## Technology Stack

- Java 8
- Spring Boot 2.7.18
- Thymeleaf (Template Engine)
- Bootstrap 5 (Frontend)
- Maven (Build Tool)

## Quick Start

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Open your browser and go to: `http://localhost:8080`
5.  https://16d54fd8b51e.ngrok-free.app -> try this url(might be down as well) :D

### Building the Application

```bash
mvn clean package
java -jar target/iso8583-simulator-1.0.0.jar
```

## Usage

### Message Builder
1. Select a Message Type Indicator (MTI)
2. Check the fields you want to include
3. Fill in the field values
4. Click "Build Message" to generate the ISO 8583 message

### Message Parser
1. Paste a raw ISO 8583 message in hex format
2. Click "Parse Message" to decode the message
3. View the parsed fields and their values

### Supported Fields

The simulator supports common ISO 8583 fields including:
- Field 2: Primary Account Number (PAN)
- Field 3: Processing Code
- Field 4: Amount Transaction
- Field 7: Transmission Date/Time
- Field 11: System Trace Audit Number
- Field 12: Time Local Transaction
- Field 13: Date Local Transaction
- And many more...

## API Endpoints

- `GET /` - Main application page
- `POST /build-message` - Build ISO 8583 message
- `POST /parse-message` - Parse ISO 8583 message
- `GET /sample-messages` - Get sample messages

## Project Structure

```
src/
├── main/
│   ├── java/com/iso8583/
│   │   ├── Iso8583SimulatorApplication.java
│   │   ├── controller/
│   │   │   └── Iso8583Controller.java
│   │   ├── model/
│   │   │   ├── Iso8583Field.java
│   │   │   └── Iso8583Message.java
│   │   ├── service/
│   │   │   ├── Iso8583FieldDefinitions.java
│   │   │   └── Iso8583MessageBuilder.java
│   │   └── config/
│   │       └── WebConfig.java
│   └── resources/
│       ├── templates/
│       │   └── index.html
│       └── application.yml
└── test/
    └── java/com/iso8583/
        └── Iso8583SimulatorApplicationTests.java
```

## Testing

Run the tests with:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This is a project made out of hobby and help of AI