package com.triolingo.app;

import com.triolingo.app.data.Box;
import com.triolingo.app.data.Card;
import com.triolingo.app.data.Drawer;
import com.triolingo.app.data.ResourceManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 1600, 850);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}