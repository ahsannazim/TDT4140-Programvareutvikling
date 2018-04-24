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
//Denne controlleren er for å teste logonsiden og har derfor en tom logon metode. 
/*I denne controlleren bytter vi scene uten at vi spesifiserer controller her,
 *  men heller har det i fxml-filen til scenen vi bytter til, slik som studassene våre har sakt at vi skal. 
 */
//brukerhistorie 2 sprint 1: Innlogging
public class LogonCon extends Application{
	
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
	
	Stage stage;

	
	public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Logon.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Hello World");
        Scene scene =new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
       
   }
	public void logon(ActionEvent event) {
		/*db.consume_query(
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
	
		*/	
	}
		
	public void makeUser(ActionEvent event) {
		newScene("Makeuserfin.fxml", "Log");
			
	}
	
	public void setInfoLabel(String info) {
		infoLabel.setText(info);
	}
	

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

   public static void main(String[] args) {
       launch(args);
   }
}