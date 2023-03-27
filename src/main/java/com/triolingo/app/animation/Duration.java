package com.triolingo.app.animation;

public class Duration
{
	public static long nanos(long d)
	{
		return d;
	}
	public static long micros(long d)
	{
		return nanos(d)*1000;
	}
	public static long millis(long d)
	{
		return micros(d)*1000;
	}
	public static long centis(long d)
	{
		return millis(d)*10;
	}
	public static long decis(long d)
	{
		return centis(d)*10;
	}
	public static long seconds(long d)
	{
		return decis(d)*10;
	}
}
