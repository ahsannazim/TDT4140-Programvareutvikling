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
import javafx.scene.control.RadioButton;
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
//brukerhistorie 1 sprint 1, issue nr 1 : opprette bruker med navn, passord og email.
public class RegisterUserController {


	private Stage myStage;
	private Scene logonScene;
	private Database db;
	public Main main;
	public boolean validEmail;
	public int dataCollector= 0;


	@FXML
	TextField usernameField;
	@FXML
	TextField passwordField;
	@FXML
	TextField passwordField1;
	@FXML
	TextField emailField;
	
	@FXML
	Button goBack;
	User person;
	@FXML
	Button registerButton;
	@FXML
	RadioButton radioCollect;
	
	@FXML 
	Label statusLabel;

	// Konstruktør - Klassen inisialiseres
	public RegisterUserController(Stage primaryStage, Database db, Main main) {
		this.myStage = primaryStage;
		this.db = db;
		this.main = main;
	}

	public RegisterUserController(Stage stage) {
		// TODO Auto-generated constructor stub
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
	}
	
	public void registerButton(ActionEvent event) {
		UserLoginController tc = new UserLoginController(myStage, db, main);
		db.consume_query(
			"SELECT users_username AS username, users_password AS password, users_email AS email "
					+ "FROM maciejks_g78db.users WHERE users_username='" + usernameField.getText() + "';",
			rs -> {
				try {
					if (rs.next()) {
						//Se query, vi ville ha ut users_username as username. Her kan vi sette som var
						//Brukeren finnes, si i fra til brukeren
						String username = rs.getString("username");
						statusLabel.setText("Username already exists");
					} else {
						//Registrer bruker
						if(passwordField.getText().equals(passwordField1.getText())) {
							String answer = checkValidEmail();
							if(validEmail ) {
								db.query_update("INSERT INTO users (users_username, users_password, users_email, users_datacollector) VALUES (" + "'"
										+ usernameField.getText() + "'" + "," + "'"
										+ passwordField.getText() + "'" + "," + "'"
										+ emailField.getText() + "'" + "," + "'"
										+ dataCollector + "'" 
										+");"
										);
								newScene("Logon2.fxml", "Log", tc);
								//System.out.println(dataCollector);
								}else{
									statusLabel.setText(answer);
								
								}
						}else { 
							statusLabel.setText("Passwords does not match");
						
					}
						}} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (NullPointerException en) {
					en.printStackTrace();
				}
				
			});
	}
	
	public void goBack(ActionEvent event) {
		UserLoginController tc = new UserLoginController(myStage, db, main);
		newScene("Logon2.fxml", "Log", tc);
	}
	
	//HUSK legg inn i fxml 
	public void radioCollect(ActionEvent event){
		dataCollector = 1;
	}
	

	/**
	 * Utility method that initializes a new scene and then moves to the scene.
	 * 
	 * @param resource
	 *            the name of the FXML resource
	 * @param title
	 *            the title on the window, if applicable
	 * @param controller
	 *            the controller object for the given FXML file
	 */
	@SuppressWarnings("restriction")
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


private String checkValidEmail() {
	String email= emailField.getText();
	if (!email.contains("@")) {
		this.validEmail =false;
		return "Your email must contain: '@'";	
	} 
	String [] parts = email.split("@");
	String[] domainParts = parts[1].split("\\.");
	
	
	if (domainParts.length != 2){
		this.validEmail = false;
		return "The domain name in your email must contain: '.'";
	}
	else {
		this.validEmail = true;
		return "";
			
	}
	


}
}
