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

public class EditPasswordController extends MainController{

	
	
	@FXML
	Button saveButton;
	@FXML
	TextField newPassword;
	@FXML
	TextField repeatNewPassword;
	@FXML
	Label statusLabel;
	@FXML
	Label currentPassword;
	
	// Konstruktør
	public EditPasswordController(Stage primaryStage, Database db, Main main) {
		super(primaryStage, db, main);
	}
	

	@SuppressWarnings("restriction")
	public void initialize() {
		super.initialize();
		
		// Viser brukeren dens nåværende passord
		currentPassword.setText(main.bruker.password);
	}
	
	public void saveButton(ActionEvent event) {
		String newpass = newPassword.getText();
		String repeatpass = repeatNewPassword.getText();
		boolean passwordsEqual = newpass.equals(repeatpass);
		// Passordet må skrives inn to ganger og blir sjekket opp mot hverandre
			if (passwordsEqual) {
				String p = repeatNewPassword.getText();
				
				// Oppdaterer passordet i databasen
				db.query_update(
						"UPDATE users "
						+ "SET users_password = '" + p + "' WHERE users_username = '" + main.bruker.username + "';");
				
				// Setter det innloggede objektet sitt passord lik det oppdaterte passordet
				main.bruker.password = p;
				
				// Sender så brukeren tilbake til profilsiden
				MyProfileController tc = new MyProfileController(myStage, db, main);
				newScene("MyProfile.fxml", "My profile", tc);
			} else {
				statusLabel.setText("Sorry, passwords do not match!");
			}
	}


}