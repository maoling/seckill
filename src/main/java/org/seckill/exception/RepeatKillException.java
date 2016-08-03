package org.seckill.exception;

//运行时异常（不受检，spring会回滚）
public class RepeatKillException extends RuntimeException{
	
	public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
	
	
}
