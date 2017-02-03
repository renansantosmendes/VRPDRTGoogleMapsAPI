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
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author renansantos
 */
public class VRPDRTSD implements Algorithm<Solution, Duration, List<Request>, Request> {

    private int numberOfNodes;
    private List<Node> nodes;
    private List<Request> listOfRequests;
    private Duration[][] duration;
    private double[][] distance;
    private String instanceName = "VRPDRTSD_requests110";
    private LocalDateTime currentTime = LocalDateTime.of(2017, 1, 1, 0, 0, 0);
    private Node currentNode = new Node(0, 40.7143528, -74.0059731);
    private Request lastPassengerAddedToRoute;

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
    public <Candidates> Candidates initializeCandidatesElementsSet() {
        for (Request request : this.listOfRequests) {
            request.determineFeasibility(currentTime, currentNode, this.duration);
        }
        return (Candidates) this.listOfRequests;
    }

    @Override
    public <Candidates> Candidates actualizeCandidatesElementsSet() {
        this.lastPassengerAddedToRoute = this.listOfRequests.get(0);
        this.listOfRequests.remove(0);
        this.currentNode = this.lastPassengerAddedToRoute.getPassengerOrigin();
        return (Candidates) this.listOfRequests;
    }

    @Override
    public <Candidate> Candidate findBestCandidate() {
        this.listOfRequests.sort(Comparator.comparing(Request::getRequestRankingFunction).reversed());
        return (Candidate) this.listOfRequests.get(0);
    }

    @Override
    public <Candidate, ProblemSolution> void addCandidateIntoSolution(ProblemSolution solution, Candidate candidate) {

    }

}
