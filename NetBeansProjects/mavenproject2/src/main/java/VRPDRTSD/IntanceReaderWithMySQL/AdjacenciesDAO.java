/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD.IntanceReaderWithMySQL;

import VRPDRTSD.Node;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

/**
 *
 * @author renansantos
 */
public class AdjacenciesDAO {

    private Connection connection;
    private String tableInDataBase = "Adjacencies_bh_nodes";

    public AdjacenciesDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public Duration[][] getDurationBetweenNodes(int numberOfNodes) {
        try {
            Duration[][] durationBetweenNodes = new Duration[numberOfNodes][numberOfNodes];
            PreparedStatement statement = this.connection.prepareStatement("select * from " + tableInDataBase);
            ResultSet resultSet = statement.executeQuery();
            int i = 0, j = 0;
            while (resultSet.next()) {
                int originNode = resultSet.getInt("originNode");
                int destinationNode = resultSet.getInt("destinationNode");
                Duration durationTo = Duration.ofMinutes(resultSet.getInt("timeTo"));
                Double distanceTo = resultSet.getDouble("distanceTo");
                durationBetweenNodes[i][j] = durationTo;
                j++;
                if(j == numberOfNodes){
                    i++;
                    j = 0;
                }
            }
            return durationBetweenNodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public double[][] getDistanceBetweenNodes(int numberOfNodes) {
        try {
            double[][] distanceBetweenNodes = new double[numberOfNodes][numberOfNodes];
            PreparedStatement statement = this.connection.prepareStatement("select * from " + tableInDataBase);
            ResultSet resultSet = statement.executeQuery();
            int i = 0, j = 0;
            while (resultSet.next()) {
                int originNode = resultSet.getInt("originNode");
                int destinationNode = resultSet.getInt("destinationNode");
                Duration durationTo = Duration.ofMinutes(resultSet.getInt("timeTo"));
                Double distanceTo = resultSet.getDouble("distanceTo");
                distanceBetweenNodes[i][j] = distanceTo;
                j++;
                if(j == numberOfNodes){
                    i++;
                    j = 0;
                }
            }
            return distanceBetweenNodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
