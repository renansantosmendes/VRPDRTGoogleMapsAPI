/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GoogleMapsApi.GoogleStaticMap;
import InstanceReaderWithMySQL.NodeDAO;
import ProblemRepresentation.InstanceData;
import View.MainScreen;
import View.WaitScreen;
import java.io.IOException;

/**
 *
 * @author renansantos
 */
public class Controller {

    private String instanceName = "r050n12tw10";
    private String nodesData = "bh_n12s";
    private String adjacenciesData = "bh_adj_n12s";
    private InstanceData data;
    private String numberOfRequests;
    private String timeWindowsSize;

    public Controller() {
        data = new InstanceData(instanceName, nodesData, adjacenciesData);
        data.readProblemData();
        //this.startUI(args);
    }

    public void startUI(String[] args) {
        MainScreen.main(args);
    }

    public void generateMapForInstance() throws IOException {
        new GoogleStaticMap(new NodeDAO(nodesData).getListOfNodes(), adjacenciesData, nodesData).getStaticMapForInstance();
    }

}
