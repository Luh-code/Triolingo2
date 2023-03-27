package com.triolingo.app.data;

import java.util.BitSet;

public class Drawer {
	private Box[] boxes;

	public Drawer(Box[] boxes)
	{
		if (boxes.length == 0) throw new ArrayIndexOutOfBoundsException("At least one Box per Drawer is required!");
		this.boxes = boxes;
	}

	public int getCardAmt(BitSet activeBoxes)
	{
		int amt = 0;
		for(int i = 0; i < boxes.length; i++)
		{
			if (activeBoxes.get(i)) amt += boxes[i].getCardAmt();
		}
		return amt;
	}

	public Box getBox(int idx)
	{
		if (idx >= boxes.length || idx < 0) throw new ArrayIndexOutOfBoundsException("Unable to access element beyond array size");
		return boxes[idx];
	}
}
