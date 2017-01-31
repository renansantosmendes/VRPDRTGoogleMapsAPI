/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import Algorithms.Metaheuristic;
import VRPDRTSD.IntanceReaderWithMySQL.AdjacenciesDataAcessObject;
import VRPDRTSD.IntanceReaderWithMySQL.NodeDataAcessObject;
import VRPDRTSD.IntanceReaderWithMySQL.NumberOfNodesDataAcessObject;
import VRPDRTSD.IntanceReaderWithMySQL.RequestDataAcessObject;
import java.time.Duration;
import java.util.List;

/**
 *
 * @author renansantos
 */
public class Interfaces implements Metaheuristic{
    private int numberOfNodes;
    private List<Node> nodes;
    private List<Request> listOfRequests;
    private Duration[][] duration;
    private double[][] distance;
    private String instanceName = "VRPDRTSD_requests110";

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
    public <Candidates, ProblemSolution, ProblemData> List<Candidates> InitializeCandidateElementsSet(ProblemData... data) {
        //listOfRequests.forEach(r -> r.determineFeasibility(currentTime, currentNode, duration));
        
        
        return (List<Candidates>) this.listOfRequests;
    }
    
}
