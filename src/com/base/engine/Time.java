package com.base.engine;

public class Time
{
	private static final double SECOND = 1000000000.0;
	
	public static double getTime()
	{
		return System.nanoTime() / SECOND;
	}
}
