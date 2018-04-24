package tdt4140.gr1878.app.ui;

import java.io.IOException;
import static java.lang.Math.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tdt4140.gr1878.app.core.LatLong;
import tdt4140.gr1878.app.core.Type;
import tdt4140.gr1878.app.ui.db.Database;

//Brukerhistorie 3 og 4 sprint 1 : starte og slutte aktivitet, issue nr 3.1, 3.3, 4.1.
public class TrackingController {

	private Scene trackingScene;
	private boolean start = false;
	private Type type;
	private int points = 0;
	private int pressNr=1;
	public static List<Double> coordList =  new ArrayList<Double>();
	private static int routeNumber;	//Rutenummeret settes tilfeldig til 1, 2 eller 3.
	public static double distance;
	private static String areaName;
	private static String TareaName;
	static Random x = new Random();
	private static int trondheimRoute;

	int sec = 1;	
	int min = 0;
	int hours = 0;

	String st = "00";
	String mt = "00";
	String ht = "00";
	String timeString;
	protected Stage myStage;
	protected Scene logonScene;
	protected Database db;
	protected Main main;
	@FXML
	Button bicycleButton;
	@FXML
	Button runningButton;
	@FXML
	Button trackingButton;
	@FXML
	Button goBack;
	@FXML
	Label chooseActivity;
	@FXML
	Label result;
	@FXML
	Label timerLabel;
	@FXML
	Button mapButton;
	@FXML
	Label chooseGroupLabel;
	@FXML
	TextField groupnamePoints;
	@FXML 
	Button addPointsButton;

	boolean check;
	int groupPoints1;


	public TrackingController(Stage primaryStage,Database db,Main main) {
		this.myStage = primaryStage;
		this.db = db;
		this.main = main;
	}

	public static double getDistance() {
		return distance;
	}

	@SuppressWarnings("deprecation")
	public void initialize() {	
		mapButton.setVisible(false);
		chooseGroupLabel.setVisible(false);
		groupnamePoints.setVisible(false);
		addPointsButton.setVisible(false);

		myStage.sceneProperty().addListener((observable, oldval, newval) -> {
			if (newval != null && this.trackingScene == null)
				this.trackingScene = newval;
		});
	}

	public void runningButton() {
		type = Type.RUN;
	}

	public void bicycleButton() {
		type = Type.BICYCLE;
	}

	public void goBack() {
		MyActivityController tc = new MyActivityController(myStage, db, main);
		newScene("MyActivity.fxml", "Log", tc);
	}

	public void trackingButton() {
		chooseActivity.setText("You have to choose running or cycling!");
		chooseActivity.setVisible(false);
		if(pressNr==1 && type==null) {
			chooseActivity.setVisible(true);
		} else if(pressNr==1) {
			chooseActivity.setVisible(false);
			pressNr ++;
			start = true;
			changeButtonText();
			runningButton.setVisible(false);
			bicycleButton.setVisible(false);
			//oppdaterer nar treningsokten starter
			//starter timer
			start();

		}else if(pressNr==2) {
			chooseActivity.setVisible(false);
			timer.cancel();	
			setRouteNumber(x.nextInt(3)+1);
			printResult(getRouteNumber());
			registerActivity();
			mapButton.setVisible(true);
			chooseGroupLabel.setVisible(true);
			groupnamePoints.setVisible(true);
			addPointsButton.setVisible(true);
			pressNr ++;

		}else {

			MyActivityController tc = new MyActivityController(myStage, db, main);
			newScene("MyActivity.fxml", "Myactivity", tc);
			pressNr=1;
		}
	}

	public boolean checkUserInGroup() {
		int userID = main.bruker.id;
		String gName = groupnamePoints.getText();

		db.consume_query(	
				"SELECT groups.group_id, group_user_relation.relation_id, group_user_relation.users_id AS user_id"
						+ " FROM maciejks_g78db.groups JOIN maciejks_g78db.group_user_relation"
						+ " ON groups.group_id = group_user_relation.group_id WHERE group_name = '" + gName 
						+ "' AND users_id = '" + userID + "';",
						rs -> {
							try {
								if (rs.next()) {
									check = true;

								} else {
									System.out.println("error");	
									check = false;
								}
							}catch (SQLException e1) {
								e1.printStackTrace();
							} catch (NullPointerException en) {
								en.printStackTrace();
							}
						});

		return check;			
	}

