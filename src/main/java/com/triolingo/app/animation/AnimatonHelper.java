package com.triolingo.app.animation;

import javafx.beans.property.Property;
import javafx.util.Pair;

import java.util.*;

public class AnimatonHelper extends Thread
{
	private Map<Double, Action> actions = new HashMap<>();
	private double newPercent;
	private long duration;

	public AnimatonHelper(long duration)
	{
		this.duration = duration;
	}

	public AnimatonHelper percent(double percent)
	{
		newPercent = percent;
		return this;
	}

	public <T extends Property> AnimatonHelper action(T property, double destination)
	{
		actions.put(newPercent, new Action(property, destination, (long) (duration*newPercent)));
		return this;
	}

	@Override
	public void run() {
		System.out.printf("animation dispatched! (length %dns)\n", duration);
		SortedSet<Double> keys = new TreeSet<>(actions.keySet());
		for (Double key : keys)
		{
			Action a = actions.get(key);
			while (!a.interpolate());
			actions.remove(key);
		}
		System.out.println("animation finished!");
	}
}
