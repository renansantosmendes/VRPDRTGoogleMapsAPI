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

    public URL buildURL() throws MalformedURLException {
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
                data.print(line + "\n");
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromFile() throws MalformedURLException {
        //URL url = buildURL();
        JSONObject jsonObject;        
        
        List<String> overviewPolyline;
        JSONArray array;
        try {
            JSONTokener tokener = new JSONTokener(new FileReader(this.fileName + "." + this.fileType));
            jsonObject = new JSONObject(tokener);
            array = jsonObject.getJSONArray("routes");
            
            JSONObject objectLegs = array.getJSONObject(0);
            JSONArray arrayOfLegs = objectLegs.getJSONArray("legs");
            JSONObject objectSteps = arrayOfLegs.getJSONObject(0);
            JSONArray arrayOfSteps = objectSteps.getJSONArray("steps");
            //System.out.println("objectLegs: " + objectLegs.get("distance"));
            
            System.out.println("Distance" + objectLegs.get("overview_polyline"));
            //objectLegs.keys().forEachRemaining(System.out::println);
            
            
            
            //objectSteps.keys().forEachRemaining(System.out::println);
            //System.out.println(arrayOfSteps.length());
            //buildGoogleRoute(arrayOfLegs,objectSteps);
            buildGoogleRoute(objectLegs,objectSteps);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
    private void buildGoogleRoute(JSONObject objectLegs, JSONObject objectSteps) {
        objectSteps.keys().forEachRemaining(System.out::println);
        JSONObject objectDuration = (JSONObject) objectSteps.get("duration");
        JSONObject objectDistance = (JSONObject) objectSteps.get("distance");
//        JSONObject objectStartAdress = (JSONObject) objectSteps.get("start_address");
        
        //System.out.println("Imprimindo do objeto json = " + objectDuration.getInt("value"));
        int totalTravelTime = objectDuration.getInt("value");
        int totalDistance = objectDistance.getInt("value");
        String startAddress = objectSteps.get("start_address").toString();
        //int totalDistance = Integer.parseInt((String) objectSteps.("duration").);
        System.out.println(startAddress);
    }
}
 