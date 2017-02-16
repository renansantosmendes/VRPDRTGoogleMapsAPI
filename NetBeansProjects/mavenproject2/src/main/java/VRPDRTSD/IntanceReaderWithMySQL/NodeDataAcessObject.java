/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD.IntanceReaderWithMySQL;

import VRPDRTSD.IntanceReaderWithMySQL.ConnectionFactory;
import VRPDRTSD.Node;
import VRPDRTSD.Node;
import VRPDRTSD.Node;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author renansantos
 */
public class NodeDataAcessObject {
    private Connection connection;

    public NodeDataAcessObject() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void addNodeIntoDataBaseOfOrigins(Node node) {
        String sql = "insert into originNodes "
                + "(originNodeId, originLatitude, originLongitude)"
                + " values (?,?,?)";

        try {
            // prepared statement para inserção
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            // seta os valores
            stmt.setString(1, node.getNodeId().toString());
            stmt.setString(2, node.getLatitude().toString());
            stmt.setString(3, node.getLongitude().toString());
            
            // executa
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void addNodeIntoDataBaseOfDestinations(Node node) {
        String sql = "insert into destinationNodes "
                + "(destinationNodeId, destinationLatitude, destinationLongitude)"
                + " values (?,?,?)";

        try {
            // prepared statement para inserção
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            // seta os valores
            stmt.setString(1, node.getNodeId().toString());
            stmt.setString(2, node.getLatitude().toString());
            stmt.setString(3, node.getLongitude().toString());
            
            // executa
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Node> getListOfNodes() {
        try {

            List<Node> listOfNodes = new ArrayList<>();
            PreparedStatement stmt = this.connection.prepareStatement("select * from bh_nodes");
            //PreparedStatement stmt = this.connection.prepareStatement("select * from Nodes");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
               Node node = new Node(rs.getInt("nodeId"), rs.getDouble("latitude"), rs.getDouble("longitude"));               
               listOfNodes.add(node);
            }
            rs.close();
            stmt.close();
            return listOfNodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    
    public List<Node> getListOfOriginNodes() {
        try {

            List<Node> listOfNodes = new ArrayList<>();
            PreparedStatement stmt = this.connection.prepareStatement("select * from originNodes");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
               Node node = new Node(rs.getInt("originNodeId"), rs.getDouble("originLatitude"), rs.getDouble("originLongiture"));               
               listOfNodes.add(node);
            }
            rs.close();
            stmt.close();
            return listOfNodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    public List<Node> getListOfDestinationNodes() {
        try {

            List<Node> listOfNodes = new ArrayList<>();
            PreparedStatement stmt = this.connection.prepareStatement("select * from destinationNodes");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
               Node node = new Node(rs.getInt("destinationNodeId"), rs.getDouble("destinationLatitude"), rs.getDouble("destinationLongiture"));               
               listOfNodes.add(node);
            }
            rs.close();
            stmt.close();
            return listOfNodes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
