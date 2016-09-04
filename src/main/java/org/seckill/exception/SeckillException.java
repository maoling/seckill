package org.seckill.exception;

public class SeckillException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = -108695138263138524L;

	public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}