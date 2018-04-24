package tdt4140.gr1878.app.core;


import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;
import tdt4140.gr1878.app.core.TimedTaggedImpl;

public class TestTimeTagged {
	
	
	@Test
	public void testvalueOf() {
		
	TimedTaggedImpl timeTagged= new TimedTaggedImpl();
		Assert.assertEquals("<en,to>", timeTagged.valueOf("en,to", ",").getTags("<",",",">"));
		Assert.assertEquals("<en,to>", timeTagged.valueOf("en:to", ":").getTags("<",",",">"));
		
	}
	@Test
	public void testGetTags() {
		TimedTaggedImpl timeTagged= new TimedTaggedImpl();
		Assert.assertEquals(0, timeTagged.getTags().length);
		timeTagged.setTags("en","to","tre");
		String [] res1= timeTagged.getTags();
		Assert.assertEquals(3,timeTagged.getTags().length);
		timeTagged.setTags("en","to","tre","tre");
		String [] res2 =timeTagged.getTags();
		Assert.assertEquals(3,timeTagged.getTags().length);
		
	}
	
	@Test
	public void testSetTags() {
		TimedTaggedImpl timeTagged= new TimedTaggedImpl();
		timeTagged.setTags("en");
		Assert.assertTrue(timeTagged.hasTags("en"));
		timeTagged.setTags("tre");
		Assert.assertFalse(timeTagged.hasTags("en"));
		Assert.assertTrue(timeTagged.hasTags("tre"));
		
		
	}
	@Test
	public void testAddTags() {
		TimedTaggedImpl timeTagged= new TimedTaggedImpl();	
		timeTagged.addTags("en");
		Assert.assertEquals(1, timeTagged.getTags().length);	
		Assert.assertTrue(timeTagged.hasTags("en"));
		timeTagged.addTags("fire");
		Assert.assertTrue(timeTagged.hasTags("en","fire"));
	}
	
	@Test 
	public void TestRemoveTags() {
		TimedTaggedImpl timeTagged= new TimedTaggedImpl();	
		timeTagged.addTags("en");
		timeTagged.removeTags("en");
		Assert.assertFalse(timeTagged.hasTags("en"));
		timeTagged.addTags("en","to","tre");
		timeTagged.removeTags("en");
		Assert.assertTrue(timeTagged.hasTags("to","tre"));
	}

}
