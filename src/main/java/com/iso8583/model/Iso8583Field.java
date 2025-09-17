package com.iso8583.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Iso8583Field {

	 	private int fieldNumber;
	    private String fieldName;
	    private String dataType;
	    private int maxLength;
	    private String value;
	    private boolean present;
	    
	    public Iso8583Field(int fieldNumber, String fieldName, String dataType, int maxLength) {
	        this.fieldNumber = fieldNumber;
	        this.fieldName = fieldName;
	        this.dataType = dataType;
	        this.maxLength = maxLength;
	        this.present = false;
	    }
}
