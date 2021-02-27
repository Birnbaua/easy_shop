package com.birnbaua.easyshop.listener;

@FunctionalInterface
public interface DeleteListener<T> extends ChangeListener {
	void delete(T t);
}
