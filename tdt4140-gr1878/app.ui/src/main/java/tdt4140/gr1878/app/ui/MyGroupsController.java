package tdt4140.gr1878.app.ui;



import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.db.Database;


public class MyGroupsController extends MainController{


	
	private int id;

	protected int counter = 0;
	
	@FXML
	Button group1;
	@FXML
	Button group2;
	@FXML
	Button group3;
	@FXML
	Button group4;
	@FXML
	Button group5;
	@FXML
	private Button createNewGroupButton;
	private List<Integer> totalmembers = new ArrayList<>();
	
	
	public MyGroupsController(Stage primaryStage, Database db, Main main) {
		super(primaryStage, db, main);
	}
	
	// brukerhistorie 5 sprint 2 , issue nr 5.0:  Se alle gruppene jeg er medlem av med gruppenavn og bilde
	@SuppressWarnings("restriction")
	public void initialize() {
		super.initialize();
		// Setter stage listener, hører etter klikk etc.
		group1.setVisible(false);
		group2.setVisible(false);
		group3.setVisible(false);
		group4.setVisible(false);
		group5.setVisible(false);
		
		getGroups();
	}
	
	// Lyttere til knapper som representerer grupper brukeren er medlem av
	public void group1(ActionEvent event) {
		String [] arrOfStr1 = group1.getText().split(":");
		String groupname = arrOfStr1[0];
		System.out.println(groupname);
		SelectedGroupController tc= new SelectedGroupController(myStage, db, main, groupname);
		newScene("SelectedGroup.fxml", groupname,tc);
	}
	public void group2(ActionEvent event) {
		String [] arrOfStr2 = group2.getText().split(":");
		String groupname = arrOfStr2[0];
		System.out.println(groupname);
		SelectedGroupController tc= new SelectedGroupController(myStage, db, main, groupname);
		newScene("SelectedGroup.fxml", groupname,tc);
	}
	public void group3(ActionEvent event) {
		String [] arrOfStr3 = group3.getText().split(":");
		String groupname = arrOfStr3[0];
		SelectedGroupController tc= new SelectedGroupController(myStage, db, main, groupname);
		newScene("SelectedGroup.fxml", groupname,tc);
	}
	public void group4(ActionEvent event) {
		String [] arrOfStr4 = group4.getText().split(":");
		String groupname = arrOfStr4[0];
		SelectedGroupController tc= new SelectedGroupController(myStage, db, main, groupname);
		newScene("SelectedGroup.fxml", groupname,tc);
	}
	public void group5(ActionEvent event) {
        String [] arrOfStr5 = group5.getText().split(" ");
		String groupname = arrOfStr5[0];
		SelectedGroupController tc= new SelectedGroupController(myStage, db, main, groupname);
		newScene("SelectedGroup.fxml", groupname,tc);
	}
	
	public void createNewGroupButton(ActionEvent event) {
		RegisterGroupController tc = new RegisterGroupController(myStage, db, main);
		newScene("RegisterGroup.fxml", "Create new group", tc);
	}
	
	
	private int members;

	// Brukerhistorie 5.0 Sprint 3, Issue #75: Henter ut antall medlemmer av (input) gruppen
	private int getTotalMembers(String groupName) {
		db.consume_query(
				"SELECT count(group_name) AS total " 
						+ "FROM maciejks_g78db.groups "
						+ "JOIN maciejks_g78db.group_user_relation ON groups.group_id = group_user_relation.group_id"
						+ " WHERE groups.group_name = '" + groupName + "';",
				rs -> {
					try {
						if(rs.next()) {
							members = Integer.parseInt(rs.getString("total"));
							} else {
								System.out.println("Finner ikke totalt antall");
							}
					}catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NullPointerException en) {
						en.printStackTrace();
					}
				}
				);
		return members;
	}
	
	// Returnerer en string med antall medlemmer av gruppen + "members"
	private String makeAmountText(String grname) {
		return ": " + getTotalMembers(grname) + " members " ;
	}
	
	// Henter ut gruppenavn, synliggjør likt antall knapper som grupper brukeren er medlem av og setter tekst på knappene
	// En bruker kan maksimalt være medlem av 5 grupper, derfor kun 5 knapper
	// Dersom brukeren ikke enda er medlem av noen grupper, vil den få opp et felt som indikerer det
	private void getGroups() {
		db.consume_query(
		"SELECT group_id AS groupID "
		+ "FROM maciejks_g78db.group_user_relation "
		+ "WHERE users_id = '" + main.bruker.id + "';",
		rs -> {
			try {
				while (rs.next()) {
					db.consume_query(
							"SELECT group_name as groupName "
							+ "FROM maciejks_g78db.groups "
									+ "WHERE group_id = '" + rs.getInt("groupID") + "';",
							ts -> {
								try {
									if (ts.next()) {
										if (counter == 0) {
											group1.setVisible(true);
											group1.setText(ts.getString("groupName") + makeAmountText(ts.getString("groupName")));
										} else if (counter == 1){
											group2.setVisible(true);
											group2.setText(ts.getString("groupName") +makeAmountText(ts.getString("groupName")));		
										} else if (counter == 2) {
											group3.setVisible(true);
											group3.setText(ts.getString("groupName") + makeAmountText(ts.getString("groupName")));
										} else if (counter == 3) {
											group4.setVisible(true);
											group4.setText(ts.getString("groupName") + makeAmountText(ts.getString("groupName")));
										} else if (counter == 4) {
											group5.setVisible(true);
											group5.setText(ts.getString("groupName") + makeAmountText(ts.getString("groupName")));
										} else {
										
										}
										counter += 1;
									
									}
								} catch (SQLException e1) {
									e1.printStackTrace();
								} catch (NullPointerException en) {
									en.printStackTrace();
								}
							}
							);
				} if (group1.getText().length() == 0) {
					// if not in any groups
					group1.setText("You are not a member of any group");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (NullPointerException en) {
				en.printStackTrace();
			}
		});
	}

}