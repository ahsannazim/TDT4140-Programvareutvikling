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
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tdt4140.gr1878.app.core.User;
import tdt4140.gr1878.app.ui.db.Database;
import javafx.scene.control.TextArea;

/**
 * The javafx controller for the login page. This is the first GUI view shown to
 * the user, and handles the login stage.
 * 
 * <p>
 * This class fetches the different accounts from the database, and sets up the
 * various choiceboxes for the user.
 * 
 * <p>
 * This is also where the account instance for the user is created.
 */

//brukerhistorie 2 sprint 1: Innlogging
public class UserLoginController {
	
	private Stage myStage;
	private Scene logonScene;
	private Database db;
	public Main main;

	@FXML
	TextField usernameField;
	@FXML
	TextField passwordField;
	@FXML
	Button logonButton;
	@FXML
	Button makeUser;
	User person;
	@FXML
	Label infoLabel;
	@FXML
	Label statusLabel;

	// Konstruktør - Klassen inisialiseres
	public UserLoginController(Stage primaryStage, Database db, Main main) {
		this.myStage = primaryStage;
		this.db = db;
		this.main = main;
	}

	public UserLoginController(Stage primaryStage) {
		this.myStage = primaryStage;
	}

	@SuppressWarnings("restriction")
	public void initialize() {
		// Setter stage listener, hører etter klikk etc.
		myStage.sceneProperty().addListener((observable, oldval, newval) -> {
			if (newval != null && this.logonScene == null)
				this.logonScene = newval;
		});
		/**
		 * Logonbutton fra fxml, her lager vi en kort versjon av "On click" metode.
		 * .setOnAction( "noe som helst, navn på funksjonen" { funskjon });
		 * db.consume_query spiser vår query. Prøv å connect til databasen gjennom MySql
		 * og kjør query der til de fungerer .consume_query tar inn førsteparameter som
		 * query og andre er en consumer (altså handler det vi får tilbake
		 * Vi kan for eksempel skrive en try catch og handle alle variablene vi fikk fra querien
		 * ved å loope gjennom de if(rs.next())
		 */

		//Denne gjør at vi kan logge på med enter.
		passwordField.setOnKeyPressed(p -> {
			p.getCode();
			if(p.getCode().equals(KeyCode.ENTER)) {
				logonButton.fire();
			}
			
		
			
		});
	}
		
	public void logon(ActionEvent event) {
		db.consume_query(
				"SELECT users_id AS userId, users_name AS name, users_username AS username, users_password AS password, users_email AS email, users_birthday AS birthday, users_weight AS weight, users_height AS height, users_datacollector AS dataCollector "
						+ "FROM maciejks_g78db.users "
						+ "WHERE users_username='" + usernameField.getText() + "' AND "
								+ "users_password= '"+ passwordField.getText() + "';",
				rs -> {
					try {
						if (rs.next()) {
							//Se query, vi ville ha ut users_username as username. Her kan vi sette som var
							//Brukeren finnes, si i fra til brukeren
							main.bruker = new User(Integer.parseInt(rs.getString("userId")), rs.getString("name"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getInt("birthday"), rs.getInt("weight"), rs.getInt("height"), rs.getInt("dataCollector"));
							//Sånn skifter dokker scener:
							MyProfileController tc = new MyProfileController(myStage, db, main);
							MyProfileDataCollectorController tc1 = new MyProfileDataCollectorController(myStage, db, main);
							if (main.bruker.dataCollector!=0) {
								newScene("MyProfileDataCollect.fxml", "ProfileCollect", tc1);
								
							}else {
								newScene("MyProfile.fxml", "Profile", tc);
							}
						} else {
							statusLabel.setText("Sorry, username and/or password is incorrect!");
							System.out.println("Can not Log In");
							//Feil bruker eller passord
							
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NullPointerException en) {
						en.printStackTrace();
					}

				});
	
			
	}
		
	public void makeUser(ActionEvent event) {
		RegisterUserController tc = new RegisterUserController(myStage, db, main);
		newScene("Makeuserfin.fxml", "Log", tc);
			
	}
	
	
	public void setInfoLabel(String info) {
		infoLabel.setText(info);
	}
	

	private void newScene(String resource, String title, Object controller) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
		loader.setController(controller);
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO replace with alert
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
