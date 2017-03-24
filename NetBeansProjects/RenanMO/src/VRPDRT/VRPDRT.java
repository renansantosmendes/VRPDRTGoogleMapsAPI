/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRT;
import static Algorithms.Algorithms.IteratedLocalSearch;
import static Algorithms.Algorithms.greedyConstructive;
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
import java.io.IOException;
/**
 *
 * @author Renan
 */
public class VRPDRT {
    final static Integer TimeWindows = 3;
    static List<Request> listOfRequests = new ArrayList<>();
    static List<List<Integer>> listOfAdjacencies = new LinkedList<>();
    static List<List<Integer>> distanceBetweenNodes = new LinkedList<>();
    static List<List<Integer>> timeBetweenNodes = new LinkedList<>();
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
    static Integer currentTime;
    static Integer lastNode;

    public static void main(String[] args) throws IOException {
        final Integer numberOfVehicles = 6;
        final Integer vehicleCapacity = 10;
        String instanceName = "requests110";
        String nodesData = "bh_nodes_little";
        String adjacenciesData = "adjacencies_bh_nodes_little";//"adjacencies_vrpdrt"

        numberOfNodes = readProblemData(instanceName, nodesData, adjacenciesData, listOfRequests, distanceBetweenNodes,
                timeBetweenNodes, Pmais, Pmenos, requestsWichBoardsInNode, requestsWichLeavesInNode, setOfNodes,
                numberOfNodes, loadIndexList);

        System.out.println("Number of Vehicles = " + numberOfVehicles);
        System.out.println("Vehicle Capacity = " + vehicleCapacity);

        Set<Integer> setOfVehicles = new HashSet<>(vehicleCapacity);
        for (int i = 0; i < numberOfVehicles; i++) {
            setOfVehicles.add(i);
        }

        Solution s = greedyConstructive(0.2, 0.15, 0.55, 0.10, listOfRequests.subList(0, 50), requestsWichBoardsInNode, requestsWichLeavesInNode,
                numberOfNodes, vehicleCapacity, setOfVehicles, listOfNonAttendedRequests, P, loadIndexList, timeBetweenNodes,
                distanceBetweenNodes, TimeWindows, currentTime, lastNode);

        System.out.println(s.getSetOfRoutes());
        System.out.println(s);
        System.out.println(s.getNonAttendedRequestsList());
        
//        IteratedLocalSearch(s, listOfRequests, requestsWichBoardsInNode,requestsWichLeavesInNode,numberOfNodes, vehicleCapacity, 
//                setOfVehicles, listOfNonAttendedRequests, P, loadIndexList, timeBetweenNodes, distanceBetweenNodes, TimeWindows);
        
        
        s.getStaticMapForEveryRoute(nodesData);
        
        
    }
}
