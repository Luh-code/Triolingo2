module com.triolingo.app {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires net.synedra.validatorfx;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.bootstrapfx.core;
	//requires eu.hansolo.tilesfx;
	requires com.almasb.fxgl.all;
	//requires jfxanimation;
	//requires ojdbc8;
	requires java.sql;

	opens com.triolingo.app to javafx.fxml;
	exports com.triolingo.app;
}