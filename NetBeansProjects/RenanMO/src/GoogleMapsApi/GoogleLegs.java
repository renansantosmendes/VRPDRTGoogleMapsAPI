/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoogleMapsApi;

import java.util.List;

/**
 *
 * @author renansantos
 */
public class GoogleLegs {

    private double distance;
    private int duration;
    private String endAddress;
    private String startAdress;
    private GoogleLocation endLocation;
    private GoogleLocation startLocation;
    private List<GoogleStep> steps;

    public GoogleLegs(double distance, int duration, String endAddress, String startAdress, GoogleLocation endLocation,
            GoogleLocation startLocation, List<GoogleStep> steps) {
        this.distance = distance;
        this.duration = duration;
        this.endAddress = endAddress;
        this.startAdress = startAdress;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
        this.steps = steps;
    }

    public double getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public String getStartAdress() {
        return startAdress;
    }

    public GoogleLocation getEndLocation() {
        return endLocation;
    }

    public GoogleLocation getStartLocation() {
        return startLocation;
    }

    public List<GoogleStep> getSteps() {
        return steps;
    }
    
    
}
