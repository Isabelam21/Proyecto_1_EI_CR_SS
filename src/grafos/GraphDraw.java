package grafos;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase del grafico del nodo
 * @author Sergio Suarez
 */
public class GraphDraw extends JComponent {

    private static final int MINIMUM_EDGE_SIZE = 32;
    private static final int EDGE_PADDING = 8;

    private Color backGroundColor = Color.white;

    private List<Edge> edgeList = new ArrayList<>();
    private List<Node> nodesList = new ArrayList<>();

    /**
     * @author Sergio Suarez
     * @param edgeList 
     */
    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    /**
     * @author Sergio Suarez
     * @param nodesList 
     */
    public void setNodesList(List<Node> nodesList) {
        this.nodesList = nodesList;
    }

    /**
     * @author Sergio Suarez
     * @param node 
     */
    public void addNode(Node node) {
        nodesList.add(node);
    }

    /**
     * @author Sergio Suarez
     * @param edge 
     */
    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }
    
    /**
     * @author Sergio Suarez
     * @param id
     * @return si el id existe, retorna node. Si no, retorna null.
     */
    public Node getNode(int id) {
        for(Node node : nodesList){ 
            if(node.getComponentNumber() == id){
                return node;
            }
        }
        return null;
    }

    private FontMetrics metrics;

    @Override
    /**
     * @author Sergio Suarez
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        metrics = g2d.getFontMetrics();
        g2d.setColor(backGroundColor);
        paintBackground(g2d);

        drawEdges(g2d);

        drawNodes(g2d);
    }

    /**
     * @author Sergio Suarez
     * @param g2d 
     */
    private void drawNodes(Graphics2D g2d) {
        for (Node node : nodesList) {
            drawNode(g2d, node);
        }
    }

    /**
     * @author Sergio Suarez
     * @param g2d
     * @param node 
     */
    private void drawNode(Graphics2D g2d, Node node) {
        g2d.setColor(backGroundColor);
        int width = measure(node.getDisplayName());
        g2d.fillOval(
                update(node.getPosition().posX),
                update(node.getPosition().posY),
                width,
                width
        );

        g2d.setColor(node.getColor());
        Rectangle2D rect = metrics.getStringBounds(node.getDisplayName(), g2d);
        int strWidth = rect.getBounds().width;
        int strHeight = rect.getBounds().height;
        int x = node.getPosition().posX;
        int y = node.getPosition().posY;
        g2d.drawString(node.getDisplayName(),
                update(x + (width - strWidth) / 2),
                update(y + (width + strHeight / 2) / 2));

        g2d.drawOval(
                update(node.getPosition().posX),
                update(node.getPosition().posY),
                width,
                width
        );
    }

    /**
     * @author Sergio Suarez
     * @param text
     * @return width + 2 * EDGE_PADDING
     */
    private int measure(String text) {
        int width = metrics.stringWidth(text);
        if (width < MINIMUM_EDGE_SIZE - 5) {
            return MINIMUM_EDGE_SIZE;
        }
        return width + 2 * EDGE_PADDING;
    }

    /**
     * @author Sergio Suarez
     * @param text
     * @return metrics.stringWidth(text)
     */
    private int measureWithoutPadding(String text) {
        return metrics.stringWidth(text);
    }

    /**
     * @author Sergio Suarez
     * @param g2d 
     */
    private void drawEdges(Graphics2D g2d) {
        for (Edge edge : edgeList) {
            drawEdge(g2d, edge);
        }
    }

    /**
     * @author Sergio Suarez
     * @param g2d
     * @param edge 
     */
    private void drawEdge(Graphics2D g2d, Edge edge) {
        g2d.setColor(edge.getColor());
        if (edge.isFocussed()) {
            g2d.setStroke(new BasicStroke(3));
        }
        Node.Position start = edge.getStartPosition();
        Node.Position end = edge.getEndPosition();
        int sWidth = measure(edge.getStartName());
        int eWidth = measure(edge.getEndName());
        g2d.drawLine(
                update(start.posX + sWidth / 2),
                update(start.posY + sWidth / 2),
                update(end.posX + eWidth / 2),
                update(end.posY + eWidth / 2)
        );
        g2d.setStroke(new BasicStroke(1));

        Node.Position cPos = edge.getCenterPosition();
        int width = measureWithoutPadding(edge.getWeight() + "");

        //draw weight on the line
        if (edge.isWeighted()) {
            float slop = edge.getSlope();
            if (slop < 0)
                g2d.drawString(edge.getWeight() + "",
                        update(cPos.posX - width / 2),
                        update(cPos.posY - width / 2));
            else
                g2d.drawString(edge.getWeight() + "",
                        update(cPos.posX + width / 2),
                        update(cPos.posY - width / 2));
        }
    }

    /**
     * @author Sergio Suarez
     * @param i
     * @return i + 20
     */
    public int update(int i) {
        return i + 20;
    }

    /**
     * @author Sergio Suarez
     * @param g2d 
     */
    private void paintBackground(Graphics2D g2d) {
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
