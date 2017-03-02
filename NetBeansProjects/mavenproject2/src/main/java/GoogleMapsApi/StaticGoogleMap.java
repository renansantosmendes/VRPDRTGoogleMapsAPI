/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoogleMapsApi;

import static GoogleMapsApi.GoogleMapsRoute.FileExtension.json;
import static GoogleMapsApi.GoogleMapsRoute.TravelMode.driving;
import VRPDRTSD.Node;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;

/**
 *
 * @author renansantos
 */
public class StaticGoogleMap {

    //Google Maps Directions API Key =  AIzaSyC--KVvuj7xGnkRBoLrUIaXLx5lGfedlBI  
    private List<Node> nodesList;
    private final String URLRoot = "https://maps.googleapis.com/maps/api/staticmap?center=";
    private final String directionsApiKey = "AIzaSyCgaZr9fRAUs3_8lftkt026_MfZ3yZVN4E";
    private final String staticMapKey = "AIzaSyBpval3mOcQgQ5PlCX8tV7Cm5k-E00_98A";
    private StringBuilder stringOfNodes = new StringBuilder();
    private StringBuilder polylines = new StringBuilder();
    private String city = "Belo+Horizonte";
    private String state = "Minas+Gerais";
    private String country = "Brasil";
    private int zoom = 14;
    private int scale = 2;
    private int width = 1000;
    private int height = 1000;
    private String mapType = "roadmap";
    private String color = "red";

    public StaticGoogleMap(List<Node> nodesList) {
        this.nodesList = nodesList;

        for (int i = 0; i < this.nodesList.size(); i++) {
            stringOfNodes.append(this.nodesList.get(i).toStringForMapQuery().toString());
        }
    }

    public StringBuilder getStringOfNodes() {
        return stringOfNodes;
    }

    public URL buildURL() throws MalformedURLException, IOException {
        
        Node origin = nodesList.get(2);
        Node destination = nodesList.get(3);
        
        GoogleMapsRoute route = new GoogleMapsRoute(json, "data_origin:" + origin.getNodeId() 
                + "_destination:" + destination.getNodeId(), driving);
        route.setOrigin(origin.getAdress());
        route.setDestination(destination.getAdress());
        route.downloadDataFile();
        route.getDataFromFile();
        String enc = route.getStringOfPolylines().toString();
        
        URL url = new URL(URLRoot + city + "," + state + "," + country + "&zoom=" + zoom + "&scale=" + scale
                + "&size=" + width + "x" + height + "&maptype=" + mapType + stringOfNodes.toString()+
                "&path=weight:3|color:"+color +"|enc:"+ route.getOverviewPolyline() + "&key="+ staticMapKey + "&format=jpg");
        return url;
    }

    public void buildMapInWindow() throws IOException {
        JFrame frame = new JFrame("Google Maps");

        try {
            String imageUrl = buildURL().toString();
            System.out.println("URL");
            System.out.println(imageUrl);

            String destinationFile = "map_from_google.jpg";
            String str = destinationFile;
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        frame.add(new JLabel(new ImageIcon((new ImageIcon("map_from_google.jpg"))
                .getImage().getScaledInstance(630, 600, java.awt.Image.SCALE_SMOOTH))));
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
