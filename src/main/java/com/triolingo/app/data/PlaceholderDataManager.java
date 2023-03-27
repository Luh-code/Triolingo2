package com.triolingo.app.data;

public class PlaceholderDataManager {
	private Drawer testDrawer;

	public PlaceholderDataManager()
	{
		Box[] boxes = new Box[5];
		boxes[0] = new Box();
		boxes[0].addCard(new Card("why", "warum"));
		boxes[0].addCard(new Card("the tree", "Der Baum"));
		boxes[0].addCard(new Card("the razor", "Der Rasierer"));

		boxes[1] = new Box();
		boxes[1].addCard(new Card("the skyscraper", "Der Wolkenkratzer"));
		boxes[1].addCard(new Card("the snail", "Die Schnecke"));
		boxes[1].addCard(new Card("the battle axe", "Die Streitaxt"));

		boxes[2] = new Box();
		boxes[2].addCard(new Card("to sneeze", "zu niesen"));
		boxes[2].addCard(new Card("the world", "Die Welt"));
		boxes[2].addCard(new Card("the fish", "Der Fisch"));

		boxes[3] = new Box();
		boxes[3].addCard(new Card("the schadenfreude", "Die Schadenfreude"));
		boxes[3].addCard(new Card("the bottle", "Die Flasche"));
		boxes[3].addCard(new Card("to fly", "zu fliegen"));

		boxes[4] = new Box();
		boxes[4].addCard(new Card("to explode", "zu explodieren"));
		boxes[4].addCard(new Card("the explosion", "Die Explosion"));
		boxes[4].addCard(new Card("the explodee", "Der Explodierende"));

		testDrawer = new Drawer(boxes);
	}

	public Drawer getTestDrawer() {
		return testDrawer;
	}
}
