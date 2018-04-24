package tdt4140.gr1878.app.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

//Denne controlleren brukes kun til å teste makeuserpage og derfor er databasefeltene kommentert ut. 
/*I denne controlleren bytter vi scene uten at vi spesifiserer controller her,
 *  men heller har det i fxml-filen til scenen vi bytter til, slik som studassene våre har sakt at vi skal. 
 */
//brukerhistorie 1 sprint 1, issue nr 1 : opprette bruker med navn, passord og email.

public class MakeuserCon extends Application {

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
	Stage stage;

	public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Makeuser.fxml"));
        
        Parent root = loader.load();

        primaryStage.setTitle("Hello World");
        Scene scene =new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.show();
       
   }
	
	
	public void registerButton(ActionEvent event) {
		/*UserLoginController tc = new UserLoginController(myStage, db, main);
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
						if(checkPasswordsMatch()) {
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
			*/
	}
	
	public boolean checkPasswordsMatch() {
		if (passwordField1.getText().equals(passwordField.getText())) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void goBack(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Logon.fxml"));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO replace with alert
			e.printStackTrace();
			return;
		}
		
		Scene scene = new Scene(root);
		
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.setTitle("Log");
		window.show();
		//måtte implementere denne litt annerledes fordi vi ikke har satt lyttere. 
	}
	
	//HUSK legg inn i fxml 
	public void radioCollect(ActionEvent event){
		//dataCollector = 1;
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
	private void newScene(String resource, String title) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			// TODO replace with alert
			e.printStackTrace();
			return;
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}


	/*private String checkValidEmail() {
		String email= emailField.getText();
		 if(!email.contains("@")) {
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
	}*/
	public static void main(String[] args) {
		launch(args);
	}
}