/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import java.util.List;
import java.util.Set;

/**
 *
 * @author renansantos - The Route Class represents a vehicle route for the problem
 */
public class Route {
    private double totalRouteDistance;
    private int routeTravelTime;
    private Set<Request> notServedRequests;
    private List<Node> nodesSequence;
    private List<Request> sequenceOfServedRequests;

    
    /**
     * 
     * @param totalRouteDistance - Sets the total distance traveled in the route
     * @param routeTravelTime - Sets the total travel time
     * @param notServedRequests - Sets the set of not served requests
     * @param nodesSequence - Sets the sequence of nodes in the route
     * @param sequenceOfServedRequests - Sets the sequence of passenger (request) served by the route
     */
    public Route(double totalRouteDistance, int routeTravelTime, Set<Request> notServedRequests, List<Node> nodesSequence, 
            List<Request> sequenceOfServedRequests) {
        this.totalRouteDistance = totalRouteDistance;
        this.routeTravelTime = routeTravelTime;
        this.notServedRequests = notServedRequests;
        this.nodesSequence = nodesSequence;
        this.sequenceOfServedRequests = sequenceOfServedRequests;
    }

    /**
     * 
     * @return totalRouteDistance - the total distance traveled by the route
     */
    public double getTotalRouteDistance() {
        return totalRouteDistance;
    }
    
    /**
     * 
     * @param totalRouteDistance - sets the total distance
     */
    public void setTotalRouteDistance(double totalRouteDistance) {
        this.totalRouteDistance = totalRouteDistance;
    }
   
    /**
     * 
     * @return routeTravelTime - the total time traveled by vehicle - considering
     * that one vehicle is allocated to one route
     */
    public int getRouteTravelTime() {
        return routeTravelTime;
    }
    
    /**
     * 
     * @param routeTravelTime - sets the route travel time 
     */
    public void setRouteTravelTime(int routeTravelTime) {
        this.routeTravelTime = routeTravelTime;
    }
    /**
     * 
     * @return notServedRequests - returns the set of requests that the route 
     * was not able to serve
     */
    public Set<Request> getNotServedRequests() {
        return notServedRequests;
    }

    /**
     * 
     * @param notServedRequests - sets the set of not served requests
     */
    public void setNotServedRequests(Set<Request> notServedRequests) {
        this.notServedRequests = notServedRequests;
    }

    /**
     * 
     * @return nodesSequence - returns the sequence of nodes that the vehicle 
     * will attend
     */
    public List<Node> getNodesSequence() {
        return nodesSequence;
    }

    /**
     * 
     * @param nodesSequence - sets the sequence of nodes in the route
     */
    public void setNodesSequence(List<Node> nodesSequence) {
        this.nodesSequence = nodesSequence;
    }

    /**
     * 
     * @return sequenceOfServedRequests - returns the sequence of served requests
     */
    public List<Request> getSequenceOfServedRequests() {
        return sequenceOfServedRequests;
    }

    /**
     * 
     * @param sequenceOfServedRequests - sets the sequence of requests that will be
     * attended by the route
     */
    public void setSequenceOfServedRequests(List<Request> sequenceOfServedRequests) {
        this.sequenceOfServedRequests = sequenceOfServedRequests;
    }
    
    /**
     * This method calculates the route total distance traveled
     */
    public void calculateTotalRouteDistance(){
        
    }
    
    /**
     * This method calculates the route travel time
     */
    public void calculateTravelTime(){
        
    }
    
    
    
}
