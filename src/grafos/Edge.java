package grafos;

import java.awt.*;

/**
 * Clase para las relaciones entre nodos
 * @author Sergio Suarez
 */
public class Edge {
    private Node.Position startPosition;
    private Node.Position endPosition;
    private int weight;
    private boolean isWeighted = false;
    private Color color = Color.black;

    private boolean focussed = false;

    private String name1;
    private String name2;
    
    /**
     * @author Sergio Suarez
     * @param node1
     * @param node2 
     */
    public Edge(Node node1, Node node2) {
        this(node1, node2, 0);
    }

    /**
     * @author Sergio Suarez
     * @param node1
     * @param node2
     * @param weight 
     */
    public Edge(Node node1, Node node2, int weight) {
        startPosition = node1.getPosition();
        endPosition = node2.getPosition();
        name1 = node1.getDisplayName();
        name2 = node2.getDisplayName();

        this.weight = weight;
        isWeighted = weight > 0;
    }

    /**
     * @author Sergio Suarez
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @author Sergio Suarez
     * @return weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @author Sergio Suarez
     * @return endPosition
     */
    public Node.Position getEndPosition() {
        return endPosition;
    }

    /**
     * @author Sergio Suarez
     * @return startPosition
     */
    public Node.Position getStartPosition() {
        return startPosition;
    }

    /**
     * @author Sergio Suarez
     * @return focussed
     */
    public boolean isFocussed() {
        return focussed;
    }

    /**
     * @author Sergio Suarez
     * @return isWeighted
     */
    public boolean isWeighted() {
        return isWeighted;
    }

    /**
     * @author Sergio Suarez
     * @param focussed 
     */
    public void setFocussed(boolean focussed) {
        this.focussed = focussed;
    }

    /**
     * @author Sergio Suarez
     * @param endPosition 
     */
    public void setEndPosition(Node endPosition) {
        this.endPosition = endPosition.getPosition();
    }

    /**
     * @author Sergio Suarez
     * @param startPosition 
     */
    public void setStartPosition(Node startPosition) {
        this.startPosition = startPosition.getPosition();
    }

    /**
     * @author Sergio Suarez
     * @param color 
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @author Sergio Suarez
     * @return position
     */
    public Node.Position getCenterPosition() {
        Node.Position position = new Node.Position();
        position.posX = (startPosition.posX + endPosition.posX) / 2;
        position.posY = (startPosition.posY + endPosition.posY) / 2;
        return position;
    }

    /**
     * @author Sergio Suarez
     * @param weight 
     */
    public void setWeight(int weight) {
        this.weight = weight;
        isWeighted = weight > 0;
    }

    /**
     * @author Sergio Suarez
     * @return deltaY / deltaX
     */
    public float getSlope() {
        float deltaX = startPosition.posX - endPosition.posX;
        float deltaY = startPosition.posY - endPosition.posY;
        return deltaY / deltaX;
    }

    /**
     * @author Sergio Suarez
     * @return name1
     */
    public String getStartName() {
        return name1;
    }

    /**
     * @author Sergio Suarez
     * @return name2
     */
    public String getEndName() {
        return name2;
    }
}
