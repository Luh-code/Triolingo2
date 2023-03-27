package com.triolingo.app.data;

public class Card {
	private String word;
	private String translation;

	private Box box;

	public Card(String word, String translation) {
		this.word = word;
		this.translation = translation;
	}

	public String getWord() {
		return word;
	}

	public String getTranslation() {
		return translation;
	}

	public Box getBox()
	{
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}
}
