package tdt4140.gr1878.app.ui;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.db.Database;

//import tdt4140.gr1878.app.ui.db.Database;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


// brukerhistorie 11, sprint 2 issue nr 11.4 :Lage en side som viser en graf med antall personer som har besøkt et bestemt område
public class AreaDataViewController extends MainControllerDataCollector implements Initializable{
	 	
	private List<Integer> woamount = new ArrayList<>();
	
	@FXML
	Button totaltripsButton;
	@FXML
	private AnchorPane paneView;

	@FXML
    private BarChart<?, ?>people;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @FXML 
    private Button goBack;
    
	    
    
	public AreaDataViewController(Stage primaryStage, Database db, Main main) {
		super(primaryStage, db, main);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		XYChart.Series visits = new XYChart.Series<>();
		visits.setName("Amount of logged workouts");
		
		db.consume_query(
				"SELECT workoutamount AS workoutamount FROM maciejks_g78db.areas;",
						rs -> {
							try {
								while (rs.next()) {
									woamount.add(Integer.parseInt(rs.getString("workoutamount")));
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							} catch (NullPointerException en) {
								en.printStackTrace();
							}
						});

		int trondheimAmount = woamount.get(0);
		int osloAmount = woamount.get(2);
		int stavangerAmount = woamount.get(1);
		
		
		//if setninger for område og lokasjonen skal bli lagd senere i querry til databasen etter kart er ferdig
		
		visits.getData().add(new XYChart.Data<>("Trondheim", trondheimAmount));
		visits.getData().add(new XYChart.Data<>("Oslo", osloAmount));
		visits.getData().add(new XYChart.Data<>("Stavanger", stavangerAmount));
		
		people.getData().addAll(visits);
		
	}
	
	public void totaltripsButton(ActionEvent event){
		TotalTripsController tc = new TotalTripsController(myStage, db, main);
		newScene("TotalTrips.fxml", "Log", tc);
	}
	
	public void goBack(ActionEvent event) {
		MyProfileDataCollectorController tc = new MyProfileDataCollectorController(myStage, db, main);
		newScene("MyProfileDataCollect.fxml", "MyProfileDataCollect", tc);
	}
	    
}
