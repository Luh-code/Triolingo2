package com.triolingo.app;

import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.SQLInfo;
import com.triolingo.app.data.sql.SQLInsertion;
import com.triolingo.app.data.sql.attributes.SQLAttributeBase;
import com.triolingo.app.data.sql.attributes.SQLDefaultAttribute;
import com.triolingo.app.data.sql.attributes.SQLIntAttribute;
import com.triolingo.app.data.sql.attributes.SQLStringAttribute;
import com.triolingo.app.data.sql.query.SQLSimpleQuery;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
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

	@FXML
	private void addCard()
	{
		SQLAccess access = ControllerManager.getInstance().getResource("SQLAccess", SQLAccess.class);
		SQLInfo info = ControllerManager.getInstance().getResource("SQLInfo", SQLInfo.class);
		access.insert(new SQLInsertion("triolingo", "card", new SQLAttributeBase[]{
			new SQLDefaultAttribute(),
			new SQLStringAttribute(info, wordtxt.getText()),
			new SQLStringAttribute(info, translationtxt.getText()),
			new SQLIntAttribute(0)
		}));
		access.query(new SQLSimpleQuery(new String[] {"card.CardId"}, "triolingo.card", "ORDER BY card.CardId DESC"));
		ResultSet rs = access.getResultSet();
		int cardId;
		try {
			rs.next();
			cardId = rs.getInt(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		access.insert(new SQLInsertion("triolingo", "cardposition", new SQLAttributeBase[]{
			new SQLIntAttribute(cardId),
			new SQLIntAttribute(1)
		}));
	}

	@FXML
	private void initialize()
	{
		ControllerManager.getInstance().registerResourceType_s(CardCreatorController.class);
		ControllerManager.getInstance().setResource("CardCreator", this);
	}
}
