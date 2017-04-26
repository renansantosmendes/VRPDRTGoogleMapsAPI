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
        for (Request request : data.getRequests()) {
            request.determineFeasibility(data.getCurrentTime(), data.getCurrentNode(), data.getDuration());
        }

        data.getRequests().forEach(r -> r.setDistanceToAttendThisRequest(data.getCurrentNode(), data.getDistance()));
        double maxDistance = data.getRequests().stream()
                .mapToDouble(Request::getDistanceToAttendThisRequest).max().getAsDouble();
        double minDistance = data.getRequests().stream()
                .mapToDouble(Request::getDistanceToAttendThisRequest).min().getAsDouble();

        int minTimeWindowLower = data.getRequests().stream()
                .mapToInt(Request::getDeliveryTimeWindowLowerInMinutes).min().getAsInt();
        int maxTimeWindowLower = data.getRequests().stream()
                .mapToInt(Request::getDeliveryTimeWindowLowerInMinutes).max().getAsInt();
        int minTimeWindowUpper = data.getRequests().stream()
                .mapToInt(Request::getDeliveryTimeWindowUpperInMinutes).min().getAsInt();
        int maxTimeWindowUpper = data.getRequests().stream()
                .mapToInt(Request::getDeliveryTimeWindowUpperInMinutes).max().getAsInt();

        Map<Node, List<Request>> requestsWichBoardsInNode = data.getRequests().stream()
                .collect(Collectors.groupingBy(Request::getPassengerOrigin));

        Map<Node, List<Request>> requestsWichLeavesInNode = data.getRequests().stream()
                .collect(Collectors.groupingBy(Request::getPassengerDestination));

        data.getNodes().forEach(n -> n.setLoadIndex(requestsWichBoardsInNode, requestsWichLeavesInNode));
        int maxLoadIndex = data.getNodes().stream().mapToInt(Node::getLoadIndex).max().getAsInt();
        int minLoadIndex = data.getNodes().stream().mapToInt(Node::getLoadIndex).min().getAsInt();

        for (Request request : data.getRequests()) {
            request.setDistanceRankingFunction(maxDistance, minDistance);
            request.setDeliveryTimeWindowLowerRankingFunction(maxTimeWindowLower, minTimeWindowLower);
            request.setDeliveryTimeWindowUpperRankingFunction(maxTimeWindowUpper, minTimeWindowUpper);
            request.setOriginNodeRankingFunction(maxLoadIndex, minLoadIndex);
            request.setDestinationNodeRankingFunction(maxLoadIndex, minLoadIndex);
            request.setRequestRankingFunction(0.2, 0.2, 0.2, 0.2, 0.2);
        }

        return (Candidates) data.getRequests()
                .stream()
                .filter(Request::isFeasible)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    @Override
    public <Candidates> Candidates actualizeCandidatesElementsSet() {
        data.setLastPassengerAddedToRoute(data.getRequests().get(0));
        data.getRequests().remove(0);
        data.setCurrentNode(data.getLastPassengerAddedToRoute().getPassengerOrigin());
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
