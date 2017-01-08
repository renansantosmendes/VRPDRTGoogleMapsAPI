package VRPDRTSD;

/**
 * 
 * @author Renan Santos Mendes
 * The Node Class represents the node used in VRPDRTSD
 */
public class Node {
    private final Integer nodeId;
    private final Double longitude;
    private final Double latitude;
    
       
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
    
    @Override
    public String toString(){
        return "Node(" + this.nodeId +") " + "Lat = " + this.latitude + " Long = " + this.longitude;
    }
}
