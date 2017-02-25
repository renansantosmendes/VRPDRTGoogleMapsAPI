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
}
