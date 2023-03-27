package com.triolingo.app.cards;

import com.triolingo.app.data.Box;
import com.triolingo.app.data.Card;
import com.triolingo.app.data.Drawer;

import java.util.*;

public class CardApp
{
	private Drawer drawer;

	private ArrayList<Card> cards = new ArrayList<>();
	private ArrayList<Card> learnt = new ArrayList<>();

	private int usedCards = 0;

	public CardApp(Drawer drawer)
	{
		this.drawer = drawer;
	}

	public void init(int cardAmt, BitSet activeBoxes)
	{
		if(cardAmt > drawer.getCardAmt(activeBoxes)) throw new ArrayIndexOutOfBoundsException("Cannot pick more cards than available!");
		ArrayList<Integer> boxIndices = new ArrayList<>();
		for (int i = activeBoxes.nextSetBit(0); i != -1; i = activeBoxes.nextSetBit(i + 1))
		{
			boxIndices.add(i);
		}
		int boxAmt = boxIndices.size();
		Random rnd = new Random();
		for (int i = 0; i < cardAmt; i++)
		{
			int boxIndex = boxIndices.get(Math.abs(rnd.nextInt())%boxAmt);
			Box b = drawer.getBox(boxIndex);
			if (b.getCardAmt() <= 0)
			{
				i--;
				continue;
			}
			Card c = b.getCard(Math.abs(rnd.nextInt())%b.getCardAmt());
			c.setBox(b);
			cards.add(c);
			b.removeCard(c);
		}
	}

	public String getWord()
	{
		return cards.get(0).getWord();
	}

	public String getTranslation()
	{
		return cards.get(0).getTranslation();
	}

	public void translationCorrect()
	{
		learnt.add(cards.remove(0));
	}

	public void translationWrong()
	{
		cards.add(cards.remove(0));
	}

	/**
	 * Judges if a string answer matches the translation
	 * @param answer user translated word
	 * @return AnswerQuality.CORRECT if there are no mistakes in the translation, AnswerQuality.CASE_ERROR if only the case is off, else AnswerQuality.WRONG is returned
	 */
	public AnswerQuality checkAnswer(String answer)
	{
		return (answer.equals(getTranslation()) ? AnswerQuality.CORRECT : (answer.equalsIgnoreCase(getTranslation()) ? AnswerQuality.CASE_ERROR : AnswerQuality.WRONG));
	}

	public void sortCardsInBoxes()
	{
		for (Card c : learnt)
		{
			c.getBox().moveCardUp(c);
		}
		for (Card c : cards)
		{
			c.getBox().moveCardDown(c);
		}
	}

	public int cardsLeft()
	{
		return cards.size() + learnt.size() - usedCards;
	}

	public Card getCurrentCard()
	{
		return cards.get(0);
	}

	public Card useCard()
	{
		usedCards++;
		return getCurrentCard();
	}
}

