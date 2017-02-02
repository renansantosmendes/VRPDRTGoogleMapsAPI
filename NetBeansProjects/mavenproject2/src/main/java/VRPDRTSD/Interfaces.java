/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import Algorithms.Algorithm;
import Algorithms.Metaheuristic;
import VRPDRTSD.IntanceReaderWithMySQL.AdjacenciesDataAcessObject;
import VRPDRTSD.IntanceReaderWithMySQL.NodeDataAcessObject;
import VRPDRTSD.IntanceReaderWithMySQL.NumberOfNodesDataAcessObject;
import VRPDRTSD.IntanceReaderWithMySQL.RequestDataAcessObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author renansantos
 */
public class Interfaces implements Algorithm <Solution, Duration, List<Request>>{
    private int numberOfNodes;
    private List<Node> nodes;
    private List<Request> listOfRequests;
    private Duration[][] duration;
    private double[][] distance;
    private String instanceName = "VRPDRTSD_requests110";
    LocalDateTime currentTime = LocalDateTime.of(2017, 1, 1, 0, 0, 0);
    Node currentNode = new Node(0, 40.7143528, -74.0059731);

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Request> getListOfRequests() {
        return listOfRequests;
    }

    public Duration[][] getDuration() {
        return duration;
    }

    public double[][] getDistance() {
        return distance;
    }

    public String getInstanceName() {
        return instanceName;
    }
    
    
    
    
    @Override
    public void readInstance() {
        this.numberOfNodes = new NumberOfNodesDataAcessObject().getNumberOfNodes(this.instanceName);
        this.nodes = new NodeDataAcessObject().getListOfNodes();
        this.listOfRequests = new RequestDataAcessObject().getListOfRequestUsingNodesList(nodes, this.instanceName);
        this.duration = new AdjacenciesDataAcessObject().getDurationBetweenNodes(this.numberOfNodes);
        this.distance = new AdjacenciesDataAcessObject().getDistanceBetweenNodes(this.numberOfNodes);
    }



        @Override
    public <Candidates, ProblemSolution, ProblemData> List<Candidates> InitializeCandidateElementsSet(ProblemData[] data) {
//        List<Request> listOfRequests = new ArrayList<>();
//        Duration[][] duration = new Duration[this.numberOfNodes][];
//        for(Object object: data){
//            if(object instanceof Request){
//                listOfRequests = (List<Request>) object;
//            }else if(object instanceof Duration[][]){
//                duration = (Duration[][]) object;
//            }
//        }
        LocalDateTime currentTime = LocalDateTime.of(2017, 1, 1, 0, 0, 0);
        Node currentNode = new Node(0, 40.7143528, -74.0059731);
        for(Request request: this.listOfRequests){
            request.determineFeasibility(currentTime, currentNode, this.duration);
        }
        return (List<Candidates>) this.listOfRequests;
    }

    
    
    @Override
    public <ProblemData> void testMethod() {
        LocalDateTime currentTime = LocalDateTime.of(2017, 1, 1, 0, 0, 0);
        Node currentNode = new Node(0, 40.7143528, -74.0059731);
        for(Request request: this.listOfRequests){
            request.determineFeasibility(currentTime, currentNode, this.duration);
        }
        
    }

    @Override
    public <Candidates> Candidates initializeCandidatesElementsSet() {
        
        for(Request request: this.listOfRequests){
            request.determineFeasibility(currentTime, currentNode, this.duration);
        }
        return (Candidates) this.listOfRequests;
    }

    @Override
    public <Candidates> Candidates actualizeCandidatesElementsSet() {
        return (Candidates) this.listOfRequests.subList(0, this.listOfRequests.size()/2);
    }
    
}
