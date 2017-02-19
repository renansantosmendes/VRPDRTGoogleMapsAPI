/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoogleMapsApi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author renansantos
 */
public class DirectionsResponse {
    List<GeocodedWayPoint> geocoded_waypoints;

    public DirectionsResponse() {
        this.geocoded_waypoints = new ArrayList<>();
    }

        
    public List<GeocodedWayPoint> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(List<GeocodedWayPoint> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }
    
}
