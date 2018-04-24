package tdt4140.gr1878.app.ui;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import junit.framework.Assert;
import tdt4140.gr1878.app.core.User;

public class MakeuserPageTest extends ApplicationTest {
	@FXML
	TextField username;
	@FXML
	TextField password;
	@FXML
	TextField password1;
	@FXML
	TextField email;
	
	@FXML
	Button goBack;
	User person;
	@FXML
	Button registerButton;
	@FXML
	RadioButton radioCollect;
	

	
	@FXML 
	Label statusLabel;
	public static Stage stage;
	
	 @BeforeClass
	 //Denne gjør at gitlab ikke kjører disse testene fordi gitlab ikke har mulighet til å kjøre siden som er vår applikasjon.
	    public static void headless() {
	    		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
	    			GitlabCISupport.headless();
	    		}
	    }
	

	public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Makeuser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MakeuserPageTest.stage = stage;
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }
    
    
    public <T extends Node> T find(final String query) {
        /** TestFX provides many operations to retrieve elements from the loaded GUI. */
        return lookup(query).query();
    }
    @Before
    public void setUp() {
        
        goBack = find("#goBack");
        radioCollect = find("#radioCollect");
        username = find("#usernameField");
        password = find("#passwordField");
        password1 = find("#passwordField1");
        email=find("#emailField");
        registerButton=find("#registerButton");
        
    }
    
    @Test
    public void testUsername() {
    		username.setText("testusername");
    		Assert.assertEquals(username.getText(), "testusername");
    		Assert.assertEquals(username.getPromptText(), "Username");
    }
    @Test
    public void testPassword() {
    		password.setText("123");
    		password1.setText("456");
    		Assert.assertEquals(password.getText(), "123");
    		Assert.assertEquals(password.getPromptText(), "Password");
    		Assert.assertEquals(password1.getText(), "456");
    		Assert.assertEquals(password1.getPromptText(),"Verify password");
    		
    }
    @Test
    public void testRegisterButtonText() {
    		Assert.assertEquals(registerButton.getText(), "Register user");
    }
    
    @Test
    public void testGoBackButton() {
    		Assert.assertEquals(goBack.getText(), "Go back");
    		clickOn(goBack);
    		String title = MakeuserPageTest.stage.getTitle();
    		Assert.assertEquals(title, "Log");
    		//Login siden som knappen goback skifter til heter "Log". Dette tester dermed at vi skifter til rett side.
    		
    		
    }
    @Test 
    public void testEmail() {
    		email.setText("test@gmail.com");
    		Assert.assertEquals(email.getText(),"test@gmail.com");
    		Assert.assertEquals(email.getPromptText(),"Email");
    }
    @Test
    public void testRadioCollect() {
    		clickOn(radioCollect);
    		Assert.assertTrue( ( (RadioButton) find("#radioCollect")).isSelected());
    	
    }
    
    @After
    public void tearDown() throws TimeoutException {
        // Lukker vinduet etter hver test. Det åpnes for hver test.  
        FxToolkit.hideStage();
    }
    
	

}