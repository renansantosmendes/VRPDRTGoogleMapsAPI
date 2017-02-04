/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRPDRTSD.IntanceReaderWithMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import representacao.Request;

/**
 *
 * @author renansantos
 */
public class RequestDataAcessObject {

    private Connection connection;

    public RequestDataAcessObject() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void addRequestIntoDataBase(Request request) {
        String sql = "insert into requests250 "
                + "(id, passengerOrigin, passengerDestination, pickUpTimeWindowLower, pickUpTimeWindowUpper, "
                + "deliveryTimeWindowLower, deliveryTimeWindowUpper)"
                + " values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);

            statement.setString(1, request.getId().toString());
            statement.setString(2, Integer.toString(request.getOrigin()));
            statement.setString(3, Integer.toString(request.getDestination()));
            LocalDateTime pickUpTimeWindowLower, pickUpTimeWindowUpper, deliveryTimeWindowLower, deliveryTimeWindowUpper;
            LocalDateTime pickUpTime = null;
            LocalDateTime requestDay = null;
            //pickUpTimeWindowLower = LocalDateTime.of(2017, 1, 1, (int) request.getPickupE()/60, (int) request.getPickupE()%60);
            pickUpTimeWindowLower = LocalDateTime.of(2017, 1, 1, (int) request.getPickupE() / 60, 
                    (int) request.getPickupE() % 60);
            pickUpTimeWindowUpper = LocalDateTime.of(2017, 1, 1, (int) request.getPickupL() / 60, 
                    (int) request.getPickupL() % 60);
            deliveryTimeWindowLower = LocalDateTime.of(2017, 1, 1, (int) request.getDeliveryE() / 60, 
                    (int) request.getDeliveryE() % 60);
            deliveryTimeWindowUpper = LocalDateTime.of(2017, 1, 1, (int) request.getDeliveryL() / 60, 
                    (int) request.getDeliveryL() % 60);

            statement.setString(4, pickUpTimeWindowLower.toLocalTime().toString());
            statement.setString(5, pickUpTimeWindowUpper.toLocalTime().toString());
            statement.setString(6, deliveryTimeWindowLower.toLocalTime().toString());
            statement.setString(7, deliveryTimeWindowUpper.toLocalTime().toString());

//            statement.setString(4, request.getDayRequestWasMade().toString());
//            statement.setString(5, request.getPickUpTime().toLocalTime().toString());
//            statement.setString(6, request.getDeliveryTimeWindowLower().toLocalTime().toString());
//            statement.setString(7, request.getDeliveryTimeWindowUpper().toLocalTime().toString());
            // executa
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void addRequestIntoDataBaseVRPDRTSD(Request request) {
        String sql = "insert into VRPDRTSD_requests250 "
                + "(id, passengerOrigin, passengerDestination, requestDay, pickUpTime, "
                + "deliveryTimeWindowLower, deliveryTimeWindowUpper)"
                + " values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);

            statement.setString(1, request.getId().toString());
            statement.setString(2, Integer.toString(request.getOrigin()));
            statement.setString(3, Integer.toString(request.getDestination()));
            LocalDateTime pickUpTimeWindowLower, pickUpTimeWindowUpper, deliveryTimeWindowLower, deliveryTimeWindowUpper;
            LocalDateTime pickUpTime = LocalDateTime.of(2017, 1, 1, 0, 0);
            LocalDateTime requestDay = LocalDateTime.of(2017, 1, 1, 0, 0);
            //pickUpTimeWindowLower = LocalDateTime.of(2017, 1, 1, (int) request.getPickupE()/60, (int) request.getPickupE()%60);
            pickUpTimeWindowLower = LocalDateTime.of(2017, 1, 1, (int) request.getPickupE() / 60, 
                    (int) request.getPickupE() % 60);
            pickUpTimeWindowUpper = LocalDateTime.of(2017, 1, 1, (int) request.getPickupL() / 60, 
                    (int) request.getPickupL() % 60);
            deliveryTimeWindowLower = LocalDateTime.of(2017, 1, 1, (int) request.getDeliveryE() / 60, 
                    (int) request.getDeliveryE() % 60);
            deliveryTimeWindowUpper = LocalDateTime.of(2017, 1, 1, (int) request.getDeliveryL() / 60, 
                    (int) request.getDeliveryL() % 60);

            statement.setString(4, requestDay.toString());
            statement.setString(5, pickUpTime.toLocalTime().toString());
            statement.setString(6, deliveryTimeWindowLower.toLocalTime().toString());
            statement.setString(7, deliveryTimeWindowUpper.toLocalTime().toString());

//            statement.setString(4, request.getDayRequestWasMade().toString());
//            statement.setString(5, request.getPickUpTime().toLocalTime().toString());
//            statement.setString(6, request.getDeliveryTimeWindowLower().toLocalTime().toString());
//            statement.setString(7, request.getDeliveryTimeWindowUpper().toLocalTime().toString());
            // executa
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Request> getListOfRequest() {
        try {

            List<Request> listOfRequests = new ArrayList<Request>();
            PreparedStatement statement = this.connection.prepareStatement("select * from requests inner join originNodes on "
                    + "requests.passengerOrigin = originNodes.originNodeId inner join destinationNodes on "
                    + "requests.passengerDestination = destinationNodes.destinationNodeId;");

            ResultSet resultSetForRequests = statement.executeQuery();

            while (resultSetForRequests.next()) {

                int requestId = resultSetForRequests.getInt("requestId");
                int passengerOriginId = resultSetForRequests.getInt("originNodeId");
                double originLatitude = resultSetForRequests.getDouble("originLatitude");
                double originLongitude = resultSetForRequests.getDouble("originLongitude");
                int passengerDestinationId = resultSetForRequests.getInt("destinationNodeId");
                double destinationLatitude = resultSetForRequests.getDouble("destinationLatitude");
                double destinationLongitude = resultSetForRequests.getDouble("destinationLongitude");

                //Node passengerOrigin = new Node(passengerOriginId, originLatitude, originLongitude);
                //Node passengerDestination = new Node(passengerDestinationId, destinationLatitude, destinationLongitude);
                LocalDate requestDay = resultSetForRequests.getDate("requestDay").toLocalDate();

                LocalDateTime requestDayConverted = LocalDateTime.of(requestDay, LocalTime.MIN);
                LocalDateTime pickUpTime = LocalDateTime.of(requestDay, resultSetForRequests.getTime("pickUpTime").toLocalTime());
                LocalDateTime deliveryTimeWindowLower = LocalDateTime.of(requestDay, resultSetForRequests.getTime("deliveryTimeWindowLower").toLocalTime());
                LocalDateTime deliveryTimeWindowUpper = LocalDateTime.of(requestDay, resultSetForRequests.getTime("deliveryTimeWindowUpper").toLocalTime());

                //Request request = new Request(requestId, passengerOrigin, passengerDestination, requestDayConverted, pickUpTime, deliveryTimeWindowLower, deliveryTimeWindowUpper);
                //listOfRequests.add(request);
            }
            resultSetForRequests.close();
            statement.close();
            return listOfRequests;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

//    public List<Request> getListOfRequestUsingNodesList(List<Node> nodes) {
//        try {
//
//            List<Request> listOfRequests = new ArrayList<Request>();
//            PreparedStatement statement = this.connection.prepareStatement("select * from requests;");
//
//            ResultSet resultSetForRequests = statement.executeQuery();
//
//            while (resultSetForRequests.next()) {
//
//                int requestId = resultSetForRequests.getInt("requestId");
//                int passengerOriginId = resultSetForRequests.getInt("passengerOrigin");
//                int passengerDestinationId = resultSetForRequests.getInt("passengerDestination");
//                LocalDate requestDay = resultSetForRequests.getDate("requestDay").toLocalDate();
//
//                LocalDateTime requestDayConverted = LocalDateTime.of(requestDay, LocalTime.MIN);
//                LocalDateTime pickUpTime = LocalDateTime.of(requestDay, resultSetForRequests.getTime("pickUpTime").toLocalTime());
//                LocalDateTime deliveryTimeWindowLower = LocalDateTime.of(requestDay, resultSetForRequests.getTime("deliveryTimeWindowLower").toLocalTime());
//                LocalDateTime deliveryTimeWindowUpper = LocalDateTime.of(requestDay, resultSetForRequests.getTime("deliveryTimeWindowUpper").toLocalTime());
//
//                Request request = new Request(requestId, nodes.stream().filter(u -> u.getNodeId() == passengerOriginId).collect(Collectors.toList()).get(0), 
//                        nodes.stream().filter(u -> u.getNodeId() == passengerDestinationId).collect(Collectors.toList()).get(0),requestDayConverted, 
//                        pickUpTime, deliveryTimeWindowLower, deliveryTimeWindowUpper);
//
//                listOfRequests.add(request);
//            }
//            resultSetForRequests.close();
//            statement.close();
//            return listOfRequests;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