	// Sjekker at (points_earned + points) er mindre enn points_goal
	public int checkPointsGoal() {
		String gName = groupnamePoints.getText();

		db.consume_query(
				"SELECT points_earned, points_goal"
						+ " FROM maciejks_g78db.groups WHERE group_name = '" + gName + "';",

						rs -> {
							try {
								if (rs.next()) {
									int maxPoints = rs.getInt("points_goal");
									groupPoints1 = rs.getInt("points_earned");
									int rest= (groupPoints1+points)-maxPoints;
									System.out.println(groupPoints1 + " "+maxPoints);
									System.out.println(rest);
									if (rest>0) {
										groupPoints1 += (groupPoints1+points)-rest;
									} else {
										groupPoints1+=points;
									}
									//Poengsummen til gruppen som skrives inn


								}
							}catch (SQLException e1) {
								e1.printStackTrace();
							} catch (NullPointerException en) {
								en.printStackTrace();
							}
						});
		return groupPoints1;
	}

	//	//Skal legge til points i group-tabellen under points_earned
	public void addPointsButton() {
		String gName = groupnamePoints.getText();
		checkUserInGroup();
		System.out.println(check);
		db.consume_query(
				"SELECT points_earned, group_id AS groupID"
						+ " FROM maciejks_g78db.groups WHERE group_name = '" + gName + "';",

						rs -> {
							try {
								if (rs.next() && checkUserInGroup()) {
									int groupPoints = checkPointsGoal();
									//groupPoints += points;//dette kommenteres ut
									//Poengsummen til gruppen som skrives inn
									db.query_update("UPDATE maciejks_g78db.groups " 
											+ "SET points_earned = '" + groupPoints
											+ "'" + " WHERE group_name = '" + gName + "';");		

									MyGroupsController mgc = new MyGroupsController(myStage, db, main);
									newScene("MyGroups.fxml", "Mygroups", mgc);	

								} else {
									System.out.println("Kan ikke legge til poeng");					
								}
							}catch (SQLException e1) {
								e1.printStackTrace();
							} catch (NullPointerException en) {
								en.printStackTrace();
							}
						});

	}




	private void changeButtonText(){
		if(start) {
			trackingButton.setText("End workout");
		}
		else {
			trackingButton.setText("Start Workout");
		}
	}


	public static List<Double> setCoordinates(double a1, double o1, double a2, double o2, double a3, double o3, double a4, double o4){
		List<Double> coordList =  new ArrayList<Double>();

		double lat1; double long1; double lat2; double long2;double lat3; double long3; double lat4; double long4; //bykoordinatvariabler
		lat1= a1;
		long1= o1;		
		lat2= a2;
		long2= o2;
		lat3= a3;
		long3= o3;		
		lat4= a4;
		long4= o4;
		coordList.add(lat1);
		coordList.add(long1);
		coordList.add(lat2);
		coordList.add(long2);
		coordList.add(lat3);
		coordList.add(long3);
		coordList.add(lat4);
		coordList.add(long4);
		return coordList;
	}

	//#3.1 og #4.0
	//metode med koordinatene til tilfeldig rute returneres i en liste. 
	public static List<Double> getCoordinates(int routeNumber, int trondheimRoute) {
		List<Double> coordList =  new ArrayList<Double>();
		if (routeNumber == 1) {
			//OSLO(Blindern) -> OSLO(Ullevaalsseter)
			coordList = setCoordinates(59.940991, 10.722582,59.958092,10.762294, 59.984760,10.752382,60.0094258, 10.712033700000006);
			setAreaName("Oslo");

		}else if (routeNumber == 2) {
			setAreaName("Trondheim");//Trondheim 
			if (trondheimRoute ==1) { // Studenterhytta til ElgesetHytta tur-retur
				coordList = setCoordinates(63.416660, 10.267171, 63.411460, 10.242201,63.418498,10.211643, 63.417763, 10.259662);
				setTrondheimAreaName("Bymarka");
				System.out.println(TareaName);
			}else if (trondheimRoute ==2) { //ladestien til Grilstadfj�ra 
				coordList = setCoordinates(63.43588829999999,10.502842500000042,63.438348,10.472729,63.446809,10.449783,63.454305,10.456007);
				setTrondheimAreaName("Ladestien");
				System.out.println(TareaName);
			}else if (trondheimRoute == 3) { //nidelven: bakklandet til Trondheim spektrum
				coordList = setCoordinates(63.43212189999999,10.406724800000006,63.420986,10.400542,63.423944,10.386629,63.42799220000001,10.375735299999974);
				setTrondheimAreaName("Nidelven");
				System.out.println(TareaName);
			}else { //geitfjellet: ila til geitfjellet
				coordList = setCoordinates(63.4307087,10.371053599999982,63.425340,10.347793,63.421807,10.313632,63.4342602,10.304367500000012);
				setTrondheimAreaName("Geitfjellet");
				System.out.println(TareaName);
			}

		}else {
			//Stavanger: hinna->paradis
			coordList = setCoordinates(58.91457639999999,5.731024199999979,58.935373,5.715498,58.952021,5.721335,58.9625084,5.74187059999997);
			setAreaName("Stavanger");

		}
		return coordList;
	}

