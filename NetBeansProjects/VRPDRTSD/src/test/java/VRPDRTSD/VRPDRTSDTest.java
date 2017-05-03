/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author renansantos
 */
public class VRPDRTSDTest {

    @Test
    public void inicializationTest() {
        String instanceName = "r150n12tw10";
        String nodesData = "bh_n12s";
        String adjacenciesData = "bh_adj_n12s";
        System.out.println("Testing VRPDRTSD class");
        VRPDRTSD problem = new VRPDRTSD(instanceName, nodesData, adjacenciesData);
        int expResult = 150;
        int result = problem.getData().getRequests().size();
        assertEquals(expResult, result);
        problem.getData().getRequests().forEach(System.out::println);
        problem.buildGreedySolution();
        
    }

}
