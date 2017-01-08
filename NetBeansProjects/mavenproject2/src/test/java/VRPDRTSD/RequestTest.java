/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import VRPDRTSD.Node;
import VRPDRTSD.Request;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
        System.out.println("determineFeasibility");
        
        Duration[][] timeMatrix = {{Duration.ofMinutes(0), Duration.ofMinutes(10), Duration.ofMinutes(10), Duration.ofMinutes(3),
            Duration.ofMinutes(13), Duration.ofMinutes(21), Duration.ofMinutes(23)},
        {Duration.ofMinutes(10), Duration.ofMinutes(0), Duration.ofMinutes(20), Duration.ofMinutes(15),
            Duration.ofMinutes(20), Duration.ofMinutes(28), Duration.ofMinutes(30)},
        {Duration.ofMinutes(20), Duration.ofMinutes(20), Duration.ofMinutes(0), Duration.ofMinutes(25),
            Duration.ofMinutes(15), Duration.ofMinutes(23), Duration.ofMinutes(25)},
        {Duration.ofMinutes(3), Duration.ofMinutes(15), Duration.ofMinutes(25), Duration.ofMinutes(0),
            Duration.ofMinutes(10), Duration.ofMinutes(18), Duration.ofMinutes(20)},
        {Duration.ofMinutes(13), Duration.ofMinutes(20), Duration.ofMinutes(15), Duration.ofMinutes(10),
            Duration.ofMinutes(0), Duration.ofMinutes(8), Duration.ofMinutes(10)},
        {Duration.ofMinutes(21), Duration.ofMinutes(28), Duration.ofMinutes(23), Duration.ofMinutes(18),
            Duration.ofMinutes(8), Duration.ofMinutes(0), Duration.ofMinutes(15)},
        {Duration.ofMinutes(23), Duration.ofMinutes(30), Duration.ofMinutes(25), Duration.ofMinutes(20),
            Duration.ofMinutes(10), Duration.ofMinutes(15), Duration.ofMinutes(0)}
        };

        System.out.println(timeMatrix[0][1].toMinutes());
        //40.7143528
        //-74.0059731
        Node node0 = new Node(0, 40.7143528, -74.0059731);
        Node node1 = new Node(1, 40.7143528, -74.0059731);
        Node node2 = new Node(2, 40.7143528, -74.0059731);
        Node node3 = new Node(3, 40.7143528, -74.0059731);
        Node node4 = new Node(4, 40.7143528, -74.0059731);
        Node node5 = new Node(5, 40.7143528, -74.0059731);
        Node node6 = new Node(6, 40.7143528, -74.0059731);

        Node currentNode = node0;

        Request requestA = new Request(0, node2, node3, LocalDateTime.now(),LocalDateTime.now().plusMinutes(40),null,LocalDateTime.now().plusMinutes(55));
        Request requestB = new Request(0, node2, node5, LocalDateTime.now(),LocalDateTime.now().plusMinutes(40),null,LocalDateTime.now().plusMinutes(55));
        Request requestC = new Request(0, node4, node6, LocalDateTime.now(),LocalDateTime.now().plusMinutes(50),null,LocalDateTime.now().plusMinutes(65));
        Request requestD = new Request(0, node2, node1, LocalDateTime.now(),LocalDateTime.now().plusMinutes(50),null,LocalDateTime.now().plusMinutes(65));

        LocalDateTime currentTime = LocalDateTime.now();
        
        List<Request> requestList = new ArrayList<>();
        
        requestA.determineFeasibility(currentTime, currentNode, timeMatrix);
        requestB.determineFeasibility(currentTime, currentNode, timeMatrix);
        requestC.determineFeasibility(currentTime, currentNode, timeMatrix);
        requestD.determineFeasibility(currentTime, currentNode, timeMatrix);
        

        requestList.add(requestA);
        requestList.add(requestB);
        requestList.add(requestC);
        requestList.add(requestD);
        
        //requestList.forEach(u -> u.determineFeasibility(currentTime, currentNode, timeMatrix));
        
        
        assertEquals(requestA.isFeasible(), true);
        assertEquals(requestB.isFeasible(), true);
        assertEquals(requestC.isFeasible(), true);
        assertEquals(requestD.isFeasible(), true);

    }

}