	//Teller opp antall treningsoekter i omraadet som er valgt. 
	public void registerActivity() {
		db.consume_query(
				"SELECT workoutamount AS workoutamount "
						+ "FROM maciejks_g78db.areas WHERE areaname='" + areaName + "';",
						rs -> {
							try {
								if (rs.next()) {
									//�ker amount med 1 i databasen							
									db.query_update("UPDATE areas " 
											+ "SET workoutamount = '" + (Integer.parseInt(rs.getString("workoutamount"))+1) 
											+ "'" + " WHERE areaname = '" 
											+ getAreaName() + "';");		
								} else {
									System.out.println("Omr�det eksisterer ikke i databasen");					
								}
							}catch (SQLException e1) {
								e1.printStackTrace();
							} catch (NullPointerException en) {
								en.printStackTrace();
							}
						});

	}

	//Teller opp antall trenings�kter i byen som er valgt. 
	public void registerActivityTrondheim() {
		db.consume_query(
				"SELECT workoutamount AS workoutamount "
						+ "FROM maciejks_g78db.areas WHERE areaname='" + TareaName + "';",
						rs -> {
							try {
								if (rs.next()) {
									//�ker amount med 1 i databasen
									db.query_update("UPDATE areas " 
											+ "SET workoutamount = '" + (Integer.parseInt(rs.getString("workoutamount"))+1) 
											+ "'" + " WHERE areaname = '" 
											+ TareaName + "';");	
								} else {
									System.out.println("Omr�det eksisterer ikke i databasen");					
								}
							}catch (SQLException e1) {
								e1.printStackTrace();
							} catch (NullPointerException en) {
								en.printStackTrace();
							}
						});

	}

