package com.nali.spreader.factory;

public interface TaskProduceLine<T> {
	void send(T data);
}
