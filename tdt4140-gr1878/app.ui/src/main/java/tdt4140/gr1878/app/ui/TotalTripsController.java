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

//Brukerhistorie 3.2 Sprint 3, Issue #41: Bruke data fra bruker til å framstille til informasjonshenter
//Lagde en tabell i databasen som innholder data for antall sykkel og løpe tur. 
//Lagde en side, Total Trips, som viser dette informasjon fra databasen ved å trykke på "Update" knappen. 

public class TotalTripsController extends MainControllerDataCollector{

	@FXML
	Button totaltripsButton;
	@FXML
	Button updateButton;
	@FXML
	private Label biketrips;
	@FXML
	private Label runs;
	@FXML
	Label biketripShow;
	@FXML
	Label runsShow;

	public TotalTripsController(Stage primaryStage, Database db, Main main) {
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

	public void totaltripsButton(ActionEvent event){
		TotalTripsController tc = new TotalTripsController(myStage, db, main);
		newScene("TotalTrips.fxml", "Log", tc);
	}

	public void updateTrips(ActionEvent event){			
		db.consume_query(
				"SELECT run as Run, bike as Bike"
						+ " FROM maciejks_g78db.total_trips "
						+ "WHERE state = '" + 1 + "';",
						ts -> {
							try {
								if (ts.next()) {
									biketripShow.setText(ts.getString("Bike"));
									runsShow.setText(ts.getString("Run"));
								} else {
									
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							} catch (NullPointerException en) {
								en.printStackTrace();
							}
						}
				);
	}
} 