package com.iso8583.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.iso8583.model.Iso8583Field;

@Component
public class Iso8583FieldDefinitions {
    
    private Map<Integer, Iso8583Field> fieldDefinitions;

    public Iso8583FieldDefinitions() {
        initializeFieldDefinitions();
    }

    private void initializeFieldDefinitions() {
        fieldDefinitions = new HashMap<>();
        
        fieldDefinitions.put(2, new Iso8583Field(2, "Primary Account Number (PAN)", "LLVAR", 19));
        fieldDefinitions.put(3, new Iso8583Field(3, "Processing Code", "NUMERIC", 6));
        fieldDefinitions.put(4, new Iso8583Field(4, "Amount Transaction", "NUMERIC", 12));
        fieldDefinitions.put(7, new Iso8583Field(7, "Transmission Date/Time", "NUMERIC", 10));
        fieldDefinitions.put(11, new Iso8583Field(11, "System Trace Audit Number", "NUMERIC", 6));
        fieldDefinitions.put(12, new Iso8583Field(12, "Time Local Transaction", "NUMERIC", 6));
        fieldDefinitions.put(13, new Iso8583Field(13, "Date Local Transaction", "NUMERIC", 4));
        fieldDefinitions.put(14, new Iso8583Field(14, "Date Expiration", "NUMERIC", 4));
        fieldDefinitions.put(18, new Iso8583Field(18, "Merchant Type", "NUMERIC", 4));
        fieldDefinitions.put(22, new Iso8583Field(22, "POS Entry Mode", "NUMERIC", 3));
        fieldDefinitions.put(25, new Iso8583Field(25, "POS Condition Code", "NUMERIC", 2));
        fieldDefinitions.put(32, new Iso8583Field(32, "Acquiring Institution ID", "LLVAR", 11));
        fieldDefinitions.put(35, new Iso8583Field(35, "Track 2 Data", "LLVAR", 37));
        fieldDefinitions.put(37, new Iso8583Field(37, "Retrieval Reference Number", "ALPHA", 12));
        fieldDefinitions.put(38, new Iso8583Field(38, "Authorization ID Response", "ALPHA", 6));
        fieldDefinitions.put(39, new Iso8583Field(39, "Response Code", "ALPHA", 2));
        fieldDefinitions.put(41, new Iso8583Field(41, "Card Acceptor Terminal ID", "ALPHA", 8));
        fieldDefinitions.put(42, new Iso8583Field(42, "Card Acceptor ID Code", "ALPHA", 15));
        fieldDefinitions.put(43, new Iso8583Field(43, "Card Acceptor Name/Location", "ALPHA", 40));
        fieldDefinitions.put(49, new Iso8583Field(49, "Currency Code Transaction", "ALPHA", 3));
        fieldDefinitions.put(52, new Iso8583Field(52, "PIN Data", "BINARY", 8));
        fieldDefinitions.put(54, new Iso8583Field(54, "Additional Amounts", "LLLVAR", 120));
        fieldDefinitions.put(64, new Iso8583Field(64, "Message Authentication Code", "BINARY", 8));
    }

    public Map<Integer, Iso8583Field> getAllFieldDefinitions() {
        return new HashMap<>(fieldDefinitions);
    }

    public Iso8583Field getFieldDefinition(int fieldNumber) {
        Iso8583Field original = fieldDefinitions.get(fieldNumber);
        if (original != null) {
            Iso8583Field copy = new Iso8583Field(original.getFieldNumber(), 
                original.getFieldName(), original.getDataType(), original.getMaxLength());
            return copy;
        }
        return null;
    }
}
