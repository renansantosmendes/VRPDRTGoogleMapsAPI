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
     * @return int - returns the node load index: number of passenger which boards
     * in this node minus the number of passenger which leaves the vehicle in this
     * node
     */
    public int getLoadIndex() {
        return loadIndex;
    }

    public void setLoadIndex(int loadIndex){
        this.loadIndex = loadIndex;
    }
    
    
    @Override
    public String toString(){
        return "Node(" + this.nodeId +") " + "Lat = " + this.latitude + " Long = " + this.longitude;
    }
    
    @Override
    public boolean equals(Object obj){
       return obj instanceof Node && equals((Node) obj);
    }
    
    public boolean equals(Node node){
        if(this.getNodeId() == node.getNodeId()){
            if(this.getLatitude() == node.getLatitude() && this.getLongitude() == node.getLongitude()){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode(){
        String string = Integer.toString(this.getNodeId());
        int hash = string.hashCode();
        return hash;
    }
}
