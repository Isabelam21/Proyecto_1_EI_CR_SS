/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grafos;

import java.util.LinkedList;
//import java.util.Queue;
import java.util.Scanner;
//import java.util.Stack;

/**
 * Clase UndirectedGraph, en esta clase se crean y se manejan las matrices de
 * adyacencia Para representar las aristas se usan matrices de adyacencia, son
 * booleanas 0/1 false/true
 *
 * @author Isabela Espinoza
 */
public class UndirectedGraph {

    public final int MAX_VERTICES = 30;

    private static final int INITIAL = 0;
    private static final int WAITING = 1;
    private static final int VISITED = 2;

    private static final int DINITIAL = 0;
    private static final int DVISITED = 1;
    private static final int DFINISHED = 2;
    private static int time;

    int n;
    int e;
    boolean[][] adj;
    Node[] nodeList;
    String bridges = "";

    /**
     * Se crea la matriz y una lista de nodos
     */
    public UndirectedGraph() {

        adj = new boolean[MAX_VERTICES][MAX_VERTICES];
        nodeList = new Node[MAX_VERTICES];
    }

    /**
     * Funcion para saber si el grafo esta completamente conectado de no ser
     * asi, la función nos dice las islas del grafo
     *
     * @author Isabela Espinoza
     * @return isConnected
     */
    public String isConnected() {
        String isConnected;

        for (int v = 0; v < n; v++) {
            nodeList[v].setState(INITIAL);
        }

        int cN = 1;
        bfsC(0, cN);

        for (int v = 0; v < n; v++) {
            if (nodeList[v].getState() == INITIAL) {

                cN++;
                bfsC(v, cN);
            }
        }

        if (cN == 1) {
            isConnected = "El grafo esta completamente conectado";
            System.out.println(isConnected);
            return isConnected;
        } else {
            isConnected = "El grafo no esta completamente conectado, tiene " + cN + " islas";
            System.out.println(isConnected);
            return isConnected;
        }
    }

    /**
     * Función que muestra la cantidad de islas mediante el recorrido de
     * profundidad
     *
     * @author Isabela Espinoza
     * @return string isConnected que es el mensaje en respuesta
     */
    public String isConnectedDFS() {
        String isConnected;

        for (int v = 0; v < n; v++) {
            nodeList[v].setState(DINITIAL);
        }

        int cN = 0;
        dfsC(0, cN);

        for (int v = 0; v < n; v++) {
            if (nodeList[v].getState() == DINITIAL) {

                cN++;
                dfsC(v, cN);
            }
        }

        if (cN == 1) {
            isConnected = "El grafo esta completamente conectado";
            System.out.println(isConnected);
            return isConnected;
        } else {
            isConnected = "El grafo no esta completamente conectado, tiene " + cN + " islas";
            System.out.println(isConnected);
            return isConnected;
        }
    }

