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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author renansantos
 */
public class NumberOfNodesDataAcessObject {

    private Connection connection;
    private String instanceName;

    public NumberOfNodesDataAcessObject() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public int getNumberOfNodes(String instanceName) {
        try {

            List<Node> listOfNodes = new ArrayList<>();
            PreparedStatement stmt = this.connection.prepareStatement("select * from NumberOfNodes");
            int numberOfNodes = 0;
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("instanceName");
                int nodes = resultSet.getInt("numberOfNodes");
                
                if(instanceName.equals(name)){
                    numberOfNodes = nodes;
                }
            }
            resultSet.close();
            stmt.close();
            return numberOfNodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
