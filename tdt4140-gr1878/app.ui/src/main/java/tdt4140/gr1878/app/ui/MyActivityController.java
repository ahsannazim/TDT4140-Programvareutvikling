package tdt4140.gr1878.app.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.db.Database;

import tdt4140.gr1878.app.ui.db.Database;

public class MyActivityController extends MainController{

	@FXML
	Button tempButton;
	
	
	public MyActivityController(Stage primaryStage, Database db, Main main) {
		super(primaryStage, db, main);
	}
	
	

	@SuppressWarnings("restriction")
	public void initialize() {
		super.initialize();
	}
	
	
	public void tempButton(ActionEvent event) {
		TrackingController tc = new TrackingController(myStage, db, main);
		newScene("Tracking2.fxml", "Tracking", tc);
	}
	

}