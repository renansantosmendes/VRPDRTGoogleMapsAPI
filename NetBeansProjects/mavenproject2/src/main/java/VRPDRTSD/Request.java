package VRPDRTSD;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author renansantos The Request Class represents the request for transport
 * used in VRPDRTSD
 */
public class Request {

    private final Integer requestId;
    private final Node passengerOrigin;
    private final Node passengerDestination;
    private LocalDateTime dayRequestWasMade;
    private LocalDateTime pickUpTime;
    private LocalDateTime deliveryTimeWindowLower;
    private LocalDateTime deliveryTimeWindowUpper;
    private boolean feasible;
    private boolean boarded;
    private double requestRankingFunction;
    private double distanceRankingFunction;
    private double distanceToAttendThisRequest;
    private double deliveryTimeWindowLowerRankingFunction;
    private double deliveryTimeWindowUpperRankingFunction;
    private double originNodeRankingFunction;
    private double destinationNodeRankingFunction;

    /**
     *
     * @param requestId - Sets the request identification
     * @param passengerOrigin - Sets the passenger origin node
     * @param passengerDestination - Sets the passenger destination node
     * @param dayRequestWasMade - Sets the day in which the request was made
     * @param pickUpTime - Sets the moment that passenger board in the vehicle
     * @param deliveryTimeWindowLower - Sets the lower limit for the delivery
     * time window
     * @param deliveryTimeWindowUpper - Sets the upper limit for the delivery
     * time window
     */
    public Request(Integer requestId, Node passengerOrigin, Node passengerDestination, LocalDateTime dayRequestWasMade,
            LocalDateTime pickUpTime, LocalDateTime deliveryTimeWindowLower, LocalDateTime deliveryTimeWindowUpper) {
        this.requestId = requestId;
        this.passengerOrigin = passengerOrigin;
        this.passengerDestination = passengerDestination;
        this.dayRequestWasMade = dayRequestWasMade;
        this.pickUpTime = pickUpTime;
        this.deliveryTimeWindowLower = deliveryTimeWindowLower;
        this.deliveryTimeWindowUpper = deliveryTimeWindowUpper;
        this.feasible = false;
        this.boarded = false;
        this.requestRankingFunction = -1.0;
        this.distanceRankingFunction = 0.0;
        this.distanceToAttendThisRequest = 0.0;
        this.deliveryTimeWindowLowerRankingFunction = 0.0;
        this.deliveryTimeWindowUpperRankingFunction = 0.0;
        this.originNodeRankingFunction = 0.0;
        this.destinationNodeRankingFunction = 0.0;
    }

    /**
     *
     * @param dayRequestWasMade - Sets the day in which the request was made
     */
    public void setDayRequestWasMade(LocalDateTime dayRequestWasMade) {
        this.dayRequestWasMade = dayRequestWasMade;
    }

