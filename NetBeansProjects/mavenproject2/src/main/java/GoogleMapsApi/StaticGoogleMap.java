/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoogleMapsApi;

import VRPDRTSD.Node;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private String key = "AIzaSyBpval3mOcQgQ5PlCX8tV7Cm5k-E00_98A";
    private StringBuilder stringOfNodes = new StringBuilder();
    private String city = "Belo+Horizonte";
    private String state = "Minas+Gerais";
    private String country = "Brasil";

    public StaticGoogleMap(List<Node> nodesList) {
        this.nodesList = nodesList;

        for (int i = 0; i < this.nodesList.size(); i++) {
            stringOfNodes.append(this.nodesList.get(i).toStringForMapQuery().toString());
        }
    }

    public StringBuilder getStringOfNodes() {
        return stringOfNodes;
    }

    public void buildMapInWindow() {
        JFrame frame = new JFrame("Google Maps");

        try {
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center="
                    + city + "," + state + "," + country + "&zoom=12&scale=2&size=1000x1000&maptype=roadmap"
                    + stringOfNodes.toString() + "&key=" + key + "&format=jpg";
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
