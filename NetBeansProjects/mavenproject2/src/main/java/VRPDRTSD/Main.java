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
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        // new StaticGoogleMap(listOfNodes).buildMapInWindow();
        GoogleMapsRoute route = new GoogleMapsRoute(json, "teste", driving);
        route.setOrigin("Av. do Contorno, 340 - Santa Efigênia, Belo Horizonte - MG, 30110-017");
        route.setDestination("Av. do Contorno, 8902 - Santo Agostinho, Belo Horizonte - MG, 30110-062");
//
        System.out.println(route.buildURL());
        //route.downloadDataFile();

//        
        route.getDataFromFile();

    }

}
