package com.triolingo.app;


import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class LearningProgressController {
	@FXML
	private ProgressBar learnprogbar;

	public void setValue(double p)
	{
		learnprogbar.progressProperty().set(p);
	}

	@FXML
	private void initialize()
	{
		ControllerManager.getInstance().registerResourceType_s(LearningProgressController.class);
		ControllerManager.getInstance().setResource("ProgressBar", this);
	}
}
