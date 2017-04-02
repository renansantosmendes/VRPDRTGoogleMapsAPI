/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRT;

import Algorithms.Algorithms;
import static Algorithms.Algorithms.IteratedLocalSearch;
import static Algorithms.Algorithms.geraPesos;
import static Algorithms.Algorithms.greedyConstructive;
import Algorithms.Methods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ProblemRepresentation.Request;
import ProblemRepresentation.Solution;
import static Algorithms.Methods.readProblemData;
import GoogleMapsApi.DataUpdaterUsingGoogleMapsApi;
import InstanceReaderWithMySQL.DataUpdaterDAO;
import InstanceReaderWithMySQL.NodeDAO;
import java.io.IOException;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TrafficModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import static Algorithms.Algorithms.perturbation;

/**
 *
 * @author renansantos
 */
public class VRPDRT {

    final static Long timeWindows = (long) 3;
    static List<Request> listOfRequests = new ArrayList<>();
    static List<List<Integer>> listOfAdjacencies = new LinkedList<>();
    static List<List<Long>> distanceBetweenNodes = new LinkedList<>();
    static List<List<Long>> timeBetweenNodes = new LinkedList<>();
    static Set<Integer> Pmais = new HashSet<>();
    static Set<Integer> Pmenos = new HashSet<>();
    static Set<Integer> setOfNodes = new HashSet<>();
    static int numberOfNodes;
    static Map<Integer, List<Request>> requestsWichBoardsInNode = new HashMap<>();
    static Map<Integer, List<Request>> requestsWichLeavesInNode = new HashMap<>();
    static List<Integer> loadIndexList = new LinkedList<>();
    static Set<Integer> setOfVehicles = new HashSet<>();
    static List<Request> listOfNonAttendedRequests = new ArrayList<>();
    static List<Request> P = new ArrayList<>();

    //-------------------Test--------------------------------
    static Long currentTime;
    static Integer lastNode;

    public static void main(String[] args) throws ApiException, InterruptedException, IOException {
        String directionsApiKey = "AIzaSyCgaZr9fRAUs3_8lftkt026_MfZ3yZVN4E";
        String distanceMatrixApiKey = "AIzaSyDP6omcIkKRzcraIm4sna5QeEybpbi2Ojw";
        String origin = "-19.9138637,-43.9419221";
        String destination = "-19.9165861,-43.9346433";
        String instanceName = "requests110";
        String nodesData = "bh_nodes_little";
        String adjacenciesData = "adjacencies_bh_nodes_little_test";
        final Integer numberOfVehicles = 50;
        final Integer vehicleCapacity = 10;

        System.out.println("Number of Vehicles = " + numberOfVehicles);
        System.out.println("Vehicle Capacity = " + vehicleCapacity);

        //new DataUpdaterUsingGoogleMapsApi(directionsApiKey, distanceMatrixApiKey, new NodeDAO(nodesData).getListOfNodes(),
        //        adjacenciesData).updateAdjacenciesData();
        numberOfNodes = readProblemData(instanceName, nodesData, adjacenciesData, listOfRequests, distanceBetweenNodes,
                timeBetweenNodes, Pmais, Pmenos, requestsWichBoardsInNode, requestsWichLeavesInNode, setOfNodes,
                numberOfNodes, loadIndexList);

        Methods.initializeFleetOfVehicles(setOfVehicles, numberOfVehicles);

        Solution solution = greedyConstructive(0.2, 0.15, 0.55, 0.10, listOfRequests.subList(0, 110), requestsWichBoardsInNode,
                requestsWichLeavesInNode, numberOfNodes, vehicleCapacity, setOfVehicles, listOfNonAttendedRequests, P,
                loadIndexList, timeBetweenNodes, distanceBetweenNodes, timeWindows, currentTime, lastNode);

        //System.out.println(solution.getSetOfRoutes());
        //System.out.println(solution.getStringWIthObjectives());
        //        IteratedLocalSearch(s, listOfRequests, requestsWichBoardsInNode,requestsWichLeavesInNode,numberOfNodes, vehicleCapacity, 
        //              setOfVehicles, listOfNonAttendedRequests, P, loadIndexList, timeBetweenNodes, distanceBetweenNodes, timeWindows);
        //s.getStaticMapWithAllRoutes(new NodeDAO(nodesData).getListOfNodes(), adjacenciesData, nodesData);
        //s.getStaticMapForEveryRoute(new NodeDAO(nodesData).getListOfNodes(), adjacenciesData, nodesData);
        int numberOfRandomSolutions = 10000;


    }

    
}
