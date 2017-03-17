/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InstanceReaderWithMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author renansantos
 */
public class AdjacenciesDAO {

    private Connection connection;
    private String adjacenciesTable;
    private String nodesTable;

    public AdjacenciesDAO(String adjacenciesTable, String nodesTable) {
        this.connection = new ConnectionFactory().getConnection();
        this.adjacenciesTable = adjacenciesTable;
        this.nodesTable = nodesTable;
    }

    public void addAdjacenciesIntoDataBase(int numberOfNodes, List<List<Integer>> time, List<List<Integer>> distance) {
        String sql = "insert into Adjacencies (originNode, destinationNode, timeTo, distanceTo) values (?,?,?,?)";
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            for (int i = 0; i < numberOfNodes; i++) {
                for (int j = 0; j < numberOfNodes; j++) {
                    statement.setString(1, Integer.toString(i));
                    statement.setString(2, Integer.toString(j));
                    statement.setString(3, Integer.toString(time.get(i).get(j)));
                    statement.setString(4, Double.toString(distance.get(i).get(j)));
                    statement.execute();

                }
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumberOfNodes() {
        try {
            String sql = "select count(nodeId) from " + this.nodesTable;
            PreparedStatement statement = this.connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            int numberOfNodes = 0;
            while (resultSet.next()) {
                numberOfNodes = resultSet.getInt("count(nodeId)");
            }
            resultSet.close();
            statement.close();
            return numberOfNodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<List<Integer>> getAdjacenciesListOfDistances() {
        try {
            int numberOfNodes = this.getNumberOfNodes();
            List<List<Integer>> distanceBetweenNodes = new LinkedList<>();
            for (int i = 0; i < numberOfNodes; i++) {
                distanceBetweenNodes.add(new LinkedList<Integer>());
                for(int j = 0; j< numberOfNodes; j++){
                    distanceBetweenNodes.get(i).add(0);
                }
            }
            String sql = "select * from " + this.adjacenciesTable;
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer originNode = resultSet.getInt("originNode");
                Integer destinationNode = resultSet.getInt("destinationNode");
                Integer distanceTo = resultSet.getInt("distanceTo");
                distanceBetweenNodes.get(originNode).set(destinationNode, distanceTo);
            }
            resultSet.close();
            statement.close();
            return distanceBetweenNodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<List<Integer>> getAdjacenciesListOfTimes() {
        try {
            int numberOfNodes = this.getNumberOfNodes();
            List<List<Integer>> timeBetweenNodes = new LinkedList<>();
            for (int i = 0; i < numberOfNodes; i++) {
                timeBetweenNodes.add(new LinkedList<Integer>());
                for(int j = 0; j< numberOfNodes; j++){
                    timeBetweenNodes.get(i).add(0);
                }
            }
            String sql = "select * from " + this.adjacenciesTable;
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer originNode = resultSet.getInt("originNode");
                Integer destinationNode = resultSet.getInt("destinationNode");
                Integer distanceTo = (resultSet.getInt("timeTo"))/60;
                timeBetweenNodes.get(originNode).set(destinationNode, distanceTo);
            }
            resultSet.close();
            statement.close();
            return timeBetweenNodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
