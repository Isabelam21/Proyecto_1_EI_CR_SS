package grafos;

import grafos.Edge;
import grafos.GraphDraw;
import grafos.Node;
import grafos.UndirectedGraph;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * Clase principal del proyecto 
 * Con la interfaz gráfica
 * @author Isabela Espinoza 
 * 
 */
public class mainFrame extends javax.swing.JFrame {

    /**
     * Creates new form mainFrame
     */
    
    private GraphDraw draw;
    private UndirectedGraph g;
    private String fileRoute;
    private int idMax = 0;

    public static int nodeCount = 0;
    public static int pairCount = 0;
    public static int xPosition = 100;
    public static int yPosition = 200;

    private static List<Node> nodesList = new ArrayList<>();

    private boolean graphLoaded = false;
    
    
    /*
    * Aqui se llama a la interfaz gráfica para la vista del usuario
    * @author Isabela Espinoza
    */
    public mainFrame() {
        //Se crea un objeto de la clase GraphDraw
        draw = new GraphDraw();
        this.setPreferredSize(new Dimension(1200, 600));
        draw.setPreferredSize(new Dimension(2800, 800));
        //Se crea un objeto de la clase UndirectedGraph
        g = new UndirectedGraph();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        initComponents();
    }
    
    
    /*
    * Funcion main, se hace visible el JFrame Form 
    * @author Isabela Espinoza
    */
    public static void main(String[] args) {
        new mainFrame().setVisible(true);
    }
    
    
    /*
    * Funcion para insertar aristas del grafo, la union de los nodos con sus respectivos años de amistad
    * Se obtienen los nodos de la lista de nodos de la clase GraphDraw
    * Se inserta la relación o arista del grafo con el metodo de la clase UndirectedGraph 
    * (Se inserta en la matriz de adyacencia del grafo)
    * Por último se le añade la arista a la clase GraphDraw para que se dibuje
    * @author Isabela Espinoza
    * @param firsNodeId, el primer usuario para la conexión
    * @param secondNodeId, el segundo usuario
    * @param years, años de amistad
    * @param g, el objeto de clase UndirectedGraph para añadir en la matriz de adyacencia
    * @param draw, el objeto de clase GraphDraw para añadir en el grafico
    */
    public static void insertEdge(int firstNodeId, int secondNodeId, int years, UndirectedGraph g, GraphDraw draw) {
        Node n1 = draw.getNode(firstNodeId);
        Node n2 = draw.getNode(secondNodeId);
        Edge edge = g.insertEdge(n1, n2, years);
        draw.addEdge(edge);
    }
    
