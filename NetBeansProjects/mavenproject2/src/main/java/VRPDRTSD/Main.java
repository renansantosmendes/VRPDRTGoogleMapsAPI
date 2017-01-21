/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import Algorithms.BuildGreedySolution;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.parser.ParseException;

/**
 *
 * @author renansantos
 */
public class Main {

    // Keys for Google Maps API
    // AIzaSyCm6B9RvXUIA1yr-iYAWsr1OV0xJCp1kOw 
    // AIzaSyB3pjdG8EsYWb-K-fzPpxCFPcnFBztm-wY //distance matrix
    public static void main(String[] args) throws SQLException, MalformedURLException, IOException, ParseException {

        BuildGreedySolution buildGreedySolutionAlgorithm = new BuildGreedySolution();
//        System.out.println(buildGreedySolutionAlgorithm.getListOfRequests());
//        System.out.println(buildGreedySolutionAlgorithm.getNodes());
//        System.out.println(buildGreedySolutionAlgorithm.getNumberOfNodes());

        LocalDateTime data = LocalDateTime.now();
        String dataString = Integer.toString(data.getYear()) + Integer.toString(data.getMonth().getValue())
                + Integer.toString(data.getDayOfMonth());
        System.out.println("data = " + data);

        List<Integer> solution = new ArrayList<>();
        solution.add(0);
        solution.add(-510);
        solution.add(1);
        solution.add(-520);
        solution.add(2);
        solution.add(-520);
        solution.add(2);
        solution.add(-520);
        solution.add(1);
        solution.add(-520);
        solution.add(0);

        System.out.println(solution);
        
        solution.removeIf(u -> u.intValue() <= 0);
        //List<Integer> route = solution.
        System.out.println(solution);
        
        System.out.println("Data do dia");
    }

}
