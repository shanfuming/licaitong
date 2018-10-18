package com.zqzr.licaitong.framwork;

public interface UIDataListener<T> {

	public void onDataChanged(T data);
	public void onErrorHappened(String errorMessage);
}
