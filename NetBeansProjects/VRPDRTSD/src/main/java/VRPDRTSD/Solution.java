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
 * @author renansantos
 */
class Solution {
    private long totalDistance;
    private long totalTravelTime;
    private List<Route> routes;
    private Set<Request> nonAttendedRequests;
    
    public Solution(){
        
    }

    public Solution(long totalDistance, long totalTravelTime, List<Route> routes, Set<Request> nonAttendedRequests) {
        this.totalDistance = totalDistance;
        this.totalTravelTime = totalTravelTime;
        this.routes = routes;
        this.nonAttendedRequests = nonAttendedRequests;
    }
    
    
}
