package com.triolingo.app.cards;

import com.triolingo.app.data.sql.SQLTableLayout;
import com.triolingo.app.data.sql.attributes.SQLAttributeType;
import kotlin.Pair;

public class Card {
	private int id;
	private String word;
	private String translation;
	private String image;
	private int cardSet;

	public Card(int id, String word, String translation, String image, int cardSet) {
		this.id = id;
		this.word = word;
		this.translation = translation;
		this.image = image;
		this.cardSet = cardSet;
	}

	public String getWord() {
		return word;
	}

	public String getTranslation() {
		return translation;
	}

	public String getImage() {
		return image;
	}

	public int getId() {
		return id;
	}

	public int getCardSet() {
		return cardSet;
	}

	public static SQLTableLayout getLayout() {
		return new SQLTableLayout(new Pair[]{
			new Pair(SQLAttributeType.INT, "CardId"),
			new Pair(SQLAttributeType.STRING, "Word"),
			new Pair(SQLAttributeType.STRING, "Translation"),
			new Pair(SQLAttributeType.STRING, "Image"),
			new Pair(SQLAttributeType.INT, "CardSetId")
		});
	}
}
