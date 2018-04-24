package tdt4140.gr1878.app.core;


import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;
import tdt4140.gr1878.app.core.GeoLocated;
import tdt4140.gr1878.app.core.GeoLocations;
import tdt4140.gr1878.app.core.GeoLocationsOwner;
import tdt4140.gr1878.app.core.LatLong;

public class TestGeoLocations {
	GeoLocationsOwner owner;
	GeoLocated geoLoc;
	
	@Test
	public void testGeoLocation() {
		GeoLocations geoLocations= new GeoLocations();
		Assert.assertEquals(0, geoLocations.size());
		Assert.assertEquals(1,new GeoLocations( new LatLong(60.0,30.0)).size());
		Assert.assertEquals(2, new GeoLocations(new LatLong(30.0,30.0), new LatLong(20.0,20.0)).size());
		
		
		
		
	}

}
