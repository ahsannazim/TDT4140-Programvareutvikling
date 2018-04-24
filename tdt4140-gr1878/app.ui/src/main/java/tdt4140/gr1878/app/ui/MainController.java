package tdt4140.gr1878.app.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.db.Database;

// Maincontroller for alle klassene som har med "vanlige" brukere å gjøre, altså brukere som ikke har til hensikt å hente informasjon (eks. statens vegevesen) 
public class MainController {

	protected Stage myStage;
	protected Scene logonScene;
	protected Database db;
	protected Main main;
	
	@FXML
	Button groupsButton;
	@FXML
	Button profileButton;
	@FXML
	Button activityButton;
	@FXML
	Button settingsButton;
	
	
	public MainController(Stage primaryStage, Database db, Main main) {
		this.myStage = primaryStage;
		this.db = db;
		this.main = main;
	}
	
	public void initialize() { 
		// Setter stage listener, hører etter klikk etc.
		myStage.sceneProperty().addListener((observable, oldval, newval) -> {
			if (newval != null && this.logonScene == null)
				this.logonScene = newval;
		});
	}
	
	// Lyttere til menyknappene
	public void groupsButton(){
			MyGroupsController tc = new MyGroupsController(myStage, db, main);
			newScene("MyGroups.fxml", "Grupper", tc);
			
	}

	public void profileButton(){
			MyProfileController tc = new MyProfileController(myStage, db, main);
			newScene("MyProfile.fxml", "Profil", tc);
			
	}
		
	public void activityButton(){
			MyActivityController tc = new MyActivityController(myStage, db, main);
			newScene("MyActivity.fxml", "Aktivitet", tc);
	}
		
	public void settingsButton(){
			MySettingsController tc = new MySettingsController(myStage, db, main);
			newScene("MySettings.fxml", "Innstillinger", tc);
	}
	
	// Metode for å bytte vindu - setter controller manuelt og linker til CSS-fil
	protected void newScene(String resource, String title, Object controller) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
		loader.setController(controller);
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		myStage.setHeight(572);
		myStage.setWidth(572);
		myStage.setTitle(title);
		myStage.setScene(scene);
		myStage.centerOnScreen();
	}
	
	
}
