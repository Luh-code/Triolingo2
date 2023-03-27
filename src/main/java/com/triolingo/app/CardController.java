package com.triolingo.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CardController {

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
		translationlbl.setVisible(false);
		successbuttonsbox.setVisible(false);
		revealbtn.setVisible(true);
	}

	@FXML
	public void correct()
	{
		resetCard();
	}

	@FXML
	public void wrong()
	{
		resetCard();
	}
}
