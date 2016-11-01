package com.bailian.kafka;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MyQueue {
	public int getSize()
	{
		return list.size();
	}
	ArrayList<String> list = new ArrayList<String>(1000);
	public boolean add(String value) {

		return list.add(value);
	}

	public boolean offer(String value) {

		return list.add(value);
	}

	/**
	 * function: 获取但不移除对象的头 为空时抛出异常
	 * 
	 * @return
	 */
	public String element() {
		if (list.size() == 0)
			throw new NoSuchElementException();
		return list.get(0);
	}

	/**
	 * function: 获取但不移除对象的头 为空时 返回null
	 * 
	 * @return
	 */
	public String peek() {
		if (list.size() == 0)
			return null;
		return list.get(0);
	}

	/**
	 * function: 获取并移除对象的头 为空时未null
	 * 
	 * @return
	 */
	public String poll() {
		if (list.size() == 0)
			return null;

		String i = list.get(0);
		list.remove(0);
		return i;
	}

	/**
	 * function:获取并移除对象的头 为空时抛出异常
	 * 
	 * @return
	 */
	public String remove() {
		if (list.size() == 0)
			throw new NoSuchElementException();

		String i = list.get(0);
		list.remove(0);
		return i;
	}
}