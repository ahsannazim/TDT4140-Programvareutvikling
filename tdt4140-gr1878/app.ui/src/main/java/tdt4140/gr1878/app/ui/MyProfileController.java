package tdt4140.gr1878.app.ui;



import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JToggleButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.db.Database;

//brukerhistorie 5 sprint 1, issue nr 5 : gjøre det mulig å logge ut av bruker

public class MyProfileController extends MainController{


	@FXML
	Label username;
	@FXML
	Button editProfileButton;
	@FXML
	Button signOutButton;
	@FXML
	Button deleteUserButton;

	//brukerhistorie 11.1 sprint 2, issue nr 11.1 :  Lage ja/nei avkrysning for dele info til vegvesen
	@FXML
	RadioButton radioYes;
	@FXML
	RadioButton radioNo;


	@FXML
	private Label name;
	@FXML
	private Label birthday;
	@FXML
	private Label email;
	@FXML
	private Label weight;
	@FXML
	private Label height;
	@FXML
	private Label shareInfo;

	private boolean bePrivateUser;

	public MyProfileController(Stage primaryStage, Database db, Main main) {
		super(primaryStage, db, main);
		this.bePrivateUser = true;

	}


	@SuppressWarnings("restriction")
	public void initialize() {
		super.initialize();

		name.setText(main.bruker.name);
		username.setText(main.bruker.username);
		email.setText(main.bruker.email);
		birthday.setText(String.valueOf(main.bruker.birthday));
		weight.setText(String.valueOf(main.bruker.weight));
		height.setText(String.valueOf(main.bruker.height));
	}

	public void editProfileButton(ActionEvent event) {
		MyProfileEditController tc = new MyProfileEditController(myStage, db, main);
		newScene("MyProfileEdit.fxml", "Log", tc);
	}

	public void signOutButton(ActionEvent event) {
		UserLoginController tc = new UserLoginController(myStage, db, main);
		newScene("Logon2.fxml", "Log", tc);
	}

	public void deleteUserButton(ActionEvent event) {
		DeleteUserController tc = new DeleteUserController(myStage, db, main);
		newScene("DeleteUser.fxml", "Delete user", tc);
	}

	//brukerhistorie 11.1 sprint 2, issue nr 11.1 :  Lage ja/nei avkrysning for dele info til vegvesen
	//Brukerhistorie 2.0 Sprint 3, Issue 45: Brukeren kan velge å dele informasjon ved knappene og oppdaterte databasen for mer sikkerhet
	public void radioNo(){
		db.consume_query(
				"SELECT privacy AS Privacy "
						+ "FROM maciejks_g78db.users "
						+ "WHERE users_id ='" + main.bruker.id + "';",
						ts -> {
							try {
								if (ts.next()) {
									db.query_update(
											"UPDATE users "
													+ "SET Privacy = '" + 0
													+ "' WHERE users_id='" + main.bruker.id + "';"); 
								}
							}catch (SQLException e1) {
								e1.printStackTrace();
							} catch (NullPointerException en) {
								en.printStackTrace();
							}
						}
				); 
	}

	public void radioYes(){
		db.consume_query(
				"SELECT privacy AS Privacy "
						+ "FROM maciejks_g78db.users "
						+ "WHERE users_id ='" + main.bruker.id + "';",
						ts -> {
							try {
								if (ts.next()) {
									db.query_update(
											"UPDATE users "
													+ "SET Privacy = '" + 1
													+ "' WHERE users_id='" + main.bruker.id + "';"); 
								}
							}catch (SQLException e1) {
								e1.printStackTrace();
							} catch (NullPointerException en) {
								en.printStackTrace();
							}
						}
				); 
	}


}