	private void printResult(int routeNumber) {
		List<Double> coordList =  new ArrayList<Double>();

		trondheimRoute = x.nextInt(4)+1;
		coordList = getCoordinates(routeNumber, trondheimRoute);
		System.out.println(coordList);
		DecimalFormat df = new DecimalFormat("#.##");
		double distance = LatLong.distance(coordList.get(0),coordList.get(1),coordList.get(2),coordList.get(3)) + LatLong.distance(coordList.get(2),coordList.get(3),coordList.get(4),coordList.get(5))
		+ LatLong.distance(coordList.get(4),coordList.get(5),coordList.get(6),coordList.get(7))	;

		//Bruker avstandsmetode i Latlong for � finne avstand. 
		System.out.println(distance);


		if (type==type.RUN) {	
			points= (int) Math.round(2*distance);
		}else {
			points= (int) Math.round(distance);		
		}

		/* Brukerhistorie 2.0 Sprint 3, Issue 45: Lagde et nytt kolonne på users tabell på 
		databasen som tar inn verdier fra radio knappene på MyProfile. 
		De knappene får brukeren til å velge om de vil dele informasjonen med datainnsammler.
		Endret funksjonen printResult på TrackingController som kun legger ti/henter 
		verdier til/fra databasen om bruker har trukket ja på å deling av informasjon.*/

		db.consume_query(
				"SELECT privacy"
						+ " FROM maciejks_g78db.users "
						+ "WHERE privacy = '" + 1 + "' AND users_id = '" + main.bruker.id + "';",
						rs -> {
							try {
								if (rs.next()) {

						// Brukerhistorie 3.2 Issue 41: henter verdien for antall antall sykkel og løpe tur fra databasen så øker verdien for den 
						//tilsvarende aktivitet med 1 og oppdateres total_trips tabell i databasen
									if (type==type.RUN) {
										db.consume_query(
												"SELECT users_username AS username, users_email AS email "
														+ "FROM maciejks_g78db.users WHERE users_username='" + main.bruker.username + "';",
														qs -> {
															try {
																if (qs.next()) {
																	db.consume_query(
																			"SELECT run AS Run "
																					+ "FROM maciejks_g78db.total_trips "
																					+ "WHERE state=1;",
																					ts -> {
																						try {
																							if (ts.next()) {
																								String r = ts.getString("Run");
																								int value = Integer.parseInt(r);
																								int rvalue = value + 1;

																								db.query_update(
																										"UPDATE total_trips "
																												+ "SET run = '" + rvalue + "' WHERE state=1;"); 
																							}
																						} catch (SQLException e1) {
																							e1.printStackTrace();
																						} catch (NullPointerException en) {
																							en.printStackTrace();
																						}
																					}
																			); 
																}
															} catch (SQLException e1) {
																e1.printStackTrace();
															} catch (NullPointerException en) {
																en.printStackTrace();
															}
														});
									} else {
										db.consume_query(
												"SELECT users_username AS username, users_email AS email "
														+ "FROM maciejks_g78db.users WHERE users_username='" + main.bruker.username + "';",
														qs -> {
															try {
																if (qs.next()) {
																	db.consume_query(
																			"SELECT bike AS Bike "
																					+ "FROM maciejks_g78db.total_trips "
																					+ "WHERE state=1;",
																					ts -> {
																						try {
																							if (ts.next()) {
																								String b = ts.getString("Bike");
																								int value = Integer.parseInt(b);
																								int bvalue = value + 1;
																								db.query_update(
																										"UPDATE total_trips "
																												+ "SET bike = '" + bvalue + "' WHERE state=1;"); 
																							}
																						} catch (SQLException e1) {
																							e1.printStackTrace();
																						} catch (NullPointerException en) {
																							en.printStackTrace();
																						}
																					}
																			); 
																}
															} catch (SQLException e1) {
																e1.printStackTrace();
															} catch (NullPointerException en) {
																en.printStackTrace();
															}
														});
									}
								}

							}catch (SQLException e1) {
								e1.printStackTrace();
							} catch (NullPointerException en) {
								en.printStackTrace();
							}
						});	    

		result.setText("Workout ended, you " + getActivity() +" "+ df.format(distance) + "km in " + timeString + "!" + "\n" + " Points earned: " + points);
		result.setVisible(true);
	}

	public String getActivity() {
		if (type==type.BICYCLE) {
			return "cycled";
		}else {
			return "ran";
		}
	}

	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		public void run() {


			SetTime();
			if(min == 59 && sec == 59) {
				min = 0;
				sec = 0;
				hours ++;
			} else if (sec == 59) {
				sec = 0;
				min ++;
			} else {
				sec ++;
			}
			Platform.runLater(new Runnable() {
				@Override public void run() {
					timerLabel.setText(timeString);
					System.out.println(timerLabel.getText());
				}
			});

		}
	};

	public void start() {
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}

	public void SetTime() {
		if (hours < 10) {
			ht = "0" + hours;
		} else {
			ht = "" + hours;
		}

		if (min < 10) {
			mt = ":0" + min;
		} else {
			mt = ":" + min;
		}

		if (sec < 10) {
			st = ":0" + sec;
		} else {
			st = ":" + sec;
		}
		timeString = ht + mt + st;

	}

	protected void newScene(String resource, String title, Object controller) {
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

	//Viser rute i kart, n�r man trykker p� show in map
	public void	mapButton(){
		FXMLController tc = new FXMLController(myStage, db, main);
		newScene("Map2.fxml", "Map", tc);
	}
	public static int getRouteNumber() {
		return routeNumber;
	}
	public static int getTRouteNumber() {
		return trondheimRoute;
	}

	public void setRouteNumber(int routeNumber) {
		this.routeNumber = routeNumber;
	}

	public static String getAreaName() {
		return areaName;
	}
	public static String getTAreaName() {
		return TareaName;
	}

	public static void setAreaName(String areaName) {
		TrackingController.areaName = areaName;
	}
	public static void setTrondheimAreaName(String areaName) {
		TrackingController.TareaName = areaName;
	}
}

