/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRT;

import static Algorithms.Algorithms.GRASP_reativo;
import static Algorithms.Algorithms.GeneticAlgorithm;
import static Algorithms.Algorithms.ILS;
import static Algorithms.Algorithms.VND;
import static Algorithms.Algorithms.floydWarshall;
import static Algorithms.Algorithms.greedyConstructive;
import static Algorithms.AlgorithmsForMultiObjectiveOptimization.NSGAII;
import static Algorithms.AlgorithmsForMultiObjectiveOptimization.SPEA2;
import static Algorithms.Methods.ImprimePopulacao;
import static Algorithms.Methods.InicializaPopulacao;
import static Algorithms.Methods.InicializaPopulacaoPerturbacao;
import static Algorithms.Methods.NewFitness;
import static Algorithms.Methods.loaderProblem;
import InstanceReaderWithMySQL.AdjacenciesDAO;
import InstanceReaderWithMySQL.NodeDAO;
import InstanceReaderWithMySQL.RequestDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ProblemRepresentation.Request;
import ProblemRepresentation.Solution;
import static Algorithms.Methods.loadAdjacencies;
import static Algorithms.Methods.readProblemData;
import GoogleMapsApi.StaticGoogleMap;
import java.io.IOException;

/**
 *
 * @author Renan
 */
public class VRPDRT {

    /**
     * @param args the command line arguments
     */
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
    static Integer numberOfVehicles = 50;
    static Set<Integer> setOfVehicles = new HashSet<>();
    static Integer vehicleCapacity = 10;
    static List<Request> listOfNonAttendedRequests = new ArrayList<>();
    static List<Request> P = new ArrayList<>();

    //-----------------Teste--------------------------------
    static Integer currentTime;
    static Integer lastNode;

    public static void main(String[] args) throws IOException {

        numberOfNodes = readProblemData(listOfRequests, distanceBetweenNodes, timeBetweenNodes, Pmais, Pmenos, requestsWichBoardsInNode,
                requestsWichLeavesInNode, setOfNodes, numberOfNodes, loadIndexList);

        System.out.println("Quantidade de veículos = " + numberOfVehicles);
        System.out.println("Capacidade = " + vehicleCapacity);

        // Conj. de veiculos, todos com mesma capacidade
        Set<Integer> setOfVehicles = new HashSet<>(vehicleCapacity);
        for (int i = 0; i < vehicleCapacity; i++) {
            setOfVehicles.add(i);
        }

        Solution s = greedyConstructive(0.2, 0.15, 0.55, 0.10, listOfRequests.subList(0, 50), requestsWichBoardsInNode, requestsWichLeavesInNode,
                numberOfNodes, vehicleCapacity, setOfVehicles, listOfNonAttendedRequests, P, loadIndexList, timeBetweenNodes,
                distanceBetweenNodes, TimeWindows, currentTime, lastNode);
    
        System.out.println(s.getConjRotas());
        System.out.println(s);
        
        //listOfRequests.forEach(System.out::println);
        Set<List<Integer>> setTest = new HashSet<>();
        for(List<Integer> route : s.getRoutesForMap() ){
            setTest.add(route);
            new StaticGoogleMap(new NodeDAO("bh_nodes_2").getListOfNodes(),setTest).buildMapInWindow();
            setTest.clear();
        }
        
        
        //new StaticGoogleMap(new NodeDAO("bh_nodes_2").getListOfNodes(),s.getRoutesForMap()).buildMapInWindow();

    }
}   
