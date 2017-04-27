/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import Algorithms.Algorithm;
import java.time.Duration;
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

    private ProblemData data;
    private String instanceName;
    private String nodesInstanceName;
    private String adjacenciesInstanceName;
    double maxDistance;
    double minDistance;
    int minTimeWindowLower;
    int maxTimeWindowLower;
    int minTimeWindowUpper;
    int maxTimeWindowUpper;
    Map<Node, List<Request>> requestsWichBoardsInNode;
    Map<Node, List<Request>> requestsWichLeavesInNode;
    int maxLoadIndex;
    int minLoadIndex;

    public VRPDRTSD(String instanceName, String nodesInstanceName, String adjacenciesInstanceName) {
        this.instanceName = instanceName;
        this.nodesInstanceName = nodesInstanceName;
        this.adjacenciesInstanceName = adjacenciesInstanceName;
        this.readInstance();
    }

    public ProblemData getData() {
        return data;
    }

    public void setData(ProblemData data) {
        this.data = data;
    }

    @Override
    public void readInstance() {
        data = new ProblemData(instanceName, nodesInstanceName, adjacenciesInstanceName);
    }

    @Override
    public <Candidates> Candidates initializeCandidatesElementsSet() {
        requestsFeasibilityAnalysis();
        setDistanceToAttendEveryRequest();
        findMaxAndMinDistance();
        findMaxAndMinTimeWindowLower();
        findMaxAndMinTimeWindowUpper();
        separateRequestsWhichBoardsAndLeavesInNodes();
        setLoadIndexForEveryNode();
        findMaxAndMinLoadIndex();
        setRequestFeasibilityParameters();
        return (Candidates) getListOfFeasibleRequests();
    }

    private ArrayList<Request> getListOfFeasibleRequests() {
        return data.getRequests().stream().filter(Request::isFeasible).collect(Collectors.toCollection(ArrayList::new));
    }

    private void setRequestFeasibilityParameters() {
        for (Request request : data.getRequests()) {
            request.setDistanceRankingFunction(maxDistance, minDistance);
            request.setDeliveryTimeWindowLowerRankingFunction(maxTimeWindowLower, minTimeWindowLower);
            request.setDeliveryTimeWindowUpperRankingFunction(maxTimeWindowUpper, minTimeWindowUpper);
            request.setOriginNodeRankingFunction(maxLoadIndex, minLoadIndex);
            request.setDestinationNodeRankingFunction(maxLoadIndex, minLoadIndex);
            request.setRequestRankingFunction(0.2, 0.2, 0.2, 0.2, 0.2);
        }
    }

    private void findMaxAndMinLoadIndex() {
        maxLoadIndex = data.getNodes().stream().mapToInt(Node::getLoadIndex).max().getAsInt();
        minLoadIndex = data.getNodes().stream().mapToInt(Node::getLoadIndex).min().getAsInt();
    }

    private void setLoadIndexForEveryNode() {
        data.getNodes().forEach(n -> n.setLoadIndex(requestsWichBoardsInNode, requestsWichLeavesInNode));
    }

    private void setDistanceToAttendEveryRequest() {
        data.getRequests().forEach(r -> r.setDistanceToAttendThisRequest(data.getCurrentNode(), data.getDistance()));
    }

    private void separateRequestsWhichBoardsAndLeavesInNodes() {
        requestsWichBoardsInNode = data.getRequests().stream().collect(Collectors.groupingBy(Request::getPassengerOrigin));
        requestsWichLeavesInNode = data.getRequests().stream().collect(Collectors.groupingBy(Request::getPassengerDestination));
    }

    private void findMaxAndMinTimeWindowUpper() {
        minTimeWindowUpper = data.getRequests().stream()
                .mapToInt(Request::getDeliveryTimeWindowUpperInMinutes).min().getAsInt();
        maxTimeWindowUpper = data.getRequests().stream()
                .mapToInt(Request::getDeliveryTimeWindowUpperInMinutes).max().getAsInt();
    }

    private void findMaxAndMinTimeWindowLower() {
        minTimeWindowLower = data.getRequests().stream()
                .mapToInt(Request::getDeliveryTimeWindowLowerInMinutes).min().getAsInt();
        maxTimeWindowLower = data.getRequests().stream()
                .mapToInt(Request::getDeliveryTimeWindowLowerInMinutes).max().getAsInt();
    }

    private void findMaxAndMinDistance() {
        maxDistance = data.getRequests().stream()
                .mapToDouble(Request::getDistanceToAttendThisRequest).max().getAsDouble();
        minDistance = data.getRequests().stream()
                .mapToDouble(Request::getDistanceToAttendThisRequest).min().getAsDouble();
    }

    private void requestsFeasibilityAnalysis() {
        for (Request request : data.getRequests()) {
            request.determineFeasibility(data.getCurrentTime(), data.getCurrentNode(), data.getDuration());
        }
    }

    @Override
    public <Candidates> Candidates actualizeCandidatesElementsSet() {
        data.setLastPassengerAddedToRoute(data.getRequests().get(0));
        data.getRequests().remove(0);
        data.setCurrentNode(data.getLastPassengerAddedToRoute().getPassengerOrigin());
        //recalculate requests feasibility
        //recalculate RRF

        System.out.println(data.getCurrentNode());
        return (Candidates) data.getRequests();
    }

    @Override
    public <Candidate> Candidate findBestCandidate() {
        data.getRequests().sort(Comparator.comparing(Request::getRequestRankingFunction).reversed());
        return (Candidate) data.getRequests().get(0);
    }

    @Override
    public <Candidate, ProblemSolution> void addCandidateIntoSolution(ProblemSolution solution, Candidate candidate) {

    }

}
