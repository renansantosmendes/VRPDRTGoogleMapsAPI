/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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
public class ProblemDataTest {

    public ProblemDataTest() {

    }

    @Test
    public void testGetListOfRequests() {
        String instanceName = "r150n12tw10";
        String nodesData = "bh_n12s";
        String adjacenciesData = "bh_adj_n12s";
        System.out.println("Testing ProblemData class");
        ProblemData instance = new ProblemData(instanceName, nodesData, adjacenciesData);
        int expResult = 150;
        int result = instance.getRequests().size();
        long[][] distanceBetweenNodes = instance.getDistance();
        Duration[][] timeBetweenNodes = instance.getDuration();
        for (int i = 0; i < instance.getNumberOfNodes(); i++) {
            for (int j = 0; j < instance.getNumberOfNodes(); j++) {
                System.out.print(distanceBetweenNodes[i][j] + " ");
            }
            System.out.println();
        }
        assertEquals(expResult, result);

    }

}
