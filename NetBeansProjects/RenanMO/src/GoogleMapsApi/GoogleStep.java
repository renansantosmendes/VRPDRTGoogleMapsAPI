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
class GoogleStep {

    private double distance;
    private int duration;
    private GoogleLocation startLocation;
    private GoogleLocation endLocation;
    private String htmlInstructions;
    private String polyline;
    private String travelMode;

    public GoogleStep(double distance, int duration, GoogleLocation startLocation, GoogleLocation endLocation,
            String htmlInstructions, String polyline, String travelMode) {
        this.distance = distance;
        this.duration = duration;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.htmlInstructions = htmlInstructions;
        this.polyline = polyline;
        this.travelMode = travelMode;
    }

    public double getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public GoogleLocation getEndLocation() {
        return endLocation;
    }

    public GoogleLocation getStartLocation() {
        return startLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public String getPolyline() {
        return polyline;
    }

    public String getTravelMode() {
        return travelMode;
    }
    
    @Override
    public String toString(){
        return "Step\n " + "start_location: "+ this.startLocation + "\n end_location: " + this.endLocation
                + "\n duration: " + this.duration + "\tdistance: " + this.distance + "\n polyline: " + this.polyline;
    }

}
