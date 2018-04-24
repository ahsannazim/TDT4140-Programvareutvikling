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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.db.Database;

public class SelectedGroupController extends MainController {


	String groupName;
	
	
	@FXML
	private Label pctLabel;
	@FXML
	private Label distLabel;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Label prizeLabel;
	@FXML
	private TextArea memberText;
	@FXML
	private TextField prizeField;
	@FXML
	private Label prizeText;
	@FXML
	private Button changeButton;
	@FXML
	private Label memberInputText;
	@FXML
	private TextField memberField;
	@FXML
	private Button addButton;
	@FXML
	private Label adminLabel;
	@FXML
	private Label pointsLabel;
	
	@FXML
	private Button backButton;
	
	public SelectedGroupController(Stage primaryStage, Database db, Main main, String groupName) {
		super(primaryStage, db, main);

		this.groupName = groupName;
	}
	
	
	@SuppressWarnings("restriction")
	public void initialize() {
		super.initialize();
		myStage.sceneProperty().addListener((observable, oldval, newval) -> {
			if (newval != null && this.logonScene == null)
				this.logonScene = newval;
		});
		
		getPoints();
		fetchPrize();
		fetchMembers();
		
		setIfAdmin();
		if (adminLabel.getText() == "Admin") {
			memberInputText.setVisible(true);
			prizeText.setVisible(true);
			prizeField.setVisible(true);
			memberField.setVisible(true);
			changeButton.setVisible(true);
			addButton.setVisible(true);
		} else {
			memberInputText.setVisible(false);
			prizeText.setVisible(false);
			prizeField.setVisible(false);
			memberField.setVisible(false);
			changeButton.setVisible(false);
			addButton.setVisible(false);
		}
	}
	
