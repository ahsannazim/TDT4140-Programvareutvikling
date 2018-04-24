package tdt4140.gr1878.app.core;


import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;
import tdt4140.gr1878.app.core.GeoLocation;
import tdt4140.gr1878.app.core.LatLong;

public class TestGeoLocation {

	@Test
	public void testsetGeolocation() {
		GeoLocation geolocation= new GeoLocation();
		LatLong latLong= new LatLong(60.0,30.0);
		geolocation.setLatLong(latLong);
		Assert.assertEquals(latLong, geolocation.getLatLong());
		
		
	}

}
