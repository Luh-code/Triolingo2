package com.triolingo.app;

import com.triolingo.app.cards.Card;
import com.triolingo.app.cards.CardApp;
import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.SQLTableLayout;
import com.triolingo.app.data.sql.SQLUpdate;
import com.triolingo.app.data.sql.attributes.SQLAttributeType;
import com.triolingo.app.data.sql.query.SQLCustomQuery;
import com.triolingo.app.utils.Logger;
import com.triolingo.app.utils.SpeechSynth;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;
//import javax.speech.*;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.Engine;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

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
	private Button wordaudiobtn;
	@FXML
	private Button translationaudiobtn;
	@FXML
	private AnchorPane translationpane;
	@FXML
	private HBox backhbx;
	private SpeechSynth speechSynth =
		ControllerManager.getInstance().getResource("voiceSynthesizer", SpeechSynth.class);
	private int boxId;

	@FXML
	public void reveal()
	{
		translationpane.setVisible(true);
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
//			speechSynth.end();
			SQLAccess access = ControllerManager.getInstance().getResource("SQLAccess", SQLAccess.class);
			SQLUpdate[] updates = new SQLUpdate[cardApp.getLearntCards().size()+cardApp.getFailedCards().size()];
			BiFunction<Integer, Integer, SQLUpdate> createUpdate = (t, u) -> {
				SQLUpdate update = new SQLUpdate("triolingo", "cardposition", new Pair[] {
					new Pair("BoxId", u.toString())
				}, String.format("WHERE CardId=%d", t));
				return update;
			};
			for(int i = 0; cardApp.getLearntCards().size() > 0; i++)
			{
				updates[i] = createUpdate.apply(cardApp.getLearntCards().remove().getId(), (boxId < 5 ? boxId+1 : boxId));
			}
			for(int i = updates.length-cardApp.getFailedCards().size(); i < updates.length; i++)
			{
				updates[i] = createUpdate.apply(cardApp.getFailedCards().remove().getId(), (boxId > 1 ? boxId-1 : boxId));
			}
			for (SQLUpdate update :
				updates) {
				access.update(update);
			}

			ControllerManager.getInstance().getResource("Main", MainController.class)
				.loadFxml("allLearnt.fxml", true);
			return;
		}
		titlelbl.setText(cardApp.getWord());
		translationlbl.setText(cardApp.getTranslation());
		setImage(cardApp.getImage());
		translationpane.setVisible(false);
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
	public void wordtts()
	{
		speechSynth.getVoiceSynthesizer().speakPlainText(cardApp.getWord(), null);
	}

	@FXML
	public void translationtts()
	{
		speechSynth.getVoiceSynthesizer().speakPlainText(cardApp.getTranslation(), null);
	}

	private void setImage(String name)
	{
		Image img = new Image(String.format("com/triolingo/app/images/%s.png", name));
		imageimg.setImage(img);
	}

	public void init(int boxId)
	{
		this.boxId = boxId;
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
		mc.addIconToBtn("com/triolingo/app/icons/Audio@2x.png", wordaudiobtn, 25);
		mc.addIconToBtn("com/triolingo/app/icons/Audio@2x.png", translationaudiobtn, 25);
//		Rectangle clip = new Rectangle();
//		imageimg.fitWidthProperty().addListener(new ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				clip.widthProperty().set((Double) newValue);
//			}
//		});
//		clip.setWidth(imageimg.getFitWidth());
//		imageimg.fitHeightProperty().addListener(new ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				clip.heightProperty().set((Double) newValue);
//			}
//		});
//		clip.setHeight(imageimg.getFitHeight());
//		clip.setArcHeight(10);
//		clip.setArcWidth(10);
//		imageimg.setClip(clip);
		//speechSynth.resume();
		resetCard();
	}

	public void initialize()
	{
		ControllerManager.getInstance().registerResourceType_s(CardController.class);
		ControllerManager.getInstance().setResource("Card", this);
	}
}
