package com.triolingo.app;

import com.triolingo.app.utils.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DummyCardController {
	@FXML
	private Label titlelbl;
	@FXML
	private Label translationlbl;
	@FXML
	private Button revealbtn;
	@FXML
	private HBox successbuttonsbox;

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
		//titlelbl.setText(cardApp.getWord());
		//translationlbl.setText(cardApp.getTranslation());
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


	@FXML
	private void initialize()
	{
		ControllerManager.getInstance().registerResourceType_s(DummyCardController.class);
		ControllerManager.getInstance().setResource("DummyCard", this);
		successbuttonsbox.setVisible(true);
		revealbtn.setVisible(false);
		translationlbl.setVisible(true);
	}

	public String getTitlelbl() {
		return titlelbl.getText();
	}

	public void setTitlelbl(String titlelbl) {
		this.titlelbl.setText(titlelbl);
	}

	public String getTranslationlbl() {
		return translationlbl.getText();
	}

	public void setTranslationlbl(String translationlbl) {
		this.translationlbl.setText(translationlbl);
	}
}
