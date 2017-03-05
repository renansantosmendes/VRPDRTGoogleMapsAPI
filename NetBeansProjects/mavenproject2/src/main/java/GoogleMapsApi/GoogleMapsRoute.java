/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoogleMapsApi;

import VRPDRTSD.Node;
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
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.json.*;

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
    private StringBuilder stringOfPolylines = new StringBuilder();
    private int totalRouteTimeInSeconds;
    private List<String> listOfPolylines = new ArrayList<>();
    private List<String> listOfIdWaypoints = new ArrayList<>();
    private List<Integer> listOfTravelTimeInSeconds = new ArrayList<>();
    private List<Double> listOfDistances = new ArrayList<>();
    private List<GoogleStep> listOfSteps = new ArrayList<>();
    private String overviewPolyline;
    private List<Integer> visitationList;
    private List<Node> nodesList;
    private StringBuilder waypoints = new StringBuilder();
    private String color;
    private int weight = 5;

    public enum FileExtension {
        xml, json
    }

    public enum TravelMode {
        driving, walking, bicycling, transit
    }

    public GoogleMapsRoute(FileExtension fileExtension, String fileName, TravelMode travelMode,
            List<Integer> visitationList, List<Node> nodesList) {
        this.fileType = fileExtension.toString();
        this.fileName = fileName;
        this.transitMode = travelMode.toString();
        this.visitationList = visitationList;
        this.nodesList = nodesList;
        
        int startNode = visitationList.get(0);
        int endNode = visitationList.get(visitationList.size() - 1);
        if (this.nodesList.size() >= 2) {
            this.origin = this.nodesList.get(startNode).getLongitude() + "," + this.nodesList.get(startNode).getLatitude();
            this.destination = this.nodesList.get(endNode).getLongitude() + "," + this.nodesList.get(endNode).getLatitude();
        } else {
            throw new IllegalArgumentException("Route size is not enough, should be greater then one!");
        }
        builStringOfWayPoints();

    }

    //add set and get methods here
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public StringBuilder getStringOfPolylines() {
        return stringOfPolylines;
    }

    public String getURLRoot() {
        return URLRoot;
    }

    public String getDirectionsApiKey() {
        return directionsApiKey;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTransitMode() {
        return transitMode;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalRouteTimeInSeconds() {
        return totalRouteTimeInSeconds;
    }

    public List<String> getListOfPolylines() {
        return listOfPolylines;
    }

    public List<String> getListOfIdWaypoints() {
        return listOfIdWaypoints;
    }

    public List<Integer> getListOfTravelTimeInSeconds() {
        return listOfTravelTimeInSeconds;
    }

    public List<Double> getListOfDistances() {
        return listOfDistances;
    }

    public List<GoogleStep> getListOfSteps() {
        return listOfSteps;
    }

    public String getOverviewPolyline() {
        return overviewPolyline;
    }

    public void builStringOfWayPoints() {
        this.visitationList.remove(0);
        this.visitationList.remove(this.visitationList.size() - 1);

        for (Integer visitationPoint : visitationList) {
            this.waypoints.append(nodesList.get(visitationPoint).getLongitude() + ","
                    + nodesList.get(visitationPoint).getLatitude() + "|");
        }
    }

    public URL buildURL() throws MalformedURLException {
        URL url = new URL(this.URLRoot + this.fileType + "?origin=" + origin + "&destination=" + destination
                + "&transit_mode=" + this.transitMode + "&waypoints=" + this.waypoints + "&key=" + this.directionsApiKey);
        return url;
    }

    public void downloadDataFile() throws MalformedURLException, IOException {
        URL url = buildURL();
        System.out.println(url.toString());
        try {
            PrintStream data = new PrintStream(this.fileName + "." + this.fileType);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                data.print(line + "\n");
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromFile() throws MalformedURLException {
        
        JSONObject jsonObject;
        JSONArray array;
        try {
            JSONTokener tokener = new JSONTokener(new FileReader(this.fileName + "." + this.fileType));
            jsonObject = new JSONObject(tokener);
            array = jsonObject.getJSONArray("routes");

            JSONObject objectLegs = array.getJSONObject(0);
            JSONObject objectOverviewPolyline = (JSONObject) objectLegs.get("overview_polyline");
            this.overviewPolyline = objectOverviewPolyline.get("points").toString();

            JSONArray arrayOfLegs = objectLegs.getJSONArray("legs");
            JSONObject objectSteps = arrayOfLegs.getJSONObject(0);
            JSONArray arrayOfSteps = objectSteps.getJSONArray("steps");

            buildGoogleRoute(objectLegs, objectSteps);
            extractRouteInformationFromData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void buildGoogleRoute(JSONObject objectLegs, JSONObject objectSteps) {

        JSONObject objectDuration = (JSONObject) objectSteps.get("duration");
        JSONObject objectDistance = (JSONObject) objectSteps.get("distance");
        JSONObject objectStartLocation = (JSONObject) objectSteps.get("start_location");
        JSONObject objectEndLocation = (JSONObject) objectSteps.get("end_location");
        JSONArray arrayOfSteps = (JSONArray) objectSteps.get("steps");

        int totalTravelTime = objectDuration.getInt("value");
        int totalDistance = objectDistance.getInt("value");
        String startAddress = objectSteps.get("start_address").toString();
        String endAddress = objectSteps.get("end_address").toString();
        GoogleLocation startLocation = new GoogleLocation(objectStartLocation.getDouble("lat"),
                objectStartLocation.getDouble("lng"));
        GoogleLocation endLocation = new GoogleLocation(objectEndLocation.getDouble("lat"),
                objectEndLocation.getDouble("lng"));

        for (int i = 0; i < arrayOfSteps.length(); i++) {
            JSONObject objectStep = (JSONObject) arrayOfSteps.get(i);
            JSONObject objectStepDuration = (JSONObject) objectStep.get("duration");
            JSONObject objectStepDistance = (JSONObject) objectStep.get("distance");
            JSONObject objectStepStartLocation = (JSONObject) objectStep.get("start_location");
            JSONObject objectStepEndLocation = (JSONObject) objectStep.get("end_location");
            JSONObject objectStepPolyline = (JSONObject) objectStep.get("polyline");

            String htmlInstructions = objectStep.get("html_instructions").toString();
            String polylineString = objectStepPolyline.get("points").toString();
            String travelMode = objectStep.get("travel_mode").toString();

            GoogleLocation stepStartLocation = new GoogleLocation(objectStepStartLocation.getDouble("lat"),
                    objectStepStartLocation.getDouble("lng"));

            GoogleLocation stepEndLocation = new GoogleLocation(objectStepEndLocation.getDouble("lat"),
                    objectStepEndLocation.getDouble("lng"));
            int stepDuration = objectStepDuration.getInt("value");
            int stepDistance = objectStepDistance.getInt("value");

            GoogleStep step = new GoogleStep(stepDistance, stepDuration, stepStartLocation, stepEndLocation,
                    htmlInstructions, polylineString, travelMode);

            listOfSteps.add(step);
        }

    }

    private void extractRouteInformationFromData() {
        this.listOfPolylines.clear();
        this.listOfPolylines.addAll(this.listOfSteps.stream().map(GoogleStep::getPolyline)
                .collect(Collectors.toCollection(ArrayList::new)));

        this.listOfDistances.clear();
        this.listOfDistances.addAll(this.listOfSteps.stream().map(GoogleStep::getDistance)
                .collect(Collectors.toCollection(ArrayList::new)));

        this.listOfTravelTimeInSeconds.clear();
        this.listOfTravelTimeInSeconds.addAll(this.listOfSteps.stream().map(GoogleStep::getDuration)
                .collect(Collectors.toCollection(ArrayList::new)));

        this.totalRouteTimeInSeconds = this.getListOfTravelTimeInSeconds().stream().reduce(0, (a, b) -> a + b);

        for (String polyline : listOfPolylines) {
            stringOfPolylines.append("|enc:" + polyline);
        }
    }
    
    public String getPathForGoogleMap(){
        //"&path=weight:" + weight + "|color:" + color + "|enc:" + enc +
        color = new ColorGenerator().generatesColor();
        return "&path=weight:" + weight+ "|color:" + color + "|enc:" + this.overviewPolyline;
    }
}
