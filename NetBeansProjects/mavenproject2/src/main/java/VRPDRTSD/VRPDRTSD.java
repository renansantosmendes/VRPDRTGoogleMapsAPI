/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import Algorithms.Algorithm;
import Algorithms.Metaheuristic;
import VRPDRTSD.InstanceReaderWithMySQL.AdjacenciesDAO;
import VRPDRTSD.InstanceReaderWithMySQL.NodeDAO;
import VRPDRTSD.InstanceReaderWithMySQL.NumberOfNodesDAO;
import VRPDRTSD.InstanceReaderWithMySQL.RequestDAO;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private String nodesInstanceName = "bh_nodes_2";
    private LocalDateTime currentTime = LocalDateTime.of(2017, 1, 1, 0, 0, 0);
    private Node currentNode = new Node(0, 40.7143528, -74.0059731,"Av. do Contorno, 340 - Santa Efigênia, Belo Horizonte - MG, 30110-017");
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

    public Node getCurrentNode() {
        return currentNode;
    }

    @Override
    public void readInstance() {
        this.numberOfNodes = new NumberOfNodesDAO().getNumberOfNodes(this.nodesInstanceName);
        this.nodes = new NodeDAO().getListOfNodes();
        this.listOfRequests = new RequestDAO().getListOfRequestUsingNodesList(nodes, this.instanceName);
        this.duration = new AdjacenciesDAO().getDurationBetweenNodes(this.numberOfNodes);
        this.distance = new AdjacenciesDAO().getDistanceBetweenNodes(this.numberOfNodes);
    }

    @Override
    public <Candidates> Candidates initializeCandidatesElementsSet() {
        for (Request request : this.listOfRequests) {
            request.determineFeasibility(currentTime, currentNode, this.duration);
        }

        listOfRequests.forEach(r -> r.setDistanceToAttendThisRequest(currentNode, distance));
        double maxDistance = listOfRequests.stream()
                .mapToDouble(Request::getDistanceToAttendThisRequest).max().getAsDouble();
        double minDistance = listOfRequests.stream()
                .mapToDouble(Request::getDistanceToAttendThisRequest).min().getAsDouble();

        int minTimeWindowLower = listOfRequests.stream()
                .mapToInt(Request::getDeliveryTimeWindowLowerInMinutes).min().getAsInt();
        int maxTimeWindowLower = listOfRequests.stream()
                .mapToInt(Request::getDeliveryTimeWindowLowerInMinutes).max().getAsInt();
        int minTimeWindowUpper = listOfRequests.stream()
                .mapToInt(Request::getDeliveryTimeWindowUpperInMinutes).min().getAsInt();
        int maxTimeWindowUpper = listOfRequests.stream()
                .mapToInt(Request::getDeliveryTimeWindowUpperInMinutes).max().getAsInt();

        Map<Node, List<Request>> requestsWichBoardsInNode = listOfRequests.stream()
                .collect(Collectors.groupingBy(Request::getPassengerOrigin));

        Map<Node, List<Request>> requestsWichLeavesInNode = listOfRequests.stream()
                .collect(Collectors.groupingBy(Request::getPassengerDestination));

        nodes.forEach(n -> n.setLoadIndex(requestsWichBoardsInNode, requestsWichLeavesInNode));
        int maxLoadIndex = nodes.stream().mapToInt(Node::getLoadIndex).max().getAsInt();
        int minLoadIndex = nodes.stream().mapToInt(Node::getLoadIndex).min().getAsInt();

        for (Request request : this.listOfRequests) {
            request.setDistanceRankingFunction(maxDistance, minDistance);
            request.setDeliveryTimeWindowLowerRankingFunction(maxTimeWindowLower, minTimeWindowLower);
            request.setDeliveryTimeWindowUpperRankingFunction(maxTimeWindowUpper, minTimeWindowUpper);
            request.setOriginNodeRankingFunction(maxLoadIndex, minLoadIndex);
            request.setDestinationNodeRankingFunction(maxLoadIndex, minLoadIndex);
            request.setRequestRankingFunction(0.2, 0.2, 0.2, 0.2, 0.2);
        }

        return (Candidates) this.listOfRequests
                .stream()
                .filter(Request::isFeasible)
                .collect(Collectors.toCollection(ArrayList::new));
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
