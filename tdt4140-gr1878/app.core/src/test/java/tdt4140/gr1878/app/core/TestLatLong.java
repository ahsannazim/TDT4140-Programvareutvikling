package tdt4140.gr1878.app.core;


import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;
import tdt4140.gr1878.app.core.LatLong;

public class TestLatLong {

	@Test
	public void testToString() {
	LatLong latLong= new LatLong(63.3,57.3);
	Assert.assertEquals(Double.toString(63.3)+"," +Double.toString(57.3), latLong.toString());
}
	@Test
	public void testEquals() {
		Assert.assertEquals(new LatLong(63.0,30.0),(new LatLong(63.0,30.0)));
		Assert.assertFalse(new LatLong(60.0,30.0).equals(new LatLong(40.0,30.0)));
		Assert.assertFalse(new LatLong(63.0,30.0).equals(null));
		Assert.assertFalse(new LatLong(63.0,30.0).equals("63.0,30.0"));
	}

}