    /**
     *
     * @author Isabela Espinoza
     */
    public void dfsTraversal() {

        for (int v = 0; v < n; v++) {
            nodeList[v].setState(DINITIAL);
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce nodo inicial para Breadth First Search : ");
        String s = scan.next();
        dfs(getIndex(s));
    }

    /**
     *
     * @author Isabela Espinoza
     */
    public void dfsTraversal_All() {

        for (int v = 0; v < n; v++) {
            nodeList[v].setState(DINITIAL);
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce nodo inicial para Breadth First Search : ");
        String s = scan.next();
        dfs(getIndex(s));

        for (int v = 0; v < n; v++) {

            if (nodeList[v].getState() == DINITIAL) {
                dfs(v);
            }
        }
    }

    /**
     *
     * @author Isabela Espinoza
     * @param v
     */
    private void dfs(int v) {

//        Stack<Integer> st = new Stack();
        //  st.push(v);
        Stack st = new Stack();
        //  st.push(v);
        Node node = new Node();
        node.setValue(v);
        st.push(node);
        //st.push(v);

        while (!st.isEmpty()) {

            //v = st.pop();
            v = st.pop().getValue();
            if (nodeList[v].getState() == DINITIAL) {

                System.out.println(nodeList[v] + " ");
                nodeList[v].setState(DVISITED);
            }

            for (int i = n - 1; i >= 0; i--) {

                if (isAdjacent(v, i) && nodeList[v].getState() == DINITIAL) {
                    Node no = new Node();
                    no.setValue(i);
                    st.push(no);
                    //st.push(i);
                }
            }
        }
        System.out.println();
    }

    /**
     *
     * @author Isabela Espinoza
     * @param v
     * @param cN
     */
    private void dfsC(int v, int cN) {

        //  Stack<Integer> st = new Stack();
        Stack st = new Stack();
        //  st.push(v);
        Node node = new Node();
        node.setValue(v);
        st.push(node);
        //st.push(v);

        while (!st.isEmpty()) {

            v = st.pop().getValue();
            //v = st.pop();
            if (nodeList[v].getState() == DINITIAL) {

                System.out.println(nodeList[v] + " ");
                nodeList[v].setState(DVISITED);
                nodeList[v].setComponentNumber(cN);
            }

            for (int i = n - 1; i >= 0; i--) {

                if (isAdjacent(v, i) && nodeList[v].getState() == DINITIAL) {
                    Node no = new Node();
                    no.setValue(i);
                    st.push(no);
                    //st.push(i);
                }
            }
        }
        System.out.println();
    }

    /**
     *
     * @author Isabela Espinoza
     * @param v
     * @param cN
     */
    private void bfsC(int v, int cN) {

        //Queue<Integer> qu = new LinkedList();
        Queue queue = new Queue();
        Node node = new Node();
        node.setValue(v);
        queue.queue(node);
        //    qu.add(v);
        nodeList[v].setState(WAITING);

        while (!queue.isEmpty()) {
            v = queue.dequeue().getValue();
            nodeList[v].setState(VISITED);
            nodeList[v].setComponentNumber(cN);

            for (int i = 0; i < n; i++) {
                if (isAdjacent(v, i) && nodeList[i].getState() == INITIAL) {
                    Node no = new Node();
                    no.setValue(i);
                    queue.queue(no);
                    //qu.add(i);
                    nodeList[i].setState(WAITING);
                }
            }
        }

//        while(!qu.isEmpty()){
//            v = qu.remove();
//            nodeList[v].setState(VISITED);
//            nodeList[v].setComponentNumber(cN);
//            
//            for(int i = 0; i < n; i++){
//                if(isAdjacent(v, i) && nodeList[i].getState() == INITIAL){
//                    qu.add(i);
//                    nodeList[i].setState(WAITING);
//                }
//            }
//        }
    }

    /**
     *
     * @author Isabela Espinoza
     */
    public void bfsTraversal() {

        for (int v = 0; v < n; v++) {
            nodeList[v].setState(INITIAL);
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce nodo inicial para Breadth First Search : ");
        String s = scan.next();
        bfs(getIndex(s));

    }

    /**
     *
     * @author Isabela Espinoza
     * @param v
     */
    private void bfs(int v) {
//        Queue<Integer> qu = new LinkedList();
        Queue queue = new Queue();
        //  qu.add(v);
        Node node = new Node();
        node.setValue(v);
        queue.queue(node);
        nodeList[v].setState(WAITING);

        while (!queue.isEmpty()) {

            v = queue.dequeue().getValue();
            System.out.println(nodeList[v] + " ");
            nodeList[v].setState(VISITED);

            for (int i = 0; i < n; i++) {
                if (isAdjacent(v, i) && nodeList[i].getState() == INITIAL) {
                    Node no = new Node();
                    no.setValue(i);
                    queue.queue(no);
                    // qu.add(i);
                    nodeList[i].setState(WAITING);
                }
            }
        }

//        while (!qu.isEmpty()) {
//
//            v = qu.remove();
//            System.out.println(nodeList[v] + " ");
//            nodeList[v].setState(VISITED);
//
//            for (int i = 0; i < n; i++) {
//                if (isAdjacent(v, i) && nodeList[i].getState() == INITIAL) {
//                    qu.add(i);
//                    nodeList[i].setState(WAITING);
//                }
//            }
//        }
        System.out.println();
    }

    /**
     *
     * @author Isabela Espinoza
     */
    public void bfsTraversal_All() {
        int v;

        for (v = 0; v < n; v++) {
            nodeList[v].setState(INITIAL);
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce nodo inicial para Breadth First Search : ");
        String s = scan.next();
        bfs(getIndex(s));

        for (v = 0; v < n; v++) {

            if (nodeList[v].getState() == INITIAL) {
                bfs(v);
            }
        }
    }

    /**
     *
     * @author Isabela Espinoza
     * @param v
     */
    private void dfsRecurrent(int v) {
        System.out.println(v + " " + nodeList[v].getDisplayName());
        nodeList[v].setState(DVISITED);
        int t = ++time;
        nodeList[v].setDiscoveryTime(t);
        nodeList[v].setLowTime(t);

        for (int i = 0; i < n; i++) {
            if (isAdjacent(v, i) && nodeList[v].getDfsParent() != null && nodeList[v].getDfsParent().equals(nodeList[i])) {
                continue;
            }
            if (isAdjacent(v, i) && nodeList[i].getState() == DVISITED) {
                System.out.println("if(isAdjacent(v, i) && nodeList[i].getState() == DVISITED){");
                nodeList[v].setLowTime(Math.min(nodeList[v].getLowTime(), nodeList[i].getDiscoveryTime()));
            }
            if (isAdjacent(v, i) && nodeList[i].getState() == DINITIAL) {
                System.out.println("if(isAdjacent(v, i) && nodeList[i].getState() == DINITIAL){");
                nodeList[i].setParent(nodeList[v]);
                dfsRecurrent(i);
                nodeList[v].setLowTime(Math.min(nodeList[v].getLowTime(), nodeList[i].getLowTime()));
                if (nodeList[i].getLowTime() > nodeList[v].getDiscoveryTime()) {
                    System.out.println("if(nodeList[i].getLowTime() > nodeList[v].getDiscoveryTime()){");
                    bridges += nodeList[v].getDisplayName() + " - " + nodeList[i].getDisplayName() + ", \n";
                    System.out.println("Bridges " + bridges);
                }
            }
        }
        nodeList[v].setState(DFINISHED);
        nodeList[v].setFinishingTime(++time);
    }

    /**
     *
     * @author Isabela Espinoza
     */
    public void dfsTraversalRecurrent() {

        for (int v = 0; v < n; v++) {
            nodeList[v].setState(DINITIAL);
        }
        time = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce nodo inicial para Depth First Search : ");
        String s = scan.next();
        dfsRecurrent(getIndex(s));

        for (int v = 0; v < n; v++) {
            System.out.println("Node " + nodeList[v]);
            System.out.println("Discovery Time " + nodeList[v].getDiscoveryTime());
            System.out.println("Finishing Time " + nodeList[v].getFinishingTime());
        }
    }

    /**
     *
     * @author Isabela Espinoza
     */
    public void dfsTraversal_AllRecurrent() {

        for (int v = 0; v < n; v++) {
            nodeList[v].setState(DINITIAL);
        }
        time = 0;
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Introduce nodo inicial para Breadth First Search : ");
//        String s = scan.next();
//        dfsRecurrent(getIndex(s));
        dfsRecurrent(0);

        for (int v = 0; v < n; v++) {

            if (nodeList[v].getState() == DINITIAL) {
                dfsRecurrent(v);
            }
        }

        for (int v = 0; v < n; v++) {
            System.out.println("Node " + nodeList[v]);
            System.out.println("Discovery Time " + nodeList[v].getDiscoveryTime());
            System.out.println("Lower Time " + nodeList[v].getLowTime());
            System.out.println("Finishing Time " + nodeList[v].getFinishingTime());
        }
    }

    /**
     *
     * @author Isabela Espinoza
     * @return bridges
     */
    public String findBridges() {

        dfsTraversal_AllRecurrent();
        return bridges;
    }

    /**
     *
     * @author Isabela Espinoza
     * @return n
     */
    public int nodes() {
        return n;
    }

    /**
     *
     * @author Isabela Espinoza
     * @return e
     */
    public int edges() {
        return e;
    }

    /**
     *
     * @author Isabela Espinoza
     */
    public void display() {

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                if (adj[i][j]) {
                    System.out.println("1 ");
                } else {
                    System.out.println("0 ");
                }
                System.out.println();
            }
        }
    }

    /**
     *
     * @author Isabela Espinoza
     * @param s
     * @return i
     */
    private int getIndex(String s) {
        for (int i = 0; i < n; i++) {
            nodeList[i].getDisplayName();
            if (s.equals(nodeList[i].getDisplayName())) {
                return i;
            }
        }
        throw new RuntimeException("Nodo invalido");
    }

    /**
     *
     * @author Isabela Espinoza
     * @param name
     * @param id
     * @return
     */
    public Node insertNode(String name, int id) {
        Node node = new Node(name, id);
        nodeList[n++] = node;
        return node;
    }

    /**
     *
     * @author Isabela Espinoza
     * @param s1
     * @param s2
     * @return isAdjacent(getIndex(s1), getIndex(s2))
     */
    public boolean edgeExist(String s1, String s2) {
        return isAdjacent(getIndex(s1), getIndex(s2));
    }

    /**
     *
     * @author Isabela Espinoza
     * @param u
     * @param v
     * @return adj[u][v]
     */
    private boolean isAdjacent(int u, int v) {
        return adj[u][v];
    }

    /**
     *
     * @author Isabela Espinoza
     * @param n1
     * @param n2
     * @param years
     * @return edge
     */
    public Edge insertEdge(Node n1, Node n2, int years) {

        String s1 = n1.getDisplayName();
        String s2 = n2.getDisplayName();

        int u = getIndex(s1);
        int v = getIndex(s2);

        if (adj[u][v] == true) {
            System.out.println("Ya existe el eje");
        } else {
            adj[u][v] = true;
            adj[v][u] = true;
            e++;
        }
        Edge edge = new Edge(n1, n2, years);
        return edge;
    }

    /**
     *
     * @author Isabela Espinoza
     * @param s1
     * @param s2
     */
    public void deletetEdge(String s1, String s2) {
        int u = getIndex(s1);
        int v = getIndex(s2);

        if (adj[u][v] == false) {
            System.out.println("Eje no existe");
        } else {
            adj[u][v] = false;
            adj[v][u] = false;
            e--;
        }
    }

    /**
     *
     * @author Isabela Espinoza
     * @param s
     * @return deg
     */
    public int degree(String s) {
        int u = getIndex(s);
        int deg = 0;

        for (int v = 0; v < n; v++) {
            if (adj[u][v]) {
                deg++;
            }
        }
        return deg;
    }

}
