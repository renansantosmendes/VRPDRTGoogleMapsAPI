package VRPDRTSD;

import java.time.Duration;
import java.time.LocalDateTime;

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
     * @param deliveryTimeWindowUpper Sets the upper limit for the delivery time
     * window
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
     * @return LocalDateTime - returns the upper limit for the delivery time
     * window
     */
    public LocalDateTime getDeliveryTimeWindowUpper() {
        return deliveryTimeWindowUpper;
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
    
    public String toString(){
        return "Request: id = " + this.requestId + " Passenger Origin = " + this.passengerOrigin.getNodeId() + 
                " Passenger Destination = " + this.passengerDestination.getNodeId()
                + "\nTime Window Lower = " + this.deliveryTimeWindowLower
                + "\nTime Window Upper = " + this.deliveryTimeWindowUpper;
    }

}
