package com.iso8583;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.iso8583.model.Iso8583Field;
import com.iso8583.model.Iso8583Message;
import com.iso8583.service.Iso8583FieldDefinitions;
import com.iso8583.service.Iso8583MessageBuilder;

@SpringBootTest
class Iso8583SimulatorApplicationTests {

    @Autowired
    private Iso8583MessageBuilder messageBuilder;

    @Autowired
    private Iso8583FieldDefinitions fieldDefinitions;

    @Test
    void contextLoads() {
        assertNotNull(messageBuilder);
        assertNotNull(fieldDefinitions);
    }

    @Test
    void testMessageBuilding() {
        Map<String, String> fields = new HashMap<>();
        fields.put("2", "4111111111111111");
        fields.put("3", "000000");
        fields.put("4", "000000010000");
        fields.put("11", "123456");
        
        Iso8583Message message = messageBuilder.createMessage("0100", fields);
        
        assertNotNull(message);
        assertEquals("0100", message.getMti());
        assertNotNull(message.getBitmap());
        assertNotNull(message.getRawMessage());
        assertEquals(4, message.getFields().size());
    }

    @Test
    void testMessageParsing() {
        // Create a message first
        Map<String, String> fields = new HashMap<>();
        fields.put("2", "4111111111111111");
        fields.put("3", "000000");
        fields.put("4", "000000010000");
        fields.put("11", "123456");
        
        Iso8583Message originalMessage = messageBuilder.createMessage("0100", fields);
        
        // Parse it back
        Iso8583Message parsedMessage = messageBuilder.parseMessage(originalMessage.getRawMessage());
        
        assertNotNull(parsedMessage);
        assertEquals(originalMessage.getMti(), parsedMessage.getMti());
        assertEquals(originalMessage.getBitmap(), parsedMessage.getBitmap());
    }

    @Test
    void testFieldDefinitions() {
        Map<Integer, Iso8583Field> definitions = fieldDefinitions.getAllFieldDefinitions();
        assertNotNull(definitions);
        assertTrue(definitions.size() > 0);
        
        Iso8583Field field2 = fieldDefinitions.getFieldDefinition(2);
        assertNotNull(field2);
        assertEquals("Primary Account Number (PAN)", field2.getFieldName());
        assertEquals("LLVAR", field2.getDataType());
    }
}

