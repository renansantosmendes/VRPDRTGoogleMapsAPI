/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author renansantos
 */
public class InterfacesTest {
    
    
    /**
     * Test of readInstance method, of class Interfaces.
     */
    @Test
    public void testReadInstance() {
        Interfaces interfaceTest = new Interfaces();
        interfaceTest.readInstance();
        Assert.assertEquals(110, interfaceTest.getListOfRequests().size());
    }

    /**
     * Test of InitializeCandidateElementsSet method, of class Interfaces.
     */
    @Test
    public void testInitializeCandidateElementsSet() {
        Interfaces interfaces = new Interfaces();
        interfaces.readInstance();
        List<Request> listOfRequests = interfaces.getListOfRequests();
       
       //interfaces.InitializeCandidateElementsSet(ProblemData);
        Assert.assertEquals(listOfRequests.size(),110);
        
    }
    
}
