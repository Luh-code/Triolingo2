package com.triolingo.app;

import com.triolingo.app.cards.Card;
import com.triolingo.app.cards.CardApp;
import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.attributes.SQLAttributeType;
import com.triolingo.app.data.sql.query.SQLSimpleQuery;
import com.triolingo.app.utils.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardController {
	private CardApp cardApp;

	@FXML
	private Label titlelbl;

	@FXML
	private Label translationlbl;

	@FXML
	private	Button revealbtn;

	@FXML
	private HBox successbuttonsbox;

	@FXML
	private Button correctbtn;

	@FXML
	private Button wrongbtn;

	@FXML
	public void reveal()
	{
		translationlbl.setVisible(true);
		successbuttonsbox.setVisible(true);
		revealbtn.setVisible(false);
	}

	@FXML
	public void resetCard()
	{
		if (ControllerManager.getInstance().isResourceRegistered(LearningProgressController.class)) {
			double percentage = cardApp.getLearntPercentage();
			ControllerManager.getInstance().getResource("ProgressBar", LearningProgressController.class)
				.setValue(percentage);
		}
		else Logger.logDebug("LearningProgressBar not registered");
		if (cardApp.getLearntPercentage() >= 1.0)
		{
			ControllerManager.getInstance().getResource("Main", MainController.class)
				.loadFxml("allLearnt.fxml", true);
			return;
		}
		titlelbl.setText(cardApp.getWord());
		translationlbl.setText(cardApp.getTranslation());
		translationlbl.setVisible(false);
		successbuttonsbox.setVisible(false);
		revealbtn.setVisible(true);
	}

	@FXML
	public void correct()
	{
		cardApp.correct();
		resetCard();
	}

	@FXML
	public void wrong()
	{
		cardApp.wrong();
		resetCard();
	}

	public void initialize()
	{
		ControllerManager.getInstance().registerResourceType_s(CardController.class);
		ControllerManager.getInstance().setResource("Card", this);
		SQLAccess access = ControllerManager.getInstance().getResource("SQLAccess", SQLAccess.class);
		access.query(new SQLSimpleQuery(new String[] {"Word", "Translation"}, "triolingo.card", ""));
		cardApp = new CardApp();
		ArrayList<Card> cards = new ArrayList<>();
		ResultSet resultSet = access.getResultSet();
		try {
			while (resultSet.next())
			{
				cards.add(new Card(
					(String) SQLAttributeType.STRING.getObjFromResultSet(resultSet, 1),
					(String) SQLAttributeType.STRING.getObjFromResultSet(resultSet, 2))
				);
			}
		} catch (SQLException e) {
			Logger.logError("ResultSet could not be accessed!");
			throw new RuntimeException(e); // return;
		}
		cardApp.init(cards.stream().toArray(Card[]::new));
		resetCard();
	}
}
