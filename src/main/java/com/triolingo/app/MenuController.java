package com.triolingo.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
