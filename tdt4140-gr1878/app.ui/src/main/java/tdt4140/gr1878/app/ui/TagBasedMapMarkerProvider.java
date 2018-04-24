package tdt4140.gr1878.app.ui;

import tdt4140.gr1878.app.core.GeoLocated;
import tdt4140.gr1878.app.core.GeoLocations;

public class TagBasedMapMarkerProvider implements IMapMarkerProvider {

	@Override
	public MapMarker getMapMarker(GeoLocated geoLoc, GeoLocations geoLocations) {
		MapMarker mapMarker = new MapMarker(geoLoc.getLatLong());
		return mapMarker;
	}
}
