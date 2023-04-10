package com.triolingo.app;

import com.triolingo.app.cards.Card;
import com.triolingo.app.cards.CardApp;
import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.SQLTableLayout;
import com.triolingo.app.data.sql.attributes.SQLAttributeType;
import com.triolingo.app.data.sql.query.SQLCustomQuery;
import com.triolingo.app.utils.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import kotlin.Pair;
//import javax.speech.*;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

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
	private ImageView imageimg;
	@FXML
	private Button audiobtn;
	private Synthesizer voiceSynthesizer;

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
			double percentage = cardApp.getDonePercentage();
			ControllerManager.getInstance().getResource("ProgressBar", LearningProgressController.class)
				.setValue(percentage);
		}
		else Logger.logDebug("ProgressBar not registered");
		if (cardApp.getDonePercentage() >= 1.0)
		{
			ControllerManager.getInstance().getResource("Main", MainController.class)
				.loadFxml("allLearnt.fxml", true);
			return;
		}
		titlelbl.setText(cardApp.getWord());
		translationlbl.setText(cardApp.getTranslation());
		setImage(cardApp.getImage());
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

	@FXML
	public void tts()
	{

	}

	private void setImage(String name)
	{
		Image img = new Image(String.format("com/triolingo/app/images/%s.png", name));
		imageimg.setImage(img);
	}

	public void init(int boxId)
	{
		SQLAccess access = ControllerManager.getInstance().getResource("SQLAccess", SQLAccess.class);
		SQLCustomQuery query = new SQLCustomQuery(
			"SELECT c.CardId, c.Word, c.Translation, c.Image, c.CardSetId\n" +
				"FROM triolingo.card AS c\n" +
				"LEFT JOIN triolingo.cardposition AS cp ON c.CardId = cp.CardId\n" +
				String.format("WHERE cp.BoxId = %d", boxId)
		);
		access.query(query);
		access.printResultSet(access.getResultSet(), Card.getLayout(), Logger::logDebug);
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
					(String) SQLAttributeType.STRING.getObjFromResultSet(resultSet, 4),
					(int) SQLAttributeType.INT.getObjFromResultSet(resultSet, 5))
				);
			}
		} catch (SQLException e) {
			Logger.logError("ResultSet could not be accessed!");
			throw new RuntimeException(e); // return;
		}
		cardApp.init(cards.stream().toArray(Card[]::new));
		MainController mc = ControllerManager.getInstance().getResource("Main", MainController.class);
		mc.addIconToBtn("com/triolingo/app/icons/Audio@2x.png", audiobtn, 25);
		try {
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
			Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
			voiceSynthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
			voiceSynthesizer.allocate();
			voiceSynthesizer.resume();
			voiceSynthesizer.speakPlainText("Some test text!", null);
		} catch (EngineException e) {
			throw new RuntimeException(e);
		} catch (AudioException e) {
			throw new RuntimeException(e);
		}
		resetCard();
	}

	public void initialize()
	{
		ControllerManager.getInstance().registerResourceType_s(CardController.class);
		ControllerManager.getInstance().setResource("Card", this);
	}
}
