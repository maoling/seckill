package org.seckill.exception;

public class SeckillClosedException extends SeckillException{

    public SeckillClosedException(String message) {
        super(message);
    }

    public SeckillClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
