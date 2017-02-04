/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD.IntanceReaderWithMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author renansantos
 */
public class AdjacenciesDataAcessObject {

    private Connection connection;

    public AdjacenciesDataAcessObject() {
        this.connection = new ConnectionFactory().getConnection();
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
}
