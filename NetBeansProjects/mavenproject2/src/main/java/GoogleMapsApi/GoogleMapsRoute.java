/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoogleMapsApi;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
//import javax.swing.text.Document;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author renansantos
 */
public class GoogleMapsRoute {

    private final String URLRoot = "https://maps.googleapis.com/maps/api/directions/";
    private final String directionsApiKey = "AIzaSyCgaZr9fRAUs3_8lftkt026_MfZ3yZVN4E";
    private final String fileType;
    private final String fileName;
    private final String transitMode;
    private String origin;
    private String destination;
    private List<String> polilines = new ArrayList<>();
    private List<String> listOfIdWaypoints = new ArrayList<>();
    private List<DateTime> listOfTravelTime = new ArrayList<>();
    private List<Double> listOfDistances = new ArrayList<>();

    public enum FileExtension {
        xml, json
    }

    public enum TravelMode {
        driving, walking, bicycling, transit
    }

    public GoogleMapsRoute(FileExtension fileExtension, String fileName, TravelMode travelMode) {
        this.fileType = fileExtension.toString();
        this.fileName = fileName;
        this.transitMode = travelMode.toString();
    }

    //add set and get methods here
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    private URL buildURL() throws MalformedURLException {
        String formatedOrigin = this.origin.replace(' ', '+');
        String formatedDestination = this.destination.replace(' ', '+');
        URL url = new URL(this.URLRoot + this.fileType + "?origin=" + formatedOrigin + "&destination=" + formatedDestination
                + "&transit_mode=" + this.transitMode + "&key=" + this.directionsApiKey);
        return url;
    }

    public void downloadDataFile() throws MalformedURLException, IOException {
        URL url = buildURL();

        try {
            PrintStream data = new PrintStream(this.fileName + "." + this.fileType);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                data.print(line + "\n");
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromFile() throws MalformedURLException, ParseException {
        //URL url = buildURL();
        JSONObject jsonObject;
        //Cria o parse de tratamento
        JSONParser parser = new JSONParser();
        //Variaveis que irao armazenar os dados do arquivo JSON
        List<String> overviewPolyline;
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader(this.fileName + "." + this.fileType));

            overviewPolyline = (List<String>) jsonObject.get("routes");
            System.out.println(overviewPolyline);
            //Salva nas variaveis os dados retirados do arquivo
            //        nome = (String) jsonObject.get("nome");
            //        sobrenome = (String) jsonObject.get("sobrenome");
            //        estado = (String) jsonObject.get("estado");
            //        pais = (String) jsonObject.get("pais");

            //System.out.printf("Nome: %s\nSobrenome: %s\nEstado: %s\nPais: %s\n", nome, sobrenome, estado, pais);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getDataFromXML() throws MalformedURLException, ParseException, SAXException, ParserConfigurationException,
            ParserConfigurationException, ParserConfigurationException, IOException, XPathExpressionException {
        //URL url = buildURL();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(this.fileName + "." + this.fileType));
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        NodeList nodeLatitude = (NodeList) xpath.evaluate("DirectionsResponse/route/leg/step/start_location/lat",
                document, XPathConstants.NODESET);
        NodeList nodeLongitude = (NodeList) xpath.evaluate("DirectionsResponse/route/leg/step/start_location/lng",
                document, XPathConstants.NODESET);
        NodeList nodeTime = (NodeList) xpath.evaluate("DirectionsResponse/route/leg/step/duration/text",
                document, XPathConstants.NODESET);
        NodeList nodeDistance = (NodeList) xpath.evaluate("DirectionsResponse/route/leg/step/distance/text",
                document, XPathConstants.NODESET);
        NodeList nodeIndications = (NodeList) xpath.evaluate("DirectionsResponse/route/leg/step/html_instructions",
                document, XPathConstants.NODESET);
        NodeList nodeSummary = (NodeList) xpath.evaluate("DirectionsResponse/route/summary",
                document, XPathConstants.NODESET);
        NodeList nodeCopyright = (NodeList) xpath.evaluate("DirectionsResponse/route/copyrights",
                document, XPathConstants.NODESET);
        NodeList nodeWaypointsIndex = (NodeList) xpath.evaluate("DirectionsResponse/route/waypoint_index",
                document, XPathConstants.NODESET);
        NodeList nodeTotaltime = (NodeList) xpath.evaluate("DirectionsResponse/route/leg/duration/value",
                document, XPathConstants.NODESET);
        NodeList nodeTotalDistance = (NodeList) xpath.evaluate("DirectionsResponse/route/leg/distance/value",
                document, XPathConstants.NODESET);
        NodeList nodePolilines = (NodeList) xpath.evaluate("DirectionsResponse/route/leg/step/polyline/points",
                document, XPathConstants.NODESET);

        System.out.println(nodePolilines.item(2).getNodeValue());

        List<NodeList> allNodes = new ArrayList<>();
        allNodes.add(nodeTime);
        allNodes.forEach(System.out::println);

        //allNodes.add(nodeDistance);
        //allNodes.add(nodeIndications);
        //allNodes.add(nodeLatitude);
        //allNodes.add(nodeLongitude);
        //String[][] result = this.getNodesRoute(allNodes);
    }

    public void getDataFromFile2() throws MalformedURLException, ParseException, FileNotFoundException {
        String file = this.fileName + "." + this.fileType;
        System.out.println("File name = " + file);
        //Reader reader = new FileReader(new File(ClassLoader.getSystemResource(this.fileName + "." + this.fileType).getFile()));
        BufferedReader br = new BufferedReader(new FileReader(file));
        Gson gson = new Gson();
        DirectionsResponse response = gson.fromJson(br, DirectionsResponse.class);

    }

}
