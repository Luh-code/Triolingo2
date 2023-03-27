package com.triolingo.app.data;

import java.util.ArrayList;

public class Box {
	private ArrayList<Card> cards = new ArrayList<>();

	private Box lowestBox;
	private Box upperBox;

	public Box()
	{

	}
	public Box(Box lowestBox, Box upperBox)
	{
		this.lowestBox = lowestBox;
		this.upperBox = upperBox;
	}

	/**
	 * Adds a card to the Box
	 * @param c card to be added
	 * @return true if card could be added
	 */
	public boolean addCard(Card c)
	{
		return cards.add(c);
	}

//	public boolean removeCard(int idx)
//	{
//		if (idx >= cards.size()) throw new ArrayIndexOutOfBoundsException("idx cannot be higher than cards.size()");
//		return cards.remove(idx) != null;
//	}

	/**
	 * Deltes Card from Box
	 * @param c target Card
	 * @return true if card could be removed
	 */
	public boolean removeCard(Card c)
	{
		return cards.remove(c);
	}

	/**
	 * Moves card c from this Box to Box b
	 * @param b target Box
	 * @param c target Card
	 * @return return true if card could be added to b
	 */
	public boolean moveCard(Box b, Card c)
	{
		if (!this.removeCard(c)) return false;
		return b.addCard(c);
	}

	public Card getCard(int idx)
	{
		if (idx >= cards.size()) throw new ArrayIndexOutOfBoundsException("Index cannot be higher than amount of cards in Box!");
		return cards.get(idx);
	}

	/**
	 * Gets the card with the next index in the cardy array
	 * @param c origin card
	 * @return next card
	 * @throws ArrayIndexOutOfBoundsException if card is not found or c == the last card in the box
	 */
	public Card getNextCard(Card c)
	{
		int idx = cards.indexOf(c);
		if (idx == -1) throw new ArrayIndexOutOfBoundsException("Card not found in cards list!");
		if (idx+1 >= cards.size()) throw new ArrayIndexOutOfBoundsException("Cannot get next card to the last card in a box!");
		return cards.get(idx+1);
	}

	/**
	 * Moves card up in next higher Box
	 * @param c target card
	 * @return true if upperBox is not null and card could be added to upperBox
	 */
	public boolean moveCardUp(Card c)
	{
		if(upperBox == null) return false;
		return moveCard(upperBox, c);
	}

	/**
	 * Moves card down in the lowest Box
	 * @param c target card
	 * @return true if lowestBox is not null and card could be added to lowestBox
	 */
	public boolean moveCardDown(Card c)
	{
		if(lowestBox != this) return false;
		return moveCard(lowestBox, c);
	}

	public Box getLowestBox() {
		return lowestBox;
	}

	public void setLowestBox(Box lowestBox) {
		this.lowestBox = lowestBox;
	}

	public Box getUpperBox() {
		return upperBox;
	}

	public void setUpperBox(Box upperBox) {
		this.upperBox = upperBox;
	}

	public boolean isHighestBox()
	{
		return upperBox == null;
	}

	public boolean isLowestBox()
	{
		return lowestBox == this;
	}

	public int getCardAmt()
	{
		return cards.size();
	}
}
