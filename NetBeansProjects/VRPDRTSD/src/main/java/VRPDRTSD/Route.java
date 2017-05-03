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
    private long totalRouteDistance;
    private long routeTravelTime;
    private Set<Request> notServedRequests;
    private List<Node> nodesSequence;
    private List<Request> sequenceOfAttendedRequests;

    public Route(long totalRouteDistance, long routeTravelTime, Set<Request> notServedRequests, List<Node> nodesSequence, 
            List<Request> sequenceOfServedRequests) {
        this.totalRouteDistance = totalRouteDistance;
        this.routeTravelTime = routeTravelTime;
        this.notServedRequests = notServedRequests;
        this.nodesSequence = nodesSequence;
        this.sequenceOfAttendedRequests = sequenceOfServedRequests;
    }

    public double getTotalRouteDistance() {
        return totalRouteDistance;
    }
 
    public void setTotalRouteDistance(long totalRouteDistance) {
        this.totalRouteDistance = totalRouteDistance;
    }

    public long getRouteTravelTime() {
        return routeTravelTime;
    }
    
    public void setRouteTravelTime(int routeTravelTime) {
        this.routeTravelTime = routeTravelTime;
    }

    public Set<Request> getNotServedRequests() {
        return notServedRequests;
    }

    public void setNotServedRequests(Set<Request> notServedRequests) {
        this.notServedRequests = notServedRequests;
    }

    public List<Node> getNodesSequence() {
        return nodesSequence;
    }

    public void setNodesSequence(List<Node> nodesSequence) {
        this.nodesSequence = nodesSequence;
    }

    public List<Request> getSequenceOfAttendedRequests() {
        return sequenceOfAttendedRequests;
    }

    public void setSequenceOfAttendedRequests(List<Request> sequenceOfAttendedRequests) {
        this.sequenceOfAttendedRequests = sequenceOfAttendedRequests;
    }
 
    public void calculateTotalRouteDistance(){
        
    }
 
    public void calculateTravelTime(){
        
    }
    
    
    
}
