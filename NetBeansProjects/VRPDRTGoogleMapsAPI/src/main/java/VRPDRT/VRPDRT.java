/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRT;

import Algorithms.Algorithms;
import static Algorithms.Algorithms.IteratedLocalSearch;
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
import InstanceReaderWithMySQL.NodeDAO;
import com.google.maps.errors.ApiException;
import java.io.IOException;

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

        String instanceName = "r050n12tw10";
        String nodesData = "bh_nodes_little";
        String adjacenciesData = "adjacencies_bh_nodes_little_test";
        final Integer numberOfVehicles = 50;
        final Integer vehicleCapacity = 10;

        //new DataUpdaterUsingGoogleMapsApi(directionsApiKey, distanceMatrixApiKey, new NodeDAO(nodesData).getListOfNodes(),
        //        adjacenciesData).updateAdjacenciesData();
        numberOfNodes = readProblemData(instanceName, nodesData, adjacenciesData, listOfRequests, distanceBetweenNodes,
                timeBetweenNodes, Pmais, Pmenos, requestsWichBoardsInNode, requestsWichLeavesInNode, setOfNodes,
                numberOfNodes, loadIndexList);

        Algorithms.printProblemInformations(listOfRequests, numberOfVehicles, vehicleCapacity, instanceName, adjacenciesData, nodesData);

        Methods.initializeFleetOfVehicles(setOfVehicles, numberOfVehicles);

        Solution solution = greedyConstructive(0.2, 0.15, 0.55, 0.10, listOfRequests, requestsWichBoardsInNode,
                requestsWichLeavesInNode, numberOfNodes, vehicleCapacity, setOfVehicles, listOfNonAttendedRequests, P,
                loadIndexList, timeBetweenNodes, distanceBetweenNodes, timeWindows, currentTime, lastNode);

        System.out.println(solution);

        IteratedLocalSearch(solution, listOfRequests, requestsWichBoardsInNode, requestsWichLeavesInNode, numberOfNodes, vehicleCapacity,
                setOfVehicles, listOfNonAttendedRequests, P, loadIndexList, timeBetweenNodes, distanceBetweenNodes, timeWindows);
        //s.getStaticMapWithAllRoutes(new NodeDAO(nodesData).getListOfNodes(), adjacenciesData, nodesData);
        solution.getStaticMapForEveryRoute(new NodeDAO(nodesData).getListOfNodes(), adjacenciesData, nodesData);
        System.out.println(solution);
    }

}
