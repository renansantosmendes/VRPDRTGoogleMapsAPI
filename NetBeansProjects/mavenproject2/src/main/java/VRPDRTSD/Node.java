package VRPDRTSD;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Renan Santos Mendes The Node Class represents the node used in
 * VRPDRTSD
 */
public class Node {

    private final Integer nodeId;
    private final Double longitude;
    private final Double latitude;
    private int loadIndex;

    /**
     *
     * @param nodeId - id used to refer the node
     * @param longitude - longitude value
     * @param latitude - latitude value
     */
    public Node(Integer nodeId, Double longitude, Double latitude) {
        this.nodeId = nodeId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.loadIndex = 0;
    }

    /**
     *
     * @return Integer - returns the node id in Integer
     */
    public Integer getNodeId() {
        return nodeId;
    }

    /**
     *
     * @return Double - returns the longitude value in Double
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @return Double - returns the latitude value in Double
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @return int - returns the node load index: number of passenger which
     * boards in this node minus the number of passenger which leaves the
     * vehicle in this node
     */
    public int getLoadIndex() {
        return loadIndex;
    }

    public void setLoadIndex(Map<Node, List<Request>> requestsWichBoardsInNode, Map<Node, List<Request>> requestsWichLeavesInNode) {
        if (requestsWichBoardsInNode.get(this) != null && requestsWichLeavesInNode.get(this) != null) {
            this.loadIndex = requestsWichBoardsInNode.get(this).size() - requestsWichLeavesInNode.get(this).size();
        }
    }

    @Override
    public String toString() {
        return "Node(" + this.nodeId + ") " + "Lat = " + this.latitude + " Long = "
                + this.longitude + " LoadIndex = " + this.loadIndex;
    }

    public String toStringForMapQuery() {
        String color;
        String label;
        if (this.nodeId == 0) {
            color = "red";
            label = "O";
        } else {
            color = "blue";
            label = "S";
        }

        //return "&markers=color:blue|label:" + this.nodeId + "|" + this.longitude + "," + this.latitude;
        return "&markers=color:" + color + "|label:" + label + "|" + this.longitude + "," + this.latitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Node node = (Node) obj;
        return this.getNodeId() == node.getNodeId();
    }

    @Override
    public int hashCode() {
        String string = Integer.toString(this.getNodeId());
        int hash = string.hashCode();
        return hash;
    }
}