    /*
    * Funcion para leer el archivo txt y cargar el grafo
    * @author Isabela Espinoza
    *
    */
    private void loadFile() {
        // Se crea un objeto de la clase JFileChooser para escoger el archivo
        JFileChooser filePath = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        // se invoca la función showsOpenDialog para mostrar el diálogo de guardar
        int r = filePath.showOpenDialog(null);

        // Cuando el usuario selecciona un archivo
        if (r == JFileChooser.APPROVE_OPTION) {

            fileRoute = filePath.getSelectedFile().getAbsolutePath();
            // establecer la etiqueta en la ruta del archivo seleccionado

            System.out.println(fileRoute);
            
            //Validación para la seleccion de un archivo txt
            if (!fileRoute.endsWith("txt")) {
                JOptionPane.showMessageDialog(null, "formato de archivo no valido, intente de nuevo");
                return;
            }
            
            // Grafo cargado
            graphLoaded = true;
            File file = new File(fileRoute);

            // Note:  Double backquote is to avoid compiler
            // interpret words like \test as \t (ie. as a escape sequence)
            
            // Se crea un objeto de la clase BufferedReader para leer el texto
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Se declara una variable de tipo string 
            String st;
            try {
                boolean usuario = false;
                boolean relaciones = false;
                // La condición es válida hasta qu haya un carácter en una cadena
                while ((st = br.readLine()) != null) {

                    // Print el string
                    System.out.println(st);
                    // Validación para leer los usuarios
                    if (st.equalsIgnoreCase("Usuarios")) {
                        usuario = true;
                    }
                    if (usuario == true && relaciones == false) {
                        String[] us = st.split(",");
                        if (us.length > 1) {
                            // Se insertan los nodos/usuarios
                            insertNode(us[1].trim(), Integer.parseInt(us[0].trim()), g, draw);
                        }

                    }
                    // Validación para leer las relaciones de los usuarios
                    if (st.equalsIgnoreCase("Relaciones")) {
                        relaciones = true;
                    }
                    if (usuario == true && relaciones == true) {
                        String[] ed = st.split(",");
                        if (ed.length > 1) {
                            // se insertan las aristas del grafo, las relaciones
                            insertEdge(Integer.parseInt(ed[0].trim()), Integer.parseInt(ed[1].trim()), Integer.parseInt(ed[2].trim()), g, draw);
                        }
                    }
                }
                //Validación, si no existen las palabras usuario/realcion no se puede leer
                if (usuario == false || relaciones == false) {
                    JOptionPane.showMessageDialog(null, "archivo mal formado, no se puede leer el contenido");
                    System.out.println("archivo mal formado, no se puede leer el contenido");
                }
            } catch (IOException ex) {
                Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        jPanel1.repaint();

    }
    
    /*
    * Funcion para insertar los nodos del grafo, inserta los usuarios
    * @author Isabela Espinoza
    * @param nodeName, nombre del usuario
    * @param id, numero de id del usuario
    * @param g, el objeto de clase UndirectedGraph para añadir en la matriz de adyacencia
    * @param draw, el objeto de clase GraphDraw para añadir en el grafico
    */
    public static void insertNode(String nodeName, int id, UndirectedGraph g, GraphDraw draw) {
        //Se inserta el nodo en la clase de  la matriz de adyacencia del nodo
        Node node = g.insertNode(nodeName, id);
        //Se suma el contador de la cantidad de nodos
        nodeCount++;
        //Posicion del nodo
        if (nodeCount % 2 == 0) {
            node.setPosition(new Node.Position(xPosition + 150 * pairCount, yPosition - 100));
            pairCount++;
        } else {
            node.setPosition(new Node.Position(xPosition + 150 * pairCount, yPosition + 100));
        }
        //Se añade el nodo a la clase del grafico
        draw.addNode(node);
    }

    
    /*
    * Función para saber las conexiones del grafo
    * La funcion llama al metodo .isConnected() de la clase UndirectedGraph
    * y la imprime en un MessageDialog
    * @author Isabela Espinoza
    */
    private void isConnected() {
        String isConnected = g.isConnected();
        JOptionPane.showMessageDialog(null, isConnected);
    }
    
    /*
    * Función para mostrar cantidad de islas mediante recorrido de profundidad DFS
    * La funcion llama al metodo .isConnectedDFS() de la clase UndirectedGraph
    * y la imprime en un MessageDialog
    * @author Isabela Espinoza
    */
    private void isConnectedDFS() {
        String isConnected = g.isConnectedDFS();
        JOptionPane.showMessageDialog(null, isConnected);
    }
    
    /*
    * Funcíon para crear un nuevo usuario y agregarlo al txt
    * @author Isabela Espinoza
    */
    private void newUser() {
        String fileContent = "";
        //Tomamos el nombre del nodo/usuario dado
        String nodeName = jTextField1.getText();

        File file = new File(fileRoute);

        // Note:  Double backquote is to avoid compiler
        // interpret words like \test as \t (ie. as a escape sequence)
        
        // Se crea un objeto de la clase BufferedReader para leer el texto
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Se declara una variable de tipo string
        String st;
        try {
            boolean usuario = false;
            boolean relaciones = false;
            // La condición es válida hasta qu haya un carácter en una cadena
            while ((st = br.readLine()) != null) {

                // Print el string
                System.out.println(st);

                //Validación para leer usuarios
                if (st.equalsIgnoreCase("Usuarios")) {
                    usuario = true;
                }
                if (usuario == true && relaciones == false) {
                    String[] us = st.split(",");
                    if (us.length > 1) {
                        if (Integer.parseInt(us[0].trim()) > idMax) {
                            idMax = Integer.parseInt(us[0].trim());
                        }

                    }

                }
                if (relaciones == false && st.equalsIgnoreCase("Relaciones")) {
                    relaciones = true;
                    int id = idMax + 1;
                    if (!nodeName.startsWith("@")) {
                        nodeName = "@" + nodeName;
                    }
                    //Se escribe el el archivo
                    String n = "" + id + ", " + nodeName + "\nRelaciones\n";
                    fileContent += n;
                } else {
                    fileContent += st + "\n";
                }

            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileRoute));
            System.out.println();
            System.out.println("***********FileContent");
            System.out.println(fileContent);
            writer.write(fileContent);
            writer.close();

            jTextField1.setText("");
            loadFileRoute(fileRoute);

        } catch (IOException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    * Funcion para cargar el grafo (lee el archivo) despues de alguna modificación
    * Esta función fue hecha para volver a cargar el archivo sin preguntarle al usuario ruta
    * Es decir, para volver a cargar grafo después de agregar/eliminar/conectar usuarios
    * Trata basicamente de la misma lógica de loadFile() pero sin preguntar cual txt
    * @author Isabela Espinoza
    */
    private void loadFileRoute(String fileRoute) {

        nodeCount = 0;
        pairCount = 0;
        draw = new GraphDraw();
        g = new UndirectedGraph();    
        draw.setPreferredSize(new Dimension(2800, 800));
        jScrollPane1.setViewportView(draw);

        // set the label to the path of the selected file
        System.out.println(fileRoute);

        File file = new File(
                fileRoute);

        // Note:  Double backquote is to avoid compiler
        // interpret words like \test as \t (ie. as a escape sequence)
        
        // Se crea un objeto de la clase BufferedReader para leer el texto
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Se declara una variable de tipo string
        String st;
        try {
            boolean usuario = false;
            boolean relaciones = false;
            // La condición es válida hasta qu haya un carácter en una cadena
            while ((st = br.readLine()) != null) {

                // Print string
                System.out.println(st);
                if (st.equalsIgnoreCase("Usuarios")) {
                    System.out.println(" if (st.equalsIgnoreCase(\"Usuarios\")");
                    usuario = true;
                }
                if (usuario == true && relaciones == false) {
                    System.out.println(" if (usuario == true && relaciones == false)");
                    String[] us = st.split(",");
                    if (us.length > 1) {
                        insertNode(us[1].trim(), Integer.parseInt(us[0].trim()), g, draw);
                    }

                }
                if (st.equalsIgnoreCase("Relaciones")) {
                    System.out.println(" if (st.equalsIgnoreCase(\"Relaciones\")");
                    relaciones = true;
                }
                if (usuario == true && relaciones == true) {
                    System.out.println(" if (usuario == true && relaciones == true)");
                    String[] ed = st.split(",");
                    if (ed.length > 1) {
                        insertEdge(Integer.parseInt(ed[0].trim()), Integer.parseInt(ed[1].trim()), Integer.parseInt(ed[2].trim()), g, draw);
                    }
                }
            }
            if (usuario == false || relaciones == false) {
                JOptionPane.showMessageDialog(null, "archivo mal formado, no se puede leer el contenido");
                System.out.println("archivo mal formado, no se puede leer el contenido");
            }
        } catch (IOException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /*
    * Función para eliminar un usuario del grafo
    * @author Isabela Espinoza
    */
    private void deleteUser() {
        String fileContent = "";
        String nodeName = jTextField1.getText();
        if (!nodeName.startsWith("@")) {
            nodeName = "@" + nodeName;
        }

        File file = new File(
                fileRoute);

        // Note:  Double backquote is to avoid compiler
        // interpret words like \test as \t (ie. as a escape sequence)
        
        // Se crea un objeto de la clase BufferedReader para leer el texto
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Se declara una variable de tipo string
        String st;
        try {
            boolean usuario = false;
            boolean relaciones = false;
            String idEliminar = "";
            // La condición es válida hasta qu haya un carácter en una cadena
            while ((st = br.readLine()) != null) {

                // Print string
                System.out.println(st);

                if (st.equalsIgnoreCase("Usuarios")) {
                    usuario = true;
                    fileContent += st + "\n";
                }
                if (usuario == true && relaciones == false) {
                    String[] us = st.split(",");
                    if (us.length > 1) {

                        if (!us[1].trim().equals(nodeName)) {
                            fileContent += st + "\n";
                        } else {
                            idEliminar = us[0].trim();
                            System.out.println("ID__ELIMINAR " + idEliminar);
                        }

                    }

                }
                if (usuario == true && relaciones == true) {
                    String[] us = st.split(",");
                    if (!us[0].trim().equals(idEliminar) && !us[1].trim().equals(idEliminar)) {
                        fileContent += st + "\n";
                    }
                }
                if (relaciones == false && st.equalsIgnoreCase("Relaciones")) {
                    relaciones = true;
                    fileContent += st + "\n";
                }

            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileRoute));
            System.out.println();
            System.out.println("***********FileContent");
            System.out.println(fileContent);
            writer.write(fileContent);
            writer.close();

            loadFileRoute(fileRoute);

        } catch (IOException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        jTextField1.setText("");
    }
    
    /*
    * Funcion para añadir conexiones/aristas/relaciones entre dos nodos/usuarios
    * @uthor Isabela Espiinoza
    */
    public void addConnection() {
        String fileContent = "";
        String initNode = jTextField2.getText();
        String finishNode = jTextField3.getText();
        boolean initNodeExist = false;
        boolean finishNodeExist = false;
        String initNodeId = "";
        String finishNodeId = "";

        String years = String.valueOf(jSpinner1.getValue());

        if (!initNode.startsWith("@")) {
            initNode = "@" + initNode;
        }

        if (!finishNode.startsWith("@")) {
            finishNode = "@" + finishNode;
        }

        File file = new File(
                fileRoute);

        // Note:  Double backquote is to avoid compiler
        // interpret words like \test as \t (ie. as a escape sequence)
        
        // Se crea un objeto de la clase BufferedReader para leer el texto
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Se declara una variable de tipo string
        String st;
        try {
            boolean usuario = false;
            boolean relaciones = false;
            String idEliminar = "";
            // La condición es válida hasta qu haya un carácter en una cadena
            while ((st = br.readLine()) != null) {

                // Print string
                System.out.println(st);

                if (st.equalsIgnoreCase("Usuarios")) {
                    usuario = true;
                }
                if (usuario == true && relaciones == false) {
                    String[] us = st.split(",");
                    if (us.length > 1) {

                        if (us[1].trim().equals(initNode)) {
                            initNodeId = us[0].trim();
                            initNodeExist = true;
                        }
                        if (us[1].trim().equals(finishNode)) {
                            finishNodeId = us[0].trim();
                            finishNodeExist = true;
                        }

                    }
                    fileContent += st + "\n";
                }
                if (usuario == true && relaciones == true) {
                    fileContent += st + "\n";
                }
                if (relaciones == false && st.equalsIgnoreCase("Relaciones")) {
                    relaciones = true;
                }

            }

            if (initNodeExist && finishNodeExist) {
                fileContent += initNodeId + ", " + finishNodeId + ", " + years + "\n";
            } else {
                System.out.println("Uno de los nodos no existe");
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileRoute));
            System.out.println();
            System.out.println("***********FileContent");
            System.out.println(fileContent);
            writer.write(fileContent);
            writer.close();

            loadFileRoute(fileRoute);

        } catch (IOException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        jTextField2.setText("");
        jTextField3.setText("");
    }
    
    
    /*
    * Función para encontrar los puentes del grafo
    * @author Isabela Espinoza
    */
    public void findBridges() {

        String bridges = g.findBridges();
        JTextArea jta = new JTextArea(15, 30);
        jta.setText(bridges);
        jta.setEditable(false);
        JScrollPane jsp = new JScrollPane(jta);
        JOptionPane.showMessageDialog(null, jsp, "puentes", 1);

    }
    
    
    

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jButton7 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Cargar Grafo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 11, 107, -1));

        jButton2.setText("Islas BFS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 52, 107, -1));

        jButton3.setText("Islas DFS");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 93, 107, -1));

        jButton4.setText("Nuevo Usuario");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 130, -1));

