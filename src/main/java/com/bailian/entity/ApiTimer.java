package com.bailian.entity;

public class ApiTimer {
	public long startTime;
	public long endTime;
	public long elsTime;

	public void start() {
		startTime = System.currentTimeMillis();
	}

	public void end() {
		endTime = System.currentTimeMillis();
	}

	public long getTime() {
		elsTime = endTime - startTime;
		return elsTime;
	}

}
