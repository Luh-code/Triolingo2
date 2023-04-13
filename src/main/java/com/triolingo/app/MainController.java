package com.triolingo.app;

//import de.schlegel11.jfxanimation.JFXAnimationTemplate;
import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class MainController {

	private int iconsize = 25;

	@FXML
	private Button homebtn;

	@FXML
	private Button showcarddecksbtn;

	@FXML
	private Button learnbtn;

	@FXML
	private VBox contentPane;

	@FXML
	private VBox sidebar;

	@FXML
	private Button createcardsbtn;

	@FXML
	public void expandSidebar()
	{
		ScaleTransition expand = new ScaleTransition();
		expand.setDuration(Duration.seconds(2));
		expand.setNode(sidebar);
		expand.setByX(3);
		expand.setCycleCount(1);
		expand.setAutoReverse(true);
		expand.play();
		/*new AnimatonHelper(Duration.seconds(2))
			.percent(1.0)
			.action(sidebar.prefWidthProperty(), 150)
			.start();*/
		//DoubleProperty w = sidebar.prefWidthProperty();
		//w.set(150);
		System.out.println("Sidebar expanding");
		/*JFXAnimationTemplate.create()
			.percent(0)
			.action(b -> b.target(sidebar.prefWidthProperty()).endValue(30))
			.percent(100)
			.action(b -> b.target(sidebar.prefWidthProperty()).endValue(150))
			.config(b -> b.duration(Duration.seconds(2)).fromToAutoGen().fluentTransition())
			.build();*/
	}

	@FXML
	public void loadHome()
	{
		loadFxml("menu.fxml", true);
	}

	@FXML
	public void loadCardRegister()
	{
		loadFxml("cardRegister.fxml", true);
	}

	@FXML
	private void train()
	{
		loadFxml("menu.fxml", true);
		ControllerManager.getInstance().getResource("Menu", MenuController.class).showBoxSelector();
	}
	@FXML
	public void loadTrainingWidget()
	{
		loadFxml("learningProgress.fxml", true);
		loadFxml("card.fxml", false);
	}

	@FXML
	public void loadCardCreator()
	{
		loadFxml("cardCreator.fxml", true);
		loadFxml("dummyCard.fxml", false);
		ControllerManager.getInstance().getResource("CardCreator", CardCreatorController.class)
			.setUseDummy(true, "DummyCard");
	}

	@FXML
	public void loadFxml(String name, boolean clear)
	{
		try {
			URL res = getClass().getResource(name);
			if(res == null) throw new IllegalArgumentException();
			Pane newLoadedPane = FXMLLoader.load(res);
			if(clear) contentPane.getChildren().clear();
			contentPane.getChildren().add(newLoadedPane);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ObservableList<Node> getContentPaneChildren()
	{
		return contentPane.getChildren();
	}

	public void addIconToBtn(String url, Button btn, int size)
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
		ControllerManager.getInstance().registerResourceType_s(MainController.class);
		ControllerManager.getInstance().setResource("Main", this);
		addIconToBtn("com/triolingo/app/icons/Home@2x.png", homebtn,25);
		addIconToBtn("com/triolingo/app/icons/Card@2x.png", learnbtn, 25);
		addIconToBtn("com/triolingo/app/icons/SearchCards@2x.png", showcarddecksbtn, 25);
		addIconToBtn("com/triolingo/app/icons/CreateCards@2x.png", createcardsbtn, 25);
		loadFxml("menu.fxml", true);
	}
}