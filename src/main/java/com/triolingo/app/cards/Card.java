package com.triolingo.app.cards;

public class Card {
	private int id;
	private String word;
	private String translation;
	private int cardSet;

	public Card(int id, String word, String translation, int cardSet) {
		this.id = id;
		this.word = word;
		this.translation = translation;
		this.cardSet = cardSet;
	}

	public String getWord() {
		return word;
	}

	public String getTranslation() {
		return translation;
	}

	public int getId() {
		return id;
	}

	public int getCardSet() {
		return cardSet;
	}
}
