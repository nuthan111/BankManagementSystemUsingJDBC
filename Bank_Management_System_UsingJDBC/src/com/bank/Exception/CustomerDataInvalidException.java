package com.bank.Exception;

public class CustomerDataInvalidException extends RuntimeException {

	private String Exception;
	public CustomerDataInvalidException()
	{
		
	}

	public CustomerDataInvalidException(String exception) {
		super();
		Exception = exception;
	}

	public String getException() {
		return Exception;
	}

	public void setException(String exception) {
		Exception = exception;
	}

	@Override
	public String toString() {
		return "CustomerDataInvalidException [Exception=" + Exception + "]";
	}
	
	

	
}
