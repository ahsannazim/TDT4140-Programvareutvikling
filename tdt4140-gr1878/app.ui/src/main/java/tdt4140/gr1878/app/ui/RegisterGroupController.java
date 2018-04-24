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

public class RegisterGroupController extends MainController {

	
	protected boolean created = false;
	@FXML
	private TextField nameField;
	@FXML
	private TextField pointsField;
	@FXML
	private TextField prizeField;
	@FXML
	private TextField memberField;
	@FXML
	private Button createButton;
	@FXML
	private Label infoLabel;
	@FXML
	private Button backButton;
	

	
	public RegisterGroupController(Stage primaryStage, Database db, Main main) {
		super(primaryStage, db, main);

	}
	

	@SuppressWarnings("restriction")
	public void initialize() {
		super.initialize();
		// Setter stage listener, hører etter klikk etc.
		myStage.sceneProperty().addListener((observable, oldval, newval) -> {
			if (newval != null && this.logonScene == null)
				this.logonScene = newval;
		});
	}
	
	public void createButton(ActionEvent event) {
		createNew();
		if (created) {
			MyGroupsController mgc = new MyGroupsController(myStage, db, main);
			newScene("MyGroups.fxml", "My groups", mgc);
		} else {
			System.out.println("created = false");
		}
	}
	
	public void backButton(ActionEvent event) {
		MyGroupsController mgc = new MyGroupsController(myStage, db, main);
		newScene("MyGroups.fxml", "My Groups", mgc);
	}
	
	public void createNew() {
		if (!(pointsField.getText().matches("\\d+")) || !(pointsField.getText().length() > 0)) {
			infoLabel.setText("Goal must be an int!");
			return;
		}
		if (!(nameField.getText().length() > 0)) {
			infoLabel.setText("Group name must be set!");
			return;
		}
		
		db.consume_query("SELECT group_name as groupName, group_id as groupID "
				+ "FROM maciejks_g78db.groups WHERE group_name='" + nameField.getText() + "';",
				rs -> {
					try {
						if (rs.next()) {
							infoLabel.setText("Group name already taken!");
						} else {
			// brukerhistorie 6 sprint 2 , issue nr 6.0: Gruppeadministrator skal kunne opprette en gevinst for gruppens mål
							db.query_update("INSERT INTO groups (group_name, points_goal, prize ) "
								+ "VALUES ('" + nameField.getText() + "','"
										+ Integer.parseInt(pointsField.getText()) + "','"
										+ prizeField.getText() + "')"
									);
							
							
//							if (addMembers(memberField.getText(), Integer.parseInt(rs.getString("groupID")))) {
//								infoLabel.setText("Group created!");
//							} else {
//								infoLabel.setText("Group created but members not added due to invalid input, you can add them at the group page!");
//							}
							//addSelf(rs.getString("groupID"));
						}
					}catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NullPointerException en) {
						en.printStackTrace();
					}
				}
				);
		created = true;
		makeSelfAdmin(main.bruker.id);
		addMembers(memberField.getText());
	}
	
	public void makeSelfAdmin(int adminID) {
		db.consume_query("SELECT group_id as groupID "
				+ "FROM maciejks_g78db.groups "
				+ "WHERE group_name = '" + nameField.getText() + "';",
				rs -> {
					try {
						if(rs.next()) {
							db.query_update("INSERT INTO group_user_relation (group_id, users_id, user_role ) "
								+ "VALUES ('" + rs.getString("groupID") + "','"
										+ adminID + "','"
										+ "admin" + "')"
									);
						}
					}catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NullPointerException en) {
						en.printStackTrace();
					}
				}
				);
	}
	
	// brukerhistorie 2 og 3 sprint 2 , issue nr 2.0 og 3.0: Legge til andre brukere i gruppen og få bruker til å være et meldem av flere grupper

	boolean ok = true;
	public void addMembers(String memberString) {
		System.out.println("addMembers blir kjørt");
		String[] memberArray = memberString.split("\\,");
		String medlem = "medlem";
		for (String member: memberArray) {
			db.consume_query("SELECT users_id as userID, users_username as userName "
					+ "FROM maciejks_g78db.users "
					+ "WHERE users_username = '" + member + "';",
					rs -> {
						try {
							if (rs.next()) {
								db.consume_query("SELECT group_id as groupID "
										+ "FROM maciejks_g78db.groups "
										+ "WHERE group_name = '" + nameField.getText() + "';",
										ts -> {
											try {
												if(ts.next()) {
													db.query_update("INSERT INTO group_user_relation (group_id, users_id, user_role ) "
														+ "VALUES ('" + ts.getString("groupID") + "','"
																+ rs.getString("userID") + "','"
																+ medlem + "')"
															);
												}
											}catch (SQLException e1) {
												e1.printStackTrace();
											} catch (NullPointerException en) {
												en.printStackTrace();
											}
										}
										);
								
								System.out.println("nice");
							} else {
								ok = false;
								System.out.println("rip");
							}
						}catch (SQLException e1) {
							e1.printStackTrace();
						} catch (NullPointerException en) {
							en.printStackTrace();
						}
						System.out.println("wat");
					}
					);
		}
		System.out.println("????");
	}
	

}
