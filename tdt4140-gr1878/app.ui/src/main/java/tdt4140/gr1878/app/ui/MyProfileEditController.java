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

public class MyProfileEditController extends MainController{



	@FXML
	Button editPasswordButton;
	@FXML
	Button editEmailButton;
	@FXML
	Button saveButton;
	@FXML
	Button signOutButton;
	@FXML
	Button deleteUserButton;
	@FXML
	Button settingsButton;
	@FXML
	Label name;
	@FXML
	Label username;
	@FXML
	Label birthday;
	@FXML
	Label email;
	@FXML
	private TextField weight;
	@FXML
	private TextField height;
	
	@FXML private void settingsButtonClicked(ActionEvent event) {
		
	}
	public MyProfileEditController(Stage primaryStage, Database db, Main main) {
		super(primaryStage, db, main);
	}

	// Setter informasjon i feltene når klassen initialiseres
	@SuppressWarnings("restriction")
	public void initialize() {
		super.initialize();

		name.setText(main.bruker.name);
		username.setText(main.bruker.username);
		email.setText(main.bruker.email);
		birthday.setText(String.valueOf(main.bruker.birthday));
	}

	public void saveButton(ActionEvent event){
			int w = Integer.parseInt(weight.getText());
			int h = Integer.parseInt(height.getText());
			
			// Oppdaterer data om brukerens vekt og høyde i database
			db.query_update("UPDATE users " + "SET users_weight = '" + w + "', users_height = '" + h
					+ "'" + " WHERE users_username = '" + main.bruker.username + "';");

			// Endrer også det innloggede bruker-objektets vekt og høyde
			main.bruker.weight = w;
			main.bruker.height = h;

			MyProfileController tc = new MyProfileController(myStage, db, main);
			newScene("MyProfile.fxml", "My profile", tc);
	}

	public void editPasswordButton(ActionEvent event){
			EditPasswordController tc = new EditPasswordController(myStage, db, main);
			newScene("EditPassword.fxml", "Edit Password", tc);

	}

	public void editEmailButton(ActionEvent event){
			EditEmailController tc = new EditEmailController(myStage, db, main);
			newScene("EditEmail.fxml", "Edit Email", tc);

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
