package com.triolingo.app;

import com.triolingo.app.data.sql.SQLAccess;
import com.triolingo.app.data.sql.SQLInfo;
import com.triolingo.app.utils.SpeechSynth;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.Engine;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.io.IOException;
import java.util.Locale;

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
		ControllerManager.getInstance().registerResourceType_s(SQLInfo.class);
		ControllerManager.getInstance().setResource("SQLInfo", info);
		ControllerManager.getInstance().registerResourceType_s(SQLAccess.class);
		ControllerManager.getInstance().setResource("SQLAccess", access);
		access.initSQLBackend(info);

		SpeechSynth synth = new SpeechSynth();
		synth.init();
		ControllerManager.getInstance().registerResourceType_s(SpeechSynth.class);
		ControllerManager.getInstance().setResource("voiceSynthesizer", synth);
		launch();
	}
}