	public void fetchMembers() {
		memberText.setText("");
		db.consume_query(
				"SELECT group_id as groupID "
				+ "FROM maciejks_g78db.groups "
						+ "WHERE group_name = '" + groupName + "'",
				ts -> {
					try {
						if (ts.next()) {
							db.consume_query(
									"SELECT users_id as userID "
									+ "FROM maciejks_g78db.group_user_relation "
									+ "WHERE group_id = '" + ts.getString("groupID") + "'",
									rs -> {
										try {
											while (rs.next()) {
												db.consume_query(
														"SELECT users_username as username "
														+ "FROM maciejks_g78db.users "
														+ "WHERE users_id = '" + rs.getString("userID") + "'",
														qs -> {
															try {
																if(qs.next()) {
																	if (memberText.getText().length() < 1) {
																		memberText.setText(qs.getString("username"));
																	} else {
																		memberText.setText(memberText.getText() + "\n" + qs.getString("username"));
																	}
																	
																}
																
															}catch (SQLException e1) {
																e1.printStackTrace();
															} catch (NullPointerException en) {
																en.printStackTrace();
															}
														}
														);
											}
										}catch (SQLException e1) {
											e1.printStackTrace();
										} catch (NullPointerException en) {
											en.printStackTrace();
										}
									}
									);
						} else {
							System.out.println("Not in a group??");
						}
					}
					 catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NullPointerException en) {
						en.printStackTrace();
					}
				
				});
	}
	
	public void backButton(ActionEvent event) {
		MyGroupsController tc = new MyGroupsController(myStage, db, main);
		newScene("MyGroups.fxml", "Groups", tc);
	}
	
	public void changeButton(ActionEvent event) {
		db.query_update(
				"UPDATE groups "
				+ "SET prize = '" + prizeField.getText()
				+ "' WHERE group_name = '" + groupName + "';"
				);
		prizeLabel.setText(prizeField.getText());
	}
	
	// brukerhistorie 2 og 3 sprint 2 , issue nr 2.0 og 3.0: Legge til andre brukere i gruppen og få bruker til å være et meldem av flere grupper
	public void addButton(ActionEvent event) {
		String memberString = memberField.getText();
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
										+ "WHERE group_name = '" + groupName + "';",
										ts -> {
											try {
												if(ts.next()) {
													db.consume_query(
															"SELECT group_id as groupID, users_id as userID "
															+ "FROM maciejks_g78db.group_user_relation "
															+ "WHERE group_id = '" + ts.getString("GroupID") + "' "
															+ "AND users_id = '" + rs.getString("userID") + "'",
															ks -> {
																try {
																	//hvis ikke allerede finnes
																	if (!ks.next()) {
																		System.out.println("User NOT already in group.");
																		db.query_update("INSERT INTO group_user_relation (group_id, users_id, user_role ) "
																				+ "VALUES ('" + ts.getString("groupID") + "','"
																						+ rs.getString("userID") + "','"
																						+ medlem + "')"
																					);
																	} else {
																		System.out.println("User already in group.");
																	}
																}catch (SQLException e1) {
																	e1.printStackTrace();
																} catch (NullPointerException en) {
																	en.printStackTrace();
																}
															}
															);
												}
											}catch (SQLException e1) {
												e1.printStackTrace();
											} catch (NullPointerException en) {
												en.printStackTrace();
											}
										}
										);
							} else {
							}
						}catch (SQLException e1) {
							e1.printStackTrace();
						} catch (NullPointerException en) {
							en.printStackTrace();
						}
					}
			);
		}
		fetchMembers();
	}
	
	public void setIfAdmin() {
		db.consume_query(
				"SELECT group_id as groupID "
				+ "FROM maciejks_g78db.groups "
				+ "WHERE group_name = '" + groupName + "'",
				ts -> {
					try {
						if (ts.next()) {
							db.consume_query(
									"SELECT user_role as userRole "
									+ "FROM maciejks_g78db.group_user_relation "
									+ "WHERE group_id = '" + ts.getString("groupID") 
									+ "' AND users_id = '" + main.bruker.id + "'"
									+ "AND user_role = '" + "admin" + "'",
									rs -> {
										try {
											if (rs.next()) {
												adminLabel.setText("Admin");
											} else {
												adminLabel.setText("");
											}
										}catch (SQLException e1) {
											e1.printStackTrace();
										} catch (NullPointerException en) {
											en.printStackTrace();
										}
									}
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
	
	// brukerhistorie 4 sprint 2 , issue nr 4.0: Vise gruppens innsats mot felles mål i form av progressbar

	public void getPoints() {
		db.consume_query(
				"SELECT points_earned as pointsEarned, points_goal as pointsGoal "
				+ "FROM maciejks_g78db.groups "
						+ "WHERE group_name = '" + groupName + "'",
				ts -> {
					try {
						if (ts.next()) {
							pointsLabel.setText(ts.getInt("pointsEarned") + "/" + ts.getInt("pointsGoal") + " points!");
							
							double earned = ts.getInt("pointsEarned");
							double goal = ts.getInt("pointsGoal");
							double percent = ((earned/goal)*100);
							
							System.out.println("%: " + percent);
							String decimalString = String.format("%.2f", percent);
							distLabel.setText("");
							pctLabel.setText(decimalString + "%");
							progressBar.setProgress(percent / 100);
						} else {
							System.out.println("No current groups.");
						}
					}
					 catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NullPointerException en) {
						en.printStackTrace();
					}
				
				});
		
	}
	
	// brukerhistorie 6 og 7 sprint 2 , issue nr 6.0 og 7.0: Gruppeadministrator skal kunne opprette en gevinst for gruppens mål og 
	// se eventuell premie for målet slik at gruppen blir mest mulig motivert.
	public void fetchPrize() {
		db.consume_query(
				"SELECT prize "
				+ "FROM maciejks_g78db.groups "
						+ "WHERE group_name = '" + groupName + "'",
				ts -> {
					try {
						if (ts.next()) {
							if (ts.getString("prize").length() > 3) {
								prizeLabel.setText(ts.getString("prize"));
							} else {
								prizeLabel.setText("no prize :S .... (yet)");
							}
						}
					}
					 catch (SQLException e1) {
						e1.printStackTrace();
					} catch (NullPointerException en) {
						en.printStackTrace();
					}
				
				});
	}

	
}
