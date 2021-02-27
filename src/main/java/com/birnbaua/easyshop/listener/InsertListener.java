package com.birnbaua.easyshop.listener;

@FunctionalInterface
public interface InsertListener<T> extends ChangeListener {
	void insert(T t);
}
