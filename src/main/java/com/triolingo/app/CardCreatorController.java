package com.triolingo.app;

import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.SQLInfo;
import com.triolingo.app.data.sql.SQLInsertion;
import com.triolingo.app.data.sql.SQLUpdate;
import com.triolingo.app.data.sql.attributes.SQLAttributeBase;
import com.triolingo.app.data.sql.attributes.SQLDefaultAttribute;
import com.triolingo.app.data.sql.attributes.SQLIntAttribute;
import com.triolingo.app.data.sql.attributes.SQLStringAttribute;
import com.triolingo.app.data.sql.query.SQLCustomQuery;
import com.triolingo.app.data.sql.query.SQLSimpleQuery;
import com.triolingo.app.utils.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import org.w3c.dom.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardCreatorController {
	@FXML
	private Spinner idspn;
	@FXML
	private TextField wordtxt;
	@FXML
	private TextField translationtxt;
	@FXML
	private TextField imgnametxt;
	@FXML
	private Spinner setidspn;
	@FXML
	private Button addbtn;

	private boolean useDummy = true;
	private String dummyName;

	public void setUseDummy(boolean useDummy, String dummyName)
	{
		this.useDummy = useDummy;
		this.dummyName = dummyName;
	}

	@FXML
	private void updateDummy()
	{
		if (!useDummy) return;
		DummyCardController dummy = ControllerManager.getInstance().getResource(dummyName, DummyCardController.class);
		dummy.setTitlelbl(wordtxt.getText());
		dummy.setTranslationlbl(translationtxt.getText());
	}

	public void setValues(int cardId, String word, String translation, String image, int cardSetId)
	{
		wordtxt.setText(word);
		translationtxt.setText(translation);
		imgnametxt.setText(image);
	}

	@FXML
	private void addCard()
	{
		SQLAccess access = ControllerManager.getInstance().getResource("SQLAccess", SQLAccess.class);
		SQLInfo info = ControllerManager.getInstance().getResource("SQLInfo", SQLInfo.class);
		ResultSet rs = access.getResultSet();
		SQLAttributeBase[] attributes = new SQLAttributeBase[]{
			new SQLDefaultAttribute(),
			new SQLStringAttribute(info, wordtxt.getText()),
			new SQLStringAttribute(info, translationtxt.getText()),
			new SQLStringAttribute(info, imgnametxt.getText()),
			new SQLIntAttribute(0)
		};
		boolean edit = false;
		access.query(new SQLCustomQuery("SELECT card.CardId " +
			"FROM triolingo.card " +
			"WHERE card.Word = 'the apple'"));
//		access.query(new SQLSimpleQuery(new String[] {"card.CardId"}, "triolingo.card",
//			String.format("WHERE card.Word = '%s'", wordtxt.getText())));
		rs = access.getResultSet();
		int cardId = 0;
		try {
			if (rs.next())
			{
				edit = true;
				cardId = rs.getInt(1);
			}
		} catch (SQLException e) {
			Logger.logError("ResultSet could not be accessed!");
			throw new RuntimeException(e); // return;
		}
		if (!edit)
		{
			access.insert(new SQLInsertion("triolingo", "card", attributes));
			access.query(new SQLSimpleQuery(new String[] {"card.CardId"}, "triolingo.card", "ORDER BY card.CardId DESC"));
			rs = access.getResultSet();
			try {
				rs.next();
				cardId = rs.getInt(1);
			} catch (SQLException e) {
				Logger.logError("ResultSet could not be accessed!");
				throw new RuntimeException(e); // return;
			}
			access.insert(new SQLInsertion("triolingo", "cardposition", new SQLAttributeBase[]{
				new SQLIntAttribute(cardId),
				new SQLIntAttribute(1)
			}));
		}
		else
		{
			access.update(new SQLUpdate("triolingo", "card", new Pair[]{
				new Pair("card.Translation", String.format("'%s'", translationtxt.getText())),
				new Pair("card.Image", String.format("'%s'", imgnametxt.getText())),
			}, String.format("WHERE card.CardId=%d", cardId)));
		}
		wordtxt.setText("");
		translationtxt.setText("");
		imgnametxt.setText("");
	}

	@FXML
	private void initialize()
	{
		ControllerManager.getInstance().registerResourceType_s(CardCreatorController.class);
		ControllerManager.getInstance().setResource("CardCreator", this);
	}
}
