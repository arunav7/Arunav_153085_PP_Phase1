package com.cg.mypaymentapp.Exception;

public class ZeroBalanceException  extends RuntimeException {
	private static final long serialVersionUID = 3177319520475094046L;

	public ZeroBalanceException() {
		super();
		
	}

	public ZeroBalanceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public ZeroBalanceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ZeroBalanceException(String message) {
		super(message);
		
	}

	public ZeroBalanceException(Throwable cause) {
		super(cause);
		
	}
}
