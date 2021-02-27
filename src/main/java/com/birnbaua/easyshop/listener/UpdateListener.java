package com.birnbaua.easyshop.listener;

@FunctionalInterface
public interface UpdateListener<T> extends ChangeListener {
	void update(T t);
}
