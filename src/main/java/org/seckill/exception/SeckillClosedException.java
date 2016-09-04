package org.seckill.exception;

public class SeckillClosedException extends SeckillException{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3555440775951503668L;

	public SeckillClosedException(String message) {
        super(message);
    }

    public SeckillClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
