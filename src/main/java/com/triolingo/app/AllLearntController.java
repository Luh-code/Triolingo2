package com.triolingo.app;

import javafx.fxml.FXML;

public class AllLearntController {

	@FXML
	private void backtomenu()
	{
		ControllerManager.getInstance().getResource("Main", MainController.class)
			.loadFxml("menu.fxml", true);
	}
}
