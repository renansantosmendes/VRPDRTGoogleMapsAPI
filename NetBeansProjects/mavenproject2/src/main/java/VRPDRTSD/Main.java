/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import static GoogleMapsApi.GoogleMapsRoute.FileExtension.json;
import static GoogleMapsApi.GoogleMapsRoute.TravelMode.driving;
import GoogleMapsApi.GoogleMapsRoute;
import static GoogleMapsApi.GoogleMapsRoute.FileExtension.xml;
import GoogleMapsApi.StaticGoogleMap;
import VRPDRTSD.InstanceReaderWithMySQL.DataUpdaterDAO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

/**
 *
 * @author renansantos
 */
public class Main {

    // Keys for Google Maps API
    // AIzaSyCm6B9RvXUIA1yr-iYAWsr1OV0xJCp1kOw 
    // AIzaSyB3pjdG8EsYWb-K-fzPpxCFPcnFBztm-wY //distance matrix
    public static void main(String[] args) throws SQLException, MalformedURLException, IOException, ParseException, SAXException, ParserConfigurationException, XPathExpressionException {

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

        VRPDRTSD problemInstance = new VRPDRTSD();
        problemInstance.readInstance();
        List<Node> listOfNodes = problemInstance.getNodes();

        List<Integer> visitationList1 = new ArrayList<>();
        List<Integer> visitationList2 = new ArrayList<>();
        List<Integer> visitationList3 = new ArrayList<>();
        List<Integer> visitationList4 = new ArrayList<>();
        
        visitationList1.add(0);
        visitationList1.add(1);
        visitationList1.add(3);
        visitationList1.add(6);
        visitationList1.add(4);       
        visitationList1.add(0);
        
//        visitationList2.add(0);
//        visitationList2.add(3);
//        visitationList2.add(6);
//        visitationList2.add(10);
//        visitationList2.add(0);
//        
//        visitationList3.add(0);
//        visitationList3.add(6);
//        visitationList3.add(4);
//        visitationList3.add(1);
//        visitationList3.add(3);
//        //visitationList3.add(6);
//        //visitationList3.add(1);
//        visitationList3.add(0);
//        
        visitationList4.add(0);
        visitationList4.add(11);
        visitationList4.add(1);
        visitationList4.add(3);
        visitationList4.add(6);
        visitationList4.add(4);
        visitationList4.add(3);
        visitationList4.add(6);
        visitationList4.add(0);
        
        
        Set<List<Integer>> setOfVisitationRoutes = new HashSet<>();
        setOfVisitationRoutes.add(visitationList1);
//        setOfVisitationRoutes.add(visitationList2);
//        setOfVisitationRoutes.add(visitationList3);
        setOfVisitationRoutes.add(visitationList4);

        new StaticGoogleMap(listOfNodes, setOfVisitationRoutes).buildMapInWindow();
        //System.out.println(new StaticGoogleMap(listOfNodes, setOfVisitationRoutes).buildURL());
        
        //new DataUpdaterDAO().updateAdjacenciesData(listOfNodes);

    }

}
