package com.triolingo.app.cards;

import java.util.*;

public class CardApp {
	private Queue<Card> failedCards = new LinkedList<>();
	private Queue<Card> learntCards = new LinkedList<>();
	private Queue<Card> currentCards = new LinkedList<>();

	public void shuffle()
	{
		ArrayList<Card> l = new ArrayList<>();
		while (!currentCards.isEmpty()) {
			l.add(currentCards.remove());
		}
		Queue<Card> temp = new LinkedList<>();
		Random rnd = new Random();
		while (!l.isEmpty())
		{
			temp.add(l.remove(rnd.nextInt(l.size())));
		}
		currentCards = temp;
	}

	public void init(Card[] cards)
	{
		for (Card c :
			cards) {
			this.currentCards.add(c);
		}
		shuffle();
	}

	public String getWord()
	{
		return currentCards.peek().getWord();
	}

	public String getTranslation()
	{
		return currentCards.peek().getTranslation();
	}

	public String getImage()
	{
		return currentCards.peek().getImage();
	}

	public void wrong()
	{
		failedCards.add(currentCards.remove());
	}

	public void correct()
	{
		learntCards.add(currentCards.remove());
	}

	public double getDonePercentage()
	{
		//if (currentCards.size() == 0) return 1.0;
		double a = (double)learntCards.size()+failedCards.size();
		double b = a+currentCards.size();
		return a/b;
	}

	public AnswerQuality judgeTypedAnswer(String answer)
	{
		if (answer.equals(getTranslation())) return AnswerQuality.CORRECT;
		else if (answer.equalsIgnoreCase(getTranslation())) return  AnswerQuality.CASE_ERROR;
		return AnswerQuality.WRONG;
	}

	public Queue<Card> getFailedCards() {
		return failedCards;
	}

	public Queue<Card> getLearntCards() {
		return learntCards;
	}
}
