/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoogleMapsApi;

/**
 *
 * @author renansantos
 */
class GeocodedWayPoint {
    private String geocoder_status;
    private String place_id;
    private String types;

    public String getGeocoder_status() {
        return geocoder_status;
    }

    public void setGeocoder_status(String geocoder_status) {
        this.geocoder_status = geocoder_status;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
    
    
}
