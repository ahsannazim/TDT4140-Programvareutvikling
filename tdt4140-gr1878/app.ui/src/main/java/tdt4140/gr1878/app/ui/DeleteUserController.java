package tdt4140.gr1878.app.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tdt4140.gr1878.app.core.User;
import tdt4140.gr1878.app.ui.db.Database;
import javafx.scene.control.TextArea;


// Brukerhistorie 6 sprint 1, issue nr 6.1 : Gjøre det mulig å slette bruker

public class DeleteUserController {

	private Stage myStage;
	private Scene logonScene;
	private Database db;
	public Main main;

	@FXML
	TextField usernameField;
	@FXML
	TextField passwordField;
	@FXML
	TextField passwordField1;
	@FXML
	Button deleteUser;
	@FXML
	Label infoLabelDeleteUser;
	@FXML
	Button goBack;
	User person;

	// Konstruktør - Klassen inisialiseres
	public DeleteUserController(Stage primaryStage, Database db, Main main) {
		this.myStage = primaryStage;
		this.db = db;
		this.main = main;
	}

	@SuppressWarnings("restriction")
	public void initialize() {
		myStage.sceneProperty().addListener((observable, oldval, newval) -> {
			if (newval != null && this.logonScene == null)
				this.logonScene = newval;
		});
	}
	
	public void deleteUser(ActionEvent event) {
		// Sjekker om brukernavnet er registrert
		db.consume_query(
				"SELECT users_username AS username, users_password AS password, users_email AS email "
						+ "FROM maciejks_g78db.users WHERE users_username='" + usernameField.getText() + "';",
				rs -> {
					try {
						if (rs.next()) {
							
							// Sjekker at passordet er skrevet riktig
							if(passwordField.getText().equals(passwordField1.getText()) && (passwordField.getText() != null )) {
							
							// Sletter brukeren fra databasen dersom passordet som er oppgitt stemmer overens med brukernavnet
							db.query_update(
									"DELETE FROM users WHERE users_username='" + usernameField.getText()
									+ "' AND users_password='" + passwordField.getText() + "'");
							
							// Skifter scene tilbake til innlogging
							UserLoginController tc = new UserLoginController(myStage, db, main);
							newScene("Logon2.fxml", "Log", tc);
							tc.setInfoLabel("User successfully deleted");
							
							}else {
								infoLabelDeleteUser.setText("Passwords are not matching.");
								System.out.println("Password not matching.");
							}
						} else {
							// Dersom brukernavnet ikke finnes i databasen vil brukeren få beskjed om det.
							infoLabelDeleteUser.setText("Username does not exist. Please try again.");
						}
						} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NullPointerException en) {
						en.printStackTrace();
					}
					
				});
	}

	// går tilbake til innloggingssien via tilbakeknappen
	public void goBack(ActionEvent event) {
		if (main.bruker.dataCollector == 0) {
			MyProfileController tc = new MyProfileController(myStage, db, main);
			newScene("MyProfile.fxml", "My Profile", tc);
		} else if (main.bruker.dataCollector == 1) {
			MyProfileDataCollectorController mpc = new MyProfileDataCollectorController(myStage, db, main);
			newScene("MyProfileDataCollect.fxml", "My Profile", mpc);
		}
		
	}
	
	// vår standardmetode for å endre vindu
	private void newScene(String resource, String title, Object controller) {
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
		// linker til CSS-fil
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		myStage.setHeight(572);
		myStage.setWidth(572);
		myStage.setTitle(title);
		myStage.setScene(scene);
		myStage.centerOnScreen();
	}

}
