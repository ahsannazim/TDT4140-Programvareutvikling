package tdt4140.gr1878.app.ui;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.db.Database;


// Brukerhistorie 2 sprint 1, issue nr 2.6 : oppdatere kontaktinformasjon

public class EditEmailController  extends MainController{


	@FXML
	Button saveButton;
	@FXML
	TextField newEmail;
	@FXML
	TextField repeatNewEmail;
	@FXML
	Label statusLabel;
	@FXML
	Label currentEmail;
	
	public EditEmailController(Stage primaryStage, Database db, Main main) {
		super(primaryStage,db,main);
	}
	

	@SuppressWarnings("restriction")
	public void initialize() {
		super.initialize();
		
		// Viser brukeren dens nåværende e-mail
		currentEmail.setText(main.bruker.email);
	}
	
	public void saveButton(ActionEvent event) {
		String newemail = newEmail.getText();
		String repeatemail = repeatNewEmail.getText();
		boolean emailEqual = newemail.equals(repeatemail);
		// Sjekker at e-mailen er skrevet riktig ved å be brukeren skrive den inn to ganger
			if (emailEqual) {
				String em = repeatNewEmail.getText();
				
				// Query oppdaterer e-mailen til brukeren i databasen			
				db.query_update(
						"UPDATE users "
						+ "SET users_email = '" + em + "' WHERE users_username = '" + main.bruker.username + "';");
				
				// Setter det innloggede objektet sin email lik den oppdaterte emailen
				main.bruker.email = em;
				
				// Etter email er oppdatert sendes brukeren tilbake til profilsiden
				MyProfileController tc = new MyProfileController(myStage, db, main);
				newScene("MyProfile.fxml", "My profile", tc);
			} else {
				statusLabel.setText("Sorry, emails do not match!");
			}
	}

}