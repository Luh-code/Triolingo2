package com.triolingo.app;

import com.triolingo.app.cards.Card;
import com.triolingo.app.cards.CardApp;
import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.SQLTableLayout;
import com.triolingo.app.data.sql.attributes.SQLAttributeType;
import com.triolingo.app.data.sql.query.SQLCustomQuery;
import com.triolingo.app.data.sql.query.SQLSimpleQuery;
import com.triolingo.app.utils.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import kotlin.Pair;

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

	public void init(int boxId)
	{
		SQLAccess access = ControllerManager.getInstance().getResource("SQLAccess", SQLAccess.class);
		SQLCustomQuery query = new SQLCustomQuery(
			"SELECT c.CardId, c.Word, c.Translation, c.CardSetId\n" +
				"FROM triolingo.card AS c\n" +
				"LEFT JOIN triolingo.cardposition AS cp ON c.CardId = cp.CardId\n" +
				String.format("WHERE cp.BoxId = %d", boxId)
		);
		access.query(query);
		access.printResultSet(access.getResultSet(), new SQLTableLayout(new Pair[]{
			new Pair(SQLAttributeType.INT, "CardId"),
			new Pair(SQLAttributeType.STRING, "Word"),
			new Pair(SQLAttributeType.STRING, "Translation"),
			new Pair(SQLAttributeType.INT, "CardSetId")
		}));
		access.query(query);
		cardApp = new CardApp();
		ArrayList<Card> cards = new ArrayList<>();
		ResultSet resultSet = access.getResultSet();
		try {
			while (resultSet.next())
			{
				cards.add(new Card(
					(int) SQLAttributeType.INT.getObjFromResultSet(resultSet, 1),
					(String) SQLAttributeType.STRING.getObjFromResultSet(resultSet, 2),
					(String) SQLAttributeType.STRING.getObjFromResultSet(resultSet, 3),
					(int) SQLAttributeType.INT.getObjFromResultSet(resultSet, 4))
				);
			}
		} catch (SQLException e) {
			Logger.logError("ResultSet could not be accessed!");
			throw new RuntimeException(e); // return;
		}
		cardApp.init(cards.stream().toArray(Card[]::new));
		resetCard();
	}

	public void initialize()
	{
		ControllerManager.getInstance().registerResourceType_s(CardController.class);
		ControllerManager.getInstance().setResource("Card", this);
	}
}
