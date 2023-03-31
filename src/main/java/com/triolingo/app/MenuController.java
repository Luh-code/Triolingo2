package com.triolingo.app;

import com.triolingo.app.cards.Card;
import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.SQLTableLayout;
import com.triolingo.app.data.sql.attributes.SQLAttributeType;
import com.triolingo.app.data.sql.attributes.data.SQLDateTime;
import com.triolingo.app.data.sql.query.SQLCustomQuery;
import com.triolingo.app.data.sql.query.SQLSimpleQuery;
import com.triolingo.app.utils.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuController{

	@FXML
	private Button learntextbtn;
	@FXML
	private Button learnpicbtn;

	@FXML
	private Button searchtextbtn;
	@FXML
	private Button searchpicbtn;

	@FXML
	private Button createtextbtn;
	@FXML
	private Button createpicbtn;

	@FXML
	private VBox boxselector;
	@FXML
	private Button box1btn;
	@FXML
	private Button box2btn;
	@FXML
	private Button box3btn;
	@FXML
	private Button box4btn;
	@FXML
	private Button box5btn;

	@FXML
	private void loadCardRegister()
	{
		ControllerManager.getInstance().getResource("Main", MainController.class).loadFxml("cardRegister.fxml", true);
	}

	@FXML
	private void loadLearn()
	{
		ControllerManager.getInstance().getResource("Main", MainController.class).loadTrainingWidget();
	}

	@FXML
	private void loadCardCreator()
	{
		ControllerManager.getInstance().getResource("Main", MainController.class).loadCardCreator();
	}

	@FXML
	private void showBoxSelector()
	{
		if (boxselector.isVisible())
		{
			learnpicbtn.setVisible(true);
			boxselector.setVisible(false);
		}
		else
		{
			learnpicbtn.setVisible(false);
			boxselector.setVisible(true);

			SQLAccess access = ControllerManager.getInstance().getResource("SQLAccess", SQLAccess.class);
			access.query(new SQLCustomQuery(
				"SELECT cp.BoxId, b.lvl, b.LastUsed, COUNT(cp.BoxId)\n" +
					"FROM triolingo.cardposition AS cp\n" +
					"LEFT JOIN triolingo.box AS b ON cp.BoxId = b.BoxId\n" +
					"WHERE b.DrawerId = 1\n" +
					"GROUP BY cp.BoxId"
			));
			ResultSet resultSet = access.getResultSet();
			Button[] btns = new Button[] {
				box1btn,
				box2btn,
				box3btn,
				box4btn,
				box5btn
			};
			for (Button btn :
				btns) {
				btn.setStyle("-fx-text-fill: -redcol;");
			}
			try {
				while (resultSet.next())
				{
					int id = (int) SQLAttributeType.INT.getObjFromResultSet(resultSet, 1);
					int lvl = (int) SQLAttributeType.INT.getObjFromResultSet(resultSet, 2);
					Button btn = btns[lvl-1];
					int amt = (int) SQLAttributeType.INT.getObjFromResultSet(resultSet, 4);
					SQLDateTime datetime = new SQLDateTime((String) SQLAttributeType.STRING.getObjFromResultSet(resultSet, 3));
					long timediff = datetime.getDifference(new SQLDateTime());
					long DAY_IN_MILLISECONDS = 24 * 60 * 60 * 1000;
					int timemod = 2;
					int beginning = 1;
					if (timediff < DAY_IN_MILLISECONDS*Math.max(beginning, (lvl-1)*timemod))
						btn.setStyle("-fx-text-fill: -yellowcol;");
					else
						btn.setStyle("-fx-text-fill: -greencol;");
					btn.setOnAction(e -> {
						ControllerManager.getInstance().getResource("Main", MainController.class).loadTrainingWidget();
						ControllerManager.getInstance().getResource("Card", CardController.class).init(id);
					});
				}
			} catch (SQLException e) {
				Logger.logError("ResultSet could not be accessed!");
				throw new RuntimeException(e); // return;
			}
		}
	}

	private void addIconToBtn(String url, Button btn, int size)
	{
		Image img = new Image(url);
		ImageView view = new ImageView(img);
		view.setFitHeight(size);
		view.setPreserveRatio(true);
		btn.setGraphic(view);
		btn.setText("");
	}

	public void initialize()
	{
		ControllerManager.getInstance().registerResourceType_s(MenuController.class);
		ControllerManager.getInstance().setResource("Menu", this);
		addIconToBtn("com/triolingo/app/icons/Card.png", learnpicbtn, 250);
		addIconToBtn("com/triolingo/app/icons/SearchCards.png", searchpicbtn, 250);
		addIconToBtn("com/triolingo/app/icons/CreateCards.png", createpicbtn, 250);
	}
}
