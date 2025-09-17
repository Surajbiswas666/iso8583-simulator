package com.iso8583.service;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iso8583.model.Iso8583Field;
import com.iso8583.model.Iso8583Message;

@Service
public class Iso8583MessageBuilder {

    @Autowired
    private Iso8583FieldDefinitions fieldDefinitions;

    public Iso8583Message createMessage(String mti, Map<String, String> fieldValues) {
        Iso8583Message message = new Iso8583Message(mti);
        
        for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
            try {
                int fieldNumber = Integer.parseInt(entry.getKey());
                String value = entry.getValue();
                
                if (value != null && !value.trim().isEmpty()) {
                    Iso8583Field fieldDef = fieldDefinitions.getFieldDefinition(fieldNumber);
                    if (fieldDef != null) {
                        fieldDef.setValue(value);
                        message.addField(fieldDef);
                    }
                }
            } catch (NumberFormatException e) {
                // Skip invalid field numbers
            }
        }
        
        message.setBitmap(generateBitmap(message.getFields()));
        message.setRawMessage(generateRawMessage(message));
        
        return message;
    }

    private String generateBitmap(Map<Integer, Iso8583Field> fields) {
        // Create a 64-bit bitmap (primary bitmap)
        BigInteger bitmap = BigInteger.ZERO;
        
        for (Integer fieldNumber : fields.keySet()) {
            if (fieldNumber >= 1 && fieldNumber <= 64) {
                // Set bit position (64 - fieldNumber) to 1
                bitmap = bitmap.setBit(64 - fieldNumber);
            }
        }
        
        // Convert to hex string with leading zeros
        String hexBitmap = String.format("%016X", bitmap);
        return hexBitmap;
    }

    private String generateRawMessage(Iso8583Message message) {
        StringBuilder rawMessage = new StringBuilder();
        
        // Add MTI
        rawMessage.append(message.getMti());
        
        // Add Bitmap
        rawMessage.append(message.getBitmap());
        
        // Add fields in order
        Map<Integer, Iso8583Field> sortedFields = new TreeMap<>(message.getFields());
        for (Iso8583Field field : sortedFields.values()) {
            rawMessage.append(formatField(field));
        }
        
        return rawMessage.toString();
    }

    private String formatField(Iso8583Field field) {
        String value = field.getValue();
        String dataType = field.getDataType();
        
        if (dataType.equals("LLVAR")) {
            return String.format("%02d%s", value.length(), value);
        } else if (dataType.equals("LLLVAR")) {
            return String.format("%03d%s", value.length(), value);
        } else if (dataType.equals("NUMERIC")) {
            // Pad with leading zeros if necessary
            return String.format("%" + field.getMaxLength() + "s", value).replace(' ', '0');
        } else {
            // ALPHA or BINARY - pad with spaces if necessary
            return String.format("%-" + field.getMaxLength() + "s", value);
        }
    }

    public Iso8583Message parseMessage(String rawMessage) {
        if (rawMessage.length() < 20) { // MTI (4) + Bitmap (16)
            throw new IllegalArgumentException("Invalid message length");
        }

        Iso8583Message message = new Iso8583Message();
        
        // Extract MTI
        message.setMti(rawMessage.substring(0, 4));
        
        // Extract Bitmap
        String bitmapHex = rawMessage.substring(4, 20);
        message.setBitmap(bitmapHex);
        
        // Parse bitmap to determine which fields are present
        BigInteger bitmap = new BigInteger(bitmapHex, 16);
        int position = 20; // Start after MTI and bitmap
        
        for (int fieldNum = 1; fieldNum <= 64; fieldNum++) {
            if (bitmap.testBit(64 - fieldNum)) {
                Iso8583Field fieldDef = fieldDefinitions.getFieldDefinition(fieldNum);
                if (fieldDef != null && position < rawMessage.length()) {
                    try {
                        position = parseField(rawMessage, position, fieldDef);
                        message.addField(fieldDef);
                    } catch (Exception e) {
                        // Skip problematic fields
                        break;
                    }
                }
            }
        }
        
        message.setRawMessage(rawMessage);
        return message;
    }

    private int parseField(String rawMessage, int startPos, Iso8583Field field) {
        String dataType = field.getDataType();
        String value;
        int nextPos = startPos;
        
        if (dataType.equals("LLVAR")) {
            if (startPos + 2 > rawMessage.length()) return rawMessage.length();
            int length = Integer.parseInt(rawMessage.substring(startPos, startPos + 2));
            nextPos = startPos + 2 + length;
            if (nextPos > rawMessage.length()) return rawMessage.length();
            value = rawMessage.substring(startPos + 2, nextPos);
        } else if (dataType.equals("LLLVAR")) {
            if (startPos + 3 > rawMessage.length()) return rawMessage.length();
            int length = Integer.parseInt(rawMessage.substring(startPos, startPos + 3));
            nextPos = startPos + 3 + length;
            if (nextPos > rawMessage.length()) return rawMessage.length();
            value = rawMessage.substring(startPos + 3, nextPos);
        } else {
            // Fixed length field
            nextPos = startPos + field.getMaxLength();
            if (nextPos > rawMessage.length()) return rawMessage.length();
            value = rawMessage.substring(startPos, nextPos).trim();
        }
        
        field.setValue(value);
        return nextPos;
    }
}