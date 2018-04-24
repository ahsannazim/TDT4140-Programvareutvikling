package tdt4140.gr1878.app.core;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class testValidEmail {
	
	User u1 = new User(1,"test","test.tester","test.tester@test.test","123",121296,80,180,1);
	
	@Test 
	public void testSetUnvalidEmailMissingAt() {
		String res =u1.setEmail("test.tester");
		Assert.assertEquals(res,"Your email must contain: '@'");
		
	}
	@Test 
	public void testSetUnvalidEmailWrongDomain() {
		String res =u1.setEmail("test.tester@abc");
		Assert.assertEquals(res,"The domain name in your email must contain: '.'");
	}
	@Test 
	public void testSetValidEmail() {
		String res = u1.setEmail("test.tester@gmail.com");
		Assert.assertEquals(res, "");
	}
	

}

