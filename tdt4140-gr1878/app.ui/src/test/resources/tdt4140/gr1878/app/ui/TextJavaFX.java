package tdt4140.gr1878.app.ui;

import static org.junit.Assert.*;

import javax.swing.JPanel;

import org.junit.Rule;
import org.junit.Test;

import javafx.stage.Stage;
import tdt4140.gr1878.app.ui.Main;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
/**
 * Created by Polakken97
 * 1. mar. 2018
 */

public class TextJavaFX {

	    @Test
	    public void testA() throws InterruptedException {
	        Thread thread = new Thread(new Runnable() {

	            @SuppressWarnings("restriction")
				@Override
	            public void run() {
	                new JFXPanel(); // Initializes the JavaFx Platform
	                Platform.runLater(new Runnable() {

	                    @Override
	                    public void run() {
	                        try {
								new Main().start(new Stage());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} // Create and
	                                                        // initialize
	                                                        // your app.

	                    }
	                });
	            }
	        });
	        thread.start();// Initialize the thread
	        Thread.sleep(10000); // Time to use the app, with out this, the thread
	                                // will be killed before you can tell.
	    }

	}


