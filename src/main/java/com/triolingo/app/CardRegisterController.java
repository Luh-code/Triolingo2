package com.triolingo.app;

import com.triolingo.app.cards.Card;
import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.SQLDeletion;
import com.triolingo.app.data.sql.attributes.SQLAttributeType;
import com.triolingo.app.data.sql.query.SQLSimpleQuery;
import com.triolingo.app.utils.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardRegisterController {
	@FXML
	private Button showallbtn;
	@FXML
	private Button deletebtn;
	@FXML
	private Button editbtn;
	@FXML
	private TextField searchquery;
	@FXML
	private Button searchbtn;
	@FXML
	private TableView cardtbl;

	private void updateTable(Card[] cards)
	{
		cardtbl.getItems().clear();
		for (Card c :
			cards) {
			cardtbl.getItems().add(c);
		}
	}
	
	@FXML
	private void showall()
	{
		SQLAccess access = ControllerManager.getInstance().getResource("SQLAccess", SQLAccess.class);
		access.query(new SQLSimpleQuery(new String[] {"cardId", "Word", "Translation", "Image", "CardSetId"}, "triolingo.card", ""));
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
		updateTable(cards.toArray(new Card[0]));
	}

	@FXML
	private void deleteCard()
	{
		Card c = (Card) cardtbl.getSelectionModel().getSelectedItem();
		SQLAccess access = ControllerManager.getInstance().getResource("SQLAccess", SQLAccess.class);
		access.delete(new SQLDeletion("triolingo.card", String.format("card.CardId = %d", c.getId())));
		access.delete(new SQLDeletion("triolingo.cardposition", String.format("cardposition.CardId = %d", c.getId())));
		cardtbl.getItems().remove(c);
	}

	@FXML
	private void editCard()
	{
		MainController m = ControllerManager.getInstance().getResource("Main", MainController.class);
		if (m.getContentPaneChildren().size() > 1)
		{
			m.getContentPaneChildren().remove(1);
			return;
		}
		m.loadFxml("cardCreator.fxml", false);
	}

	@FXML
	private void searchCards()
	{
		SQLAccess access = ControllerManager.getInstance().getResource("SQLAccess", SQLAccess.class);
		access.query(new SQLSimpleQuery(
			new String[] {
				"cardId", "Word", "Translation", "Image", "CardSetId"},
			"triolingo.card",
			String.format("WHERE card.Word LIKE '%s%s%s'", "%", searchquery.getText(), "%")
		));
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
		updateTable(cards.toArray(new Card[0]));
	}

	@FXML
	private void initialize()
	{
		ObservableList columns = cardtbl.getColumns();

		((TableColumn)columns.get(0)).setCellValueFactory(new PropertyValueFactory<>("id"));
		((TableColumn)columns.get(1)).setCellValueFactory(new PropertyValueFactory<>("word"));
		((TableColumn)columns.get(2)).setCellValueFactory(new PropertyValueFactory<>("translation"));
		((TableColumn)columns.get(3)).setCellValueFactory(new PropertyValueFactory<>("cardSet"));
	}
}
