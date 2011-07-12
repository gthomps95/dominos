package net.gthomps.domino.validation;

import java.util.ArrayList;

public class ValidatorException extends Exception {

	private static final long serialVersionUID = 1L;
	private ArrayList<String> messages;
	
	public ValidatorException(ArrayList<String> messages) {
		this.messages = messages;
	}
	
	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		
		for (String message : messages)
			sb.append(String.format("%s\n", message));
				
		return sb.toString();
	}

}
