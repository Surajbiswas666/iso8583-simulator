package com.iso8583.model;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Iso8583Message {
    private String mti;
    private String bitmap;
    private Map<Integer, Iso8583Field> fields = new HashMap<>();
    private String rawMessage;

    public Iso8583Message(String mti) {
        this.mti = mti;
        this.fields = new HashMap<>();
    }
    public void addField(Iso8583Field field) {
        field.setPresent(true);
        this.fields.put(field.getFieldNumber(), field);
    }

    public Iso8583Field getField(int fieldNumber) {
        return this.fields.get(fieldNumber);
    }
}