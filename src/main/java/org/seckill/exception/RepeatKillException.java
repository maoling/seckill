package org.seckill.exception;

//����ʱ�쳣�����ܼ죬spring��ع���
public class RepeatKillException extends RuntimeException{
	
	public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
	
	
}
