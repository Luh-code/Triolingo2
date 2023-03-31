package com.triolingo.app;

import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.SQLInfo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 1600, 850);
		stage.setTitle("Triolingo v0.3alpha");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		SQLInfo info = new SQLInfo("jdbc:mysql://localhost:3306", "triolingo", "triolingodb");
		SQLAccess access = new SQLAccess();
		ControllerManager.getInstance().registerResourceType(SQLInfo.class);
		ControllerManager.getInstance().setResource("SQLInfo", info);
		ControllerManager.getInstance().registerResourceType(SQLAccess.class);
		ControllerManager.getInstance().setResource("SQLAccess", access);
		access.initSQLBackend(info);
		launch();
	}
}