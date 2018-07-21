package com.cg.mypaymentapp.Exception;

public class InsufficientBalanceException extends RuntimeException { 
	private static final long serialVersionUID = 9093426535721786566L;

	public InsufficientBalanceException(String msg) {
		super(msg);
	}

	public InsufficientBalanceException() {
		super();
		
	}

	public InsufficientBalanceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public InsufficientBalanceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public InsufficientBalanceException(Throwable cause) {
		super(cause);
		
	}
	
}