    /**
     *
     * @param pickUpTime - Sets the time in which the passenger board the
     * vehicle
     */
    public void setPickUpTime(LocalDateTime pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    /**
     *
     * @param deliveryTimeWindowLower - Sets the lower limit for the delivery
     * time window
     */
    public void setDeliveryTimeWindowLower(LocalDateTime deliveryTimeWindowLower) {
        this.deliveryTimeWindowLower = deliveryTimeWindowLower;
    }

    /**
     *
     * @param deliveryTimeWindowUpper - Sets the upper limit for the delivery
     * time window
     */
    public void setDeliveryTimeWindowUpper(LocalDateTime deliveryTimeWindowUpper) {
        this.deliveryTimeWindowUpper = deliveryTimeWindowUpper;
    }

    /**
     *
     * @param feasible - Sets if the request is feasible or not
     */
    public void setFeasible(boolean feasible) {
        this.feasible = feasible;
    }

    /**
     *
     * @param boarded - Sets if the passenger is boarded in one vehicle
     */
    public void setBoarded(boolean boarded) {
        this.boarded = boarded;
    }

    /**
     *
     * @param requestRankingFunction (define the parameters - not done yet)
     */
    public void setRequestRankingFunction(double distanceCoeficient, double deliveryTimeWindowLowerCoeficient,
            double deliveryTimeWindowUpperCoeficient, double originNodeCoeficient, double destinationNodeCoeficient) {

        this.requestRankingFunction = distanceCoeficient * this.distanceRankingFunction
                + deliveryTimeWindowLowerCoeficient * this.deliveryTimeWindowLowerRankingFunction
                + deliveryTimeWindowUpperCoeficient * this.deliveryTimeWindowUpperRankingFunction
                + originNodeCoeficient * this.originNodeRankingFunction
                + destinationNodeCoeficient * this.destinationNodeRankingFunction;
    }

    public void setDistanceRankingFunction(double maxDistance, double minDistance) {
        this.distanceRankingFunction
                = (maxDistance - this.getDistanceToAttendThisRequest()) / (maxDistance - minDistance);
    }

    public void setDistanceToAttendThisRequest(Node currentNode, double distanceMatrix[][]) {
        this.distanceToAttendThisRequest = distanceMatrix[currentNode.getNodeId()][this.getPassengerOrigin().getNodeId()]
                + distanceMatrix[this.getPassengerOrigin().getNodeId()][this.getPassengerDestination().getNodeId()];
    }

    public void setDeliveryTimeWindowLowerRankingFunction(int maxTimeWindowLower, int minTimeWindowLower) {
        this.deliveryTimeWindowLowerRankingFunction
                = (maxTimeWindowLower - this.getDeliveryTimeWindowLowerInMinutes()) / (maxTimeWindowLower - minTimeWindowLower);
    }

    public void setDeliveryTimeWindowUpperRankingFunction(int maxTimeWindowUpper, int minTimeWindowUpper) {
        this.deliveryTimeWindowUpperRankingFunction
                = (maxTimeWindowUpper - this.getDeliveryTimeWindowLowerInMinutes()) / (maxTimeWindowUpper - minTimeWindowUpper);
    }

    public void setOriginNodeRankingFunction(int maxLoadIndex, int minLoadIndex) {
        this.originNodeRankingFunction
                = (double) (this.getPassengerOrigin().getLoadIndex() - minLoadIndex) / (maxLoadIndex - minLoadIndex);
    }

    public void setDestinationNodeRankingFunction(int maxLoadIndex, int minLoadIndex) {
        this.destinationNodeRankingFunction
                = (double) (this.getPassengerDestination().getLoadIndex() - minLoadIndex) / (maxLoadIndex - minLoadIndex);;
    }

    /**
     *
     * @return Integer - returns the request Id
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     *
     * @return Node - returns the passenger origin node
     */
    public Node getPassengerOrigin() {
        return passengerOrigin;
    }

    /**
     *
     * @return Node - returns the passenger destination node
     */
    public Node getPassengerDestination() {
        return passengerDestination;
    }

    /**
     *
     * @return LocalDateTime - returns the day in which the request was made
     */
    public LocalDateTime getDayRequestWasMade() {
        return dayRequestWasMade;
    }

    /**
     *
     * @return LocalDateTime - returns the moment when the passenger boards in
     * vehicle
     */
    public LocalDateTime getPickUpTime() {
        return pickUpTime;
    }

    /**
     *
     * @return LocalDateTime - returns the lower limit for the delivery time
     * window
     */
    public LocalDateTime getDeliveryTimeWindowLower() {
        return deliveryTimeWindowLower;
    }

    /**
     *
     * @return Integer - returns the lower limit for the delivery time window in
     * minutes
     */
    public Integer getDeliveryTimeWindowLowerInMinutes() {
        return deliveryTimeWindowLower.getHour() * 60 + deliveryTimeWindowLower.getMinute();
    }

    /**
     *
     * @return LocalDateTime - returns the upper limit for the delivery time
     * window
     */
    public LocalDateTime getDeliveryTimeWindowUpper() {
        return deliveryTimeWindowUpper;
    }

    /**
     *
     * @return Integer - returns the lower limit for the delivery time window in
     * minutes
     */
    public Integer getDeliveryTimeWindowUpperInMinutes() {
        return deliveryTimeWindowUpper.getHour() * 60 + deliveryTimeWindowUpper.getMinute();
    }

    /**
     *
     * @return bool - returns true if the request is feasible and false if it
     * isn't
     */
    public boolean isFeasible() {
        return feasible;
    }

    /**
     *
     * @return bool - returns true if the passenger boarded in the vehicle and
     * false if the passenger didn't board - it could be used to analyse if the
     * passenger already leaves the vehicle
     */
    public boolean isBoarded() {
        return boarded;
    }

    /**
     *
     * @return double - Returns the request evaluation value. This evaluation is
     * used in greedy constructive algorithm
     */
    public double getRequestRankingFunction() {
        return requestRankingFunction;
    }

    public double getDistanceRankingFunction() {
        return distanceRankingFunction;
    }

    public double getDistanceToAttendThisRequest() {
        return distanceToAttendThisRequest;
    }

    public double getDeliveryTimeWindowLowerRankingFunction() {
        return deliveryTimeWindowLowerRankingFunction;
    }

    public double getDeliveryTimeWindowUpperRankingFunction() {
        return deliveryTimeWindowLowerRankingFunction;
    }

    public double getOriginNodeRankingFunction() {
        return originNodeRankingFunction;
    }

    public double getDestinationNodeRankingFunction() {
        return destinationNodeRankingFunction;
    }

    /**
     * This method analyses the feasibility of the request according to the
     * current node and current time wherein the vehicle is
     *
     * @param currentTime - the actual time, used to calculate the time until
     * arrive in the node
     * @param currentNode - the vehicle current node
     * @param timeMatrix - a matrix with the jouney time between all nodes
     */
    public void determineFeasibility(LocalDateTime currentTime, Node currentNode, Duration timeMatrix[][]) {

        Duration durationUntilVehicleArrivesPickUpNode = timeMatrix[currentNode.getNodeId()][this.getPassengerOrigin().getNodeId()];
        Duration durationBetweenOriginAndDestination = timeMatrix[this.getPassengerOrigin().getNodeId()][this.getPassengerDestination().getNodeId()];

        Duration totalDuration = durationUntilVehicleArrivesPickUpNode.plus(durationBetweenOriginAndDestination);

        if (currentTime.plus(totalDuration).isBefore(this.getDeliveryTimeWindowUpper())) {
            this.setFeasible(true);
        }
    }

    /**
     *
     * @return String - returns a string with the request informations
     */
    public String toString() {

//        double RRF = this.requestRankingFunction;
//        DecimalFormat formato = new DecimalFormat("#.####");
//        RRF = Double.valueOf(formato.format(RRF));

        return "Request: id = " + this.requestId + " Passenger Origin = " + this.passengerOrigin.getNodeId()
                + " Passenger Destination = " + this.passengerDestination.getNodeId()
                + "\nTime Window Lower = " + this.deliveryTimeWindowLower
                + "\nTime Window Upper = " + this.deliveryTimeWindowUpper + "\nRRF = " + this.requestRankingFunction + "\n";
    }

}
