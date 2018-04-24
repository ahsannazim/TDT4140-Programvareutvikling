package tdt4140.gr1878.app.ui;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JToggleButton;

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

//brukerhistorie 11, sprint 2 issue nr 11.3 : Tilpasse sidene til innlogging som data-uthenter

public class MyProfileDataCollectorController extends MainControllerDataCollector{
	
	@FXML
	Button totaltripsButton;
	@FXML
	Button signOutButton;
	@FXML
	Button deleteUserButton;
	@FXML
	Button mapButton;
	
	@FXML
	private Label username;
	@FXML
	private Label email;
		
	public MyProfileDataCollectorController(Stage primaryStage, Database db, Main main) {
		super(primaryStage, db, main);
	}
	
	@SuppressWarnings("restriction")
	public void initialize() {
		super.initialize();
		// Setter stage listener, hører etter klikk etc.
		
		myStage.sceneProperty().addListener((observable, oldval, newval) -> {
			if (newval != null && this.logonScene == null)
				this.logonScene = newval;
		});
		
			
		username.setText(main.bruker.username);
		email.setText(main.bruker.email);
	}
		
	public void totaltripsButton(ActionEvent event){
		TotalTripsController tc = new TotalTripsController(myStage, db, main);
		newScene("TotalTrips.fxml", "Log", tc);
	}
	
	public void signOutButton(ActionEvent event) {
			UserLoginController tc = new UserLoginController(myStage, db, main);
			newScene("Logon2.fxml", "Log", tc);
	}
	
	public void deleteUserButton(ActionEvent event) {
			DeleteUserController tc = new DeleteUserController(myStage, db, main);
			newScene("DeleteUser.fxml", "Delete user", tc);
	}
				
	public void	mapButton(ActionEvent event){
			MapController tc = new MapController(myStage, db, main);
			newScene("Map.fxml", "Map", tc);
	}
	
}
	