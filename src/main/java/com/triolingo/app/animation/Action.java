package com.triolingo.app.animation;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;

public class Action<T extends Property>
{
	private T property;

	private Double beginning;
	private Double destination;

	private long beginningTime;
	private long duration;

	public Action(T property) {
		this.property = property;
	}

	public void init(Double destination, long duration)
	{
		this.beginning = (Double) this.property.getValue();
		this.destination = destination;
		this.beginningTime = System.nanoTime();
		this.duration = System.nanoTime()+duration;
	}

	public Action(T property, Double destination, long duration)
	{
		this.property = property;
		this.beginning = (Double) this.property.getValue();
		this.destination = destination;
		this.beginningTime = System.nanoTime();
		this.duration = System.nanoTime()+duration;
	}

	private Double linearInterpolate(Double vala, Double valb, double fac)
	{
		return vala + (Math.abs(valb-vala)*fac);
	}

	public boolean interpolate()
	{
		long temp = System.nanoTime();

		double factor = (temp-beginningTime)/(duration-beginningTime);

		double interpolatedValue = linearInterpolate(beginning, destination, factor);

		property.setValue(Math.min(destination, interpolatedValue));

		if (factor >= 1.0)
		{
			System.out.println("half point");
		}

		return duration <= temp;
	}
}
