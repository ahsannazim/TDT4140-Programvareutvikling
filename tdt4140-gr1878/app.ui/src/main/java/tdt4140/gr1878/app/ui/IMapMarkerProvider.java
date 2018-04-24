package tdt4140.gr1878.app.ui;

import tdt4140.gr1878.app.core.GeoLocated;
import tdt4140.gr1878.app.core.GeoLocations;

public interface IMapMarkerProvider {

	public MapMarker getMapMarker(GeoLocated geoLoc, GeoLocations geoLocations);
}
