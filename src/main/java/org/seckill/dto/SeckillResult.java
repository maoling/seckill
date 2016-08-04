package org.seckill.dto;

//·â×°jsonÊý¾Ý
public class SeckillResult<T>{
	private boolean success;
	
	public SeckillResult(boolean success, T data) {
		this.success = success;
		this.data = data;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	private T data;
	private String error;
	
	public SeckillResult(boolean success, String error) {
		this.success = success;
		this.error = error;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	
}
