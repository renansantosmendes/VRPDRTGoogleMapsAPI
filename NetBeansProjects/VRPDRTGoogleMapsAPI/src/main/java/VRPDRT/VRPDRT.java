/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRT;

import Algorithms.Algorithms;
import static Algorithms.Algorithms.IteratedLocalSearch;
import static Algorithms.Algorithms.generateInitialPopulation;
import static Algorithms.Algorithms.generateRandomSolutionsUsingPerturbation;
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
import static Algorithms.AlgorithmsForMultiObjectiveOptimization.NonDominatedSortedGeneticAlgorithmII;
import GoogleMapsApi.GoogleStaticMap;

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
    static List<Request> requestList = new ArrayList<>();

    //-------------------Test--------------------------------
    static Long currentTime;
    static Integer lastNode;

    public static void main(String[] args) throws ApiException, InterruptedException, IOException {
        String directionsApiKey = "AIzaSyD9W0em7H723uVOMD6QFe_1Mns71XAi5JU";
        String instanceName = "r100n12tw10";
        String nodesData = "bh_n12s";
        String adjacenciesData = "bh_adj_n12s";
        final Integer numberOfVehicles = 50;
        final Integer vehicleCapacity = 4;
        Integer populationSize = 100;
        Integer maximumNumberOfGenerations = 15;
        Integer maximumNumberOfExecutions = 1;
        double probabilityOfMutation = 0.02;
        double probabilityOfCrossover = 0.7;

//        new DataUpdaterUsingGoogleMapsApi(directionsApiKey, new NodeDAO(nodesData).getListOfNodes(),
//                adjacenciesData).updateAdjacenciesData();
        numberOfNodes = readProblemData(instanceName, nodesData, adjacenciesData, listOfRequests, distanceBetweenNodes,
                timeBetweenNodes, Pmais, Pmenos, requestsWichBoardsInNode, requestsWichLeavesInNode, setOfNodes,
                numberOfNodes, loadIndexList);

        Algorithms.printProblemInformations(listOfRequests, numberOfVehicles, vehicleCapacity, instanceName, adjacenciesData, nodesData);

        Methods.initializeFleetOfVehicles(setOfVehicles, numberOfVehicles);

        Solution solution = new Solution(Algorithms.greedyConstructive(0.20, 0.15, 0.55, 0.10, listOfRequests,
                requestsWichBoardsInNode, requestsWichLeavesInNode, numberOfNodes, vehicleCapacity, setOfVehicles,
                listOfNonAttendedRequests, requestList, loadIndexList, timeBetweenNodes, distanceBetweenNodes,
                timeWindows, currentTime, lastNode));

        //solution.getStaticMapForEveryRoute(new NodeDAO(nodesData).getListOfNodes(), adjacenciesData, nodesData);
        //new GoogleStaticMap(new NodeDAO(nodesData).getListOfNodes(), adjacenciesData, nodesData).getStaticMapForInstance();
        //solution.getStaticMapWithAllRoutes(new NodeDAO(nodesData).getListOfNodes(), adjacenciesData, nodesData);
        System.out.println(solution);

        solution.getSetOfRoutes()
                .forEach(r -> System.out.println(r.getVehicleOccupationWhenLeavesNode()
                .stream()
                .mapToDouble(Integer::valueOf)
                .average().getAsDouble() / vehicleCapacity));
        
        //solution.getSetOfRoutes().stream().flatMap(r -> r.getVehicleOccupationWhenLeavesNode().stream()).distinct().forEach(System.out::println);

        //System.out.println(distanceBetweenNodes);
//        NonDominatedSortedGeneticAlgorithmII(populationSize, maximumNumberOfGenerations,maximumNumberOfExecutions,
//                probabilityOfMutation,  probabilityOfCrossover, listOfRequests, requestsWichBoardsInNode, requestsWichLeavesInNode,
//                numberOfNodes, vehicleCapacity, setOfVehicles, listOfNonAttendedRequests, requestList, loadIndexList, timeBetweenNodes,
//                distanceBetweenNodes, timeWindows, currentTime, lastNode);
//        
    }

}
