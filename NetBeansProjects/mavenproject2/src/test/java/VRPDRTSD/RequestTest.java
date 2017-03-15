/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import VRPDRTSD.IntanceReaderWithMySQL.AdjacenciesDAO;
import VRPDRTSD.IntanceReaderWithMySQL.NodeDAO;
import VRPDRTSD.IntanceReaderWithMySQL.NumberOfNodesDAO;
import VRPDRTSD.IntanceReaderWithMySQL.RequestDAO;
import VRPDRTSD.Node;
import VRPDRTSD.Request;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author renansantos
 */
public class RequestTest {

    /**
     * Test of determineFeasibility method, of class Request.
     */
    @Test
    public void testDetermineFeasibility() {
//        System.out.println("determineFeasibility");
//
//        Duration[][] timeMatrix = {{Duration.ofMinutes(0), Duration.ofMinutes(10), Duration.ofMinutes(10), Duration.ofMinutes(3),
//            Duration.ofMinutes(13), Duration.ofMinutes(21), Duration.ofMinutes(23)},
//        {Duration.ofMinutes(10), Duration.ofMinutes(0), Duration.ofMinutes(20), Duration.ofMinutes(15),
//            Duration.ofMinutes(20), Duration.ofMinutes(28), Duration.ofMinutes(30)},
//        {Duration.ofMinutes(20), Duration.ofMinutes(20), Duration.ofMinutes(0), Duration.ofMinutes(25),
//            Duration.ofMinutes(15), Duration.ofMinutes(23), Duration.ofMinutes(25)},
//        {Duration.ofMinutes(3), Duration.ofMinutes(15), Duration.ofMinutes(25), Duration.ofMinutes(0),
//            Duration.ofMinutes(10), Duration.ofMinutes(18), Duration.ofMinutes(20)},
//        {Duration.ofMinutes(13), Duration.ofMinutes(20), Duration.ofMinutes(15), Duration.ofMinutes(10),
//            Duration.ofMinutes(0), Duration.ofMinutes(8), Duration.ofMinutes(10)},
//        {Duration.ofMinutes(21), Duration.ofMinutes(28), Duration.ofMinutes(23), Duration.ofMinutes(18),
//            Duration.ofMinutes(8), Duration.ofMinutes(0), Duration.ofMinutes(15)},
//        {Duration.ofMinutes(23), Duration.ofMinutes(30), Duration.ofMinutes(25), Duration.ofMinutes(20),
//            Duration.ofMinutes(10), Duration.ofMinutes(15), Duration.ofMinutes(0)}
//        };
//
//        System.out.println(timeMatrix[0][1].toMinutes());
//        //40.7143528
//        //-74.0059731
//        Node node0 = new Node(0, 40.7143528, -74.0059731);
//        Node node1 = new Node(1, 40.7143528, -74.0059731);
//        Node node2 = new Node(2, 40.7143528, -74.0059731);
//        Node node3 = new Node(3, 40.7143528, -74.0059731);
//        Node node4 = new Node(4, 40.7143528, -74.0059731);
//        Node node5 = new Node(5, 40.7143528, -74.0059731);
//        Node node6 = new Node(6, 40.7143528, -74.0059731);
//
//        Node currentNode = node0;
//
//        Request requestA = new Request(0, node2, node3, LocalDateTime.now(), LocalDateTime.now().plusMinutes(40), null, LocalDateTime.now().plusMinutes(55));
//        Request requestB = new Request(0, node2, node5, LocalDateTime.now(), LocalDateTime.now().plusMinutes(40), null, LocalDateTime.now().plusMinutes(55));
//        Request requestC = new Request(0, node4, node6, LocalDateTime.now(), LocalDateTime.now().plusMinutes(50), null, LocalDateTime.now().plusMinutes(65));
//        Request requestD = new Request(0, node2, node1, LocalDateTime.now(), LocalDateTime.now().plusMinutes(50), null, LocalDateTime.now().plusMinutes(65));
//
//        LocalDateTime currentTime = LocalDateTime.now();
//
//        List<Request> requestList = new ArrayList<>();
//
//        requestA.determineFeasibility(currentTime, currentNode, timeMatrix);
//        requestB.determineFeasibility(currentTime, currentNode, timeMatrix);
//        requestC.determineFeasibility(currentTime, currentNode, timeMatrix);
//        requestD.determineFeasibility(currentTime, currentNode, timeMatrix);
//
//        requestList.add(requestA);
//        requestList.add(requestB);
//        requestList.add(requestC);
//        requestList.add(requestD);
//
//        //requestList.forEach(u -> u.determineFeasibility(currentTime, currentNode, timeMatrix));
//        assertEquals(requestA.isFeasible(), true);
//        assertEquals(requestB.isFeasible(), true);
//        assertEquals(requestC.isFeasible(), true);
//        assertEquals(requestD.isFeasible(), true);

    }