        jButton5.setText("Nueva Conexion");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 140, -1));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(1800, 800));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(draw);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 930, 470));
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 120, -1));

        jButton6.setText("Puentes");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 134, 107, -1));

        jSeparator1.setToolTipText("");
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 175, 107, 14));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 299, 107, 10));

        jButton7.setText("Eliminar Usuario");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 130, -1));
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 353, 107, -1));
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 398, 107, -1));
        jPanel1.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 429, 51, -1));

        jLabel1.setText("Conectar desde:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 313, 107, -1));

        jLabel2.setText("Usuario A");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 333, 107, -1));

        jLabel3.setText("--> Hasta: usuario B");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 120, -1));

        jLabel4.setText("Usuarios");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 107, -1));

        jLabel5.setText("años");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 432, 31, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    loadFile();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (graphLoaded) {

                    isConnected();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe cargar el archivo del grafo para continuar");
                }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (graphLoaded) {

                    isConnectedDFS();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe cargar el archivo del grafo para continuar");
                }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (graphLoaded) {

                    findBridges();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe cargar el archivo del grafo para continuar");
                }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (graphLoaded) {

                    newUser();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe cargar el archivo del grafo para continuar");
                }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (graphLoaded) {

                    deleteUser();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe cargar el archivo del grafo para continuar");
                }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (graphLoaded) {

                    addConnection();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe cargar el archivo del grafo para continuar");
                }
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
