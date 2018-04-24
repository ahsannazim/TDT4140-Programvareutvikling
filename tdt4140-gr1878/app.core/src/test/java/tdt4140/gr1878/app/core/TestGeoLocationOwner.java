package tdt4140.gr1878.app.core;


import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import junit.framework.Assert;
import tdt4140.gr1878.app.core.GeoLocations;
import tdt4140.gr1878.app.core.GeoLocationsOwner;

public class TestGeoLocationOwner {
	
	@Test
	public void testgetGeoLocations1() {
		GeoLocationsOwner owner= new GeoLocationsOwner();	
		Assert.assertNull(owner.getGeoLocations("one"));
		GeoLocations hello =new GeoLocations("one");
		owner.addGeolocations(hello);
		Assert.assertSame(owner.getGeoLocations("one"),hello);
		Assert.assertNotNull(owner.getGeoLocations("one"));
		GeoLocations hei= new GeoLocations("two");
		owner.addGeolocations(hei);
		Assert.assertSame(owner.getGeoLocations("two"),hei);
		Assert.assertSame(owner.getGeoLocations("one"), hello);
	}
	

	@Test
	public void testgetGeoLocations2() {
		GeoLocationsOwner owner= new GeoLocationsOwner();
		GeoLocations hei= new GeoLocations("en");
		Assert.assertEquals(0,owner.getGeoLocations().size());
		owner.addGeolocations(hei);
		check(owner.getGeoLocations(new String []{"en"}),hei);
		check(owner.getGeoLocations((String []) null), hei);
	}
	
	@Test
	public void testhasGeoLocations() {
		GeoLocationsOwner owner= new GeoLocationsOwner();
		Assert.assertTrue(owner.hasGeoLocations());
		Assert.assertFalse(owner.hasGeoLocations("en"));
		owner.addGeolocations(new GeoLocations("en"));
		Assert.assertTrue(owner.hasGeoLocations("en"));
		owner.addGeolocations(new GeoLocations("to"));
		Assert.assertTrue(owner.hasGeoLocations("en","to"));
		
	}
	
	@Test
	public void testaddGeolocations() {
		GeoLocationsOwner owner= new GeoLocationsOwner();
		owner.addGeolocations(new GeoLocations("en"));
		Assert.assertTrue(owner.hasGeoLocations("en"));
		Assert.assertFalse(owner.hasGeoLocations("to"));
		
		
	}
	@Test
	public void testremoveGeolocations() {
		GeoLocationsOwner owner= new GeoLocationsOwner();
		GeoLocations hei= new GeoLocations("en");
		GeoLocations ho= new GeoLocations("to");
		GeoLocations he= new GeoLocations("tre");
		owner.addGeolocations(hei);
		owner.addGeolocations(ho);
		owner.addGeolocations(he);
		Assert.assertEquals(3, owner.getGeoLocations((String[]) null).size());
		owner.removeGeolocations("to");
		//check(owner.getGeoLocations((String []) null),true, hei );
		owner.removeGeolocations("tre");
		Assert.assertEquals(1,owner.getGeoLocations((String[])null).size());
	
	}
	private static int size(Iterable<?> iterable) {
		int size = 0;
		for (Iterator<?> it = iterable.iterator(); it.hasNext(); it.next()) {
			size++;
		}
		return size;
	}
	

	@Test
	public void testgetGeoLocationsName() {
		GeoLocationsOwner owner= new GeoLocationsOwner();
		Assert.assertEquals(0,size(owner.getGeoLocationsNames()));
		owner.addGeolocations(new GeoLocations("en"));
		check(owner.getGeoLocationsNames(),true,"en");
		Assert.assertEquals(1,size(owner.getGeoLocationsNames()));
	}
	

	
	private static <T> void check(Iterable<T> iterable, boolean anyOrder, T... t) {
		Collection<T> all;
		if (anyOrder ==true){
			all= Arrays.asList(t);
		} else{
			all=null;
		}
		int num = 0;
		for (Iterator<T> it = iterable.iterator(); it.hasNext(); num++) {
			Assert.assertTrue(num < t.length);
			T next = it.next();
			if (anyOrder) {
				assertTrue(all.contains(next));
			} else {
				Assert.assertEquals(t[num], next);
			}
		}
		Assert.assertTrue(num == t.length);
	}
	
	private static <T> void check(Iterable<T> iterable, T...t) {
		check(iterable,false,t);

	}
	
	
}
