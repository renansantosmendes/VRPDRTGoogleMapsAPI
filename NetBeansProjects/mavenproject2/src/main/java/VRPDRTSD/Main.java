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
        System.out.println(buildGreedySolutionAlgorithm.getListOfRequests());
        System.out.println(buildGreedySolutionAlgorithm.getNodes());
        System.out.println(buildGreedySolutionAlgorithm.getNumberOfNodes());
    }

}
