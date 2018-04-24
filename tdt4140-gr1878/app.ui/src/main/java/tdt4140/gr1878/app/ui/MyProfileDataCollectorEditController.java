package tdt4140.gr1878.app.ui;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.db.Database;

/**
 * Created by Polakken97 26. feb. 2018
 */

public class MyProfileDataCollectorEditController extends MainControllerDataCollector{

	@FXML
	Button totaltripsButton;
	@FXML
	Button signOutButton;
	@FXML
	Button deleteUserButton;
	@FXML
	Label name;
	@FXML
	Label email;

	
	@FXML private void settingsButtonClicked(ActionEvent event) {
		
	}
	public MyProfileDataCollectorEditController(Stage primaryStage, Database db, Main main) {
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

		name.setText(main.bruker.name);
		email.setText(main.bruker.email);
	}

	public void totaltripsButton(ActionEvent event){
		TotalTripsController tc = new TotalTripsController(myStage, db, main);
		newScene("TotalTrips.fxml", "Log", tc);
}
	public void signOutButton(ActionEvent event){
			UserLoginController tc = new UserLoginController(myStage, db, main);
			newScene("Logon2.fxml", "Log", tc);
	}

	public void deleteUserButton(ActionEvent event){
			DeleteUserController tc = new DeleteUserController(myStage, db, main);
			newScene("DeleteUser.fxml", "Delete user", tc);
	}


}
