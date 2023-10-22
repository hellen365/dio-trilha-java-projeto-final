package com.vhdnasc.produto.domain.exception;

public class ObjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException() {
		super("Object not found.");
	}

}
