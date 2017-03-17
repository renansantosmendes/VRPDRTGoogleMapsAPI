/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD;

import Algorithms.Algorithm;
import VRPDRTSD.InstanceReaderWithMySQL.AdjacenciesDAO;
import VRPDRTSD.InstanceReaderWithMySQL.NodeDAO;
import VRPDRTSD.InstanceReaderWithMySQL.NumberOfNodesDAO;
import VRPDRTSD.InstanceReaderWithMySQL.RequestDAO;
import VRPDRTSD.Node;
import VRPDRTSD.Request;
import java.time.Duration;
import java.util.List;

/**
 *
 * @author renansantos
 */
public class BuildGreedySolution implements Algorithm {

    private int numberOfNodes;
    private List<Node> nodes;
    private List<Request> listOfRequests;
    private Duration[][] duration;
    private double[][] distance;
    private String instanceName = "VRPDRTSD_requests110";
    private String adjacenciesTable = "Adjacencies_bh_nodes";

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Request> getListOfRequests() {
        return listOfRequests;
    }

    public Duration[][] getDuration() {
        return duration;
    }

    public double[][] getDistance() {
        return distance;
    }

    public String getInstanceName() {
        return instanceName;
    }

//    @Override
//    public Object builRandomSoltution() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Object reBuildSolution() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void evaluateSolution() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Object localSeach() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    @Override
    public void readInstance() {
        this.numberOfNodes = new NumberOfNodesDAO().getNumberOfNodes(this.instanceName);
        this.nodes = new NodeDAO().getListOfNodes();
        this.listOfRequests = new RequestDAO(this.instanceName).getListOfRequestUsingNodesList(nodes);
        this.duration = new AdjacenciesDAO(this.adjacenciesTable).getDurationBetweenNodes(this.numberOfNodes);
        this.distance = new AdjacenciesDAO(this.adjacenciesTable).getDistanceBetweenNodes(this.numberOfNodes);
    }

    @Override
    public Object initializeCandidatesElementsSet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object actualizeCandidatesElementsSet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object findBestCandidate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCandidateIntoSolution(Object solution, Object candidate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
