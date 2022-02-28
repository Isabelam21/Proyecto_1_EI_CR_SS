package grafos;

import java.awt.*;

/**
 * Clase nodo, los nodos son los usuarios en este proyecto
 * @author Roman Chacín
 */
public class Node {
    private boolean automaticName = true;
    private String displayName;
    
   private int state;
   private int componentNumber;

    private Color color = Color.black;

    private Position position;
    
    private int discoveryTime;
    
    private int lowTime;
    
    private int finishingTime;
    
    private Node dfsParent;
    
    private Node next;
    
    private Object element;
    
    private int value;

    public Node() {

    }
    
    /**
     * @author Roman Chacín
     * @param next 
     */
    public void setNext(Node next){
        this.next = next;
    }
    
    /**
     * @author Roman Chacín
     * @return 
     */
    public Object getElement(){
        return element;
    }
    
    /**
     * @author Roman Chacín
     * @return 
     */
    public Node getNext(){
        return next;
    }

    /**
     * 
     * @author Roman Chacín
     * @param edgePosition 
     */
    public Node(Position edgePosition) { 
        this.position = edgePosition;
    }
    
    /**
     * @author Roman Chacín
     * @param name 
     */
     public Node(String name) {
         automaticName = false;
       this.displayName = name; 
    }
     
    /**
     * @author Roman Chacín
     * @param name
     * @param componentNumber 
     */
     public Node(String name, int componentNumber) {
         automaticName = false;
       this.displayName = name; 
       this.componentNumber = componentNumber;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
     
     

     /**
      * @author Roman Chacín
      * @return componentNumber
      */
    public int getComponentNumber() {
        return componentNumber;
    }
    
    /**
     * @author Roman Chacín
     * @param componentNumber 
     */
    public void setComponentNumber(int componentNumber) {
        this.componentNumber = componentNumber;
    }

    /**
     * @author Roman Chacín
     * @return state
     */
    public int getState() {
        return state;
    }

    /**
     * @author Roman Chacín
     * @param state 
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * @author Roman Chacín
     * @param displayName 
     */
    public void setDisplayName(String displayName) {
        automaticName = false;
        this.displayName = displayName;
    }

    /**
     * @author Roman Chacín
     * @param position 
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @author Roman Chacín
     * @param color 
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @author Roman Chacín
     * @return color
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * @author Roman Chacín
     * @return automaticName
     */
    public boolean isAutomaticName() {
        return automaticName;
    }

    /**
     * @author Roman Chacín
     * @return displayName
     */
    public String getDisplayName() {
        if (isAutomaticName()) {
            displayName = NameUtil.getInstance().getName();
        }
        return displayName;
    }

    /**
     * @author Roman Chacín
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @author Roman Chacín
     * @param i 
     */
    void setDiscoveryTime(int i) {
        this.discoveryTime = i;
    }
    
    /**
     * @author Roman Chacín
     * @param i 
     */
    void setFinishingTime(int i) {
        this.finishingTime = i;
    }

    /**
     * @author Roman Chacín
     * @return discoveryTime
     */
    public int getDiscoveryTime() {
        return discoveryTime;
    }

    /**
     * @author Roman Chacín
     * @return finishingTime
     */
    public int getFinishingTime() {
        return finishingTime;
    }

    /**
     * @author Roman Chacín
     * @param i 
     */
    void setLowTime(int i) {
        this.lowTime = i;
    }

    /**
     * @author Roman Chacín
     * @return lowTime
     */
     public int getLowTime() {
        return lowTime;
    }

    /**
     * @author Roman Chacín
     * @param node 
     */
    void setParent(Node node) {
        this.dfsParent = node;
    }

    /**
     * @author Roman Chacín
     * @return dfsParent
     */
    public Node getDfsParent() {
        return dfsParent;
    }
     
    /**
     * @author Roman Chacín
     */
    public static class Position {
        int posX;
        int posY;

        public Position() {
            this(0, 0);
        }

        public Position(int posX, int posY) {

            this.posX = posX;
            this.posY = posY;
        }
    }
}