    /**
     * Test of determineFeasibility method, of class Request.
     */
    @Test
    public void testDetermineFeasibilityForBiggerInstances() {
        int numberOfNodes;
        List<Node> nodes;
        List<Request> listOfRequests;
        Duration[][] duration;
        double[][] distance;
        String instanceName = "VRPDRTSD_requests110";

        numberOfNodes = new NumberOfNodesDAO().getNumberOfNodes(instanceName);
        nodes = new NodeDAO().getListOfNodes();
        listOfRequests = new RequestDAO().getListOfRequestUsingNodesList(nodes, instanceName);
        duration = new AdjacenciesDAO().getDurationBetweenNodes(numberOfNodes);
        distance = new AdjacenciesDAO().getDistanceBetweenNodes(numberOfNodes);

        //local variables for the feasibility test
        LocalDateTime currentTime = LocalDateTime.of(2017, 1, 1, 0, 0, 0);
        Node currentNode = new Node(0, 40.7143528, -74.0059731,null);
        Node currentNode1 = new Node(1, 40.7143528, -74.0059731,null);

        //evaluating every request
        listOfRequests.forEach(r -> r.determineFeasibility(currentTime, currentNode, duration));

        //mapping the requests into a map with two sets
        Map<Boolean, List<Request>> feasibleRequestsMap = listOfRequests
                .stream().collect(Collectors.partitioningBy(Request::isFeasible));

        Map<Node, List<Request>> requestsWichBoardsInNode = listOfRequests.stream()
                .collect(Collectors.groupingBy(Request::getPassengerOrigin));

        Map<Node, List<Request>> requestsWichLeavesInNode = listOfRequests.stream()
                .collect(Collectors.groupingBy(Request::getPassengerDestination));

        System.out.println("Requests = " + requestsWichBoardsInNode.keySet().stream()
                .sorted(Comparator.comparing(Node::getNodeId)).collect(Collectors.toCollection(ArrayList::new)));

        listOfRequests.forEach(r -> r.setDistanceToAttendThisRequest(currentNode, distance));
        double maxDistance = listOfRequests.stream().mapToDouble(Request::getDistanceToAttendThisRequest).max().getAsDouble();
        double minDistance = listOfRequests.stream().mapToDouble(Request::getDistanceToAttendThisRequest).min().getAsDouble();

        listOfRequests.forEach(r -> r.setDistanceRankingFunction(maxDistance, minDistance));

//        System.out.println("\n TESTE (distRankingFunction): " + listOfRequests.get(10).getDistanceRankingFunction());
//        System.out.println("\n TESTE (distRankingFunction): " + listOfRequests.get(10).getDistanceToAttendThisRequest());

        Assert.assertEquals(3, feasibleRequestsMap.get(false).size());

        int minTimeWindowLower = listOfRequests.stream().mapToInt(Request::getDeliveryTimeWindowLowerInMinutes).min().getAsInt();
        int maxTimeWindowLower = listOfRequests.stream().mapToInt(Request::getDeliveryTimeWindowLowerInMinutes).max().getAsInt();
        int minTimeWindowUpper = listOfRequests.stream().mapToInt(Request::getDeliveryTimeWindowUpperInMinutes).min().getAsInt();
        int maxTimeWindowUpper = listOfRequests.stream().mapToInt(Request::getDeliveryTimeWindowUpperInMinutes).max().getAsInt();

//        System.out.println("Time Window = " + minTimeWindowLower);

        Assert.assertEquals(25, minTimeWindowLower);
        Assert.assertEquals(178, maxTimeWindowLower);
        Assert.assertEquals(28, minTimeWindowUpper);
        Assert.assertEquals(181, maxTimeWindowUpper);

        listOfRequests.forEach(r -> {
            r.setDeliveryTimeWindowLowerRankingFunction(maxTimeWindowLower, minTimeWindowLower);
            r.setDeliveryTimeWindowUpperRankingFunction(maxTimeWindowUpper, minTimeWindowUpper);
        });

        nodes.forEach(n -> n.setLoadIndex(requestsWichBoardsInNode, requestsWichLeavesInNode));
        Assert.assertEquals(14,nodes.get(1).getLoadIndex());
        //nodes.forEach(System.out::println);
        
        int maxLoadIndex = nodes.stream().mapToInt(Node::getLoadIndex).max().getAsInt();
        int minLoadIndex = nodes.stream().mapToInt(Node::getLoadIndex).min().getAsInt();
        Assert.assertEquals(-20, minLoadIndex);
        Assert.assertEquals(22, maxLoadIndex);
        listOfRequests.forEach(r -> r.setOriginNodeRankingFunction(maxLoadIndex, minLoadIndex));
        //listOfRequests.forEach(r -> System.out.println(r.getOriginNodeRankingFunction()));

    }

}
