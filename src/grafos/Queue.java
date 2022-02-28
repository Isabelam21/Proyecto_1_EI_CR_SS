/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

/**
 * Clase Queue, clase colas
 * @author isabela Espinoza
 */
public class Queue {

    //Atributos
    private Node head;
    private Node tail;
    private int size;

    /**
     * construir
     * @author Isabela Espinoza
     */
    public Queue() {
        this.head = this.tail = null;
        this.size = 0;
    }

    /**
     * Si esta vacia
     * @author Isabela Espinoza
     * @return true or false
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Para vaciar cola
     * @author Isabela Espinoza
     */
    public void empty() {
        this.head = this.tail = null;
        this.size = 0;
    }


    /**
     * Encolar
     * @author Isabela Espinoza
     * @param nuevo 
     */
    public void queue(Node nuevo) {
        System.out.println("encolando "+nuevo.getValue());
        if (this.isEmpty()) {
            head = tail = nuevo;
        } else {
            tail.setNext(nuevo);
            tail = nuevo;
        }
        size++;
    }

    /**
     * Desencolar
     * @author Isabela Espinoza
     * @return 
     */
    public Node dequeue() {
        System.out.println("size es "+size+" head value "+head.getValue());
        Node node = null;
        if (this.isEmpty()) {
            System.out.println("La cola esta vacia.");
        } else if (size == 1) {
            node = new Node();
            System.out.println("head.getValue( "+head.getValue());
            node.setValue(head.getValue());
            this.empty();
        } else {
            node = head;
            head = head.getNext();
            size--;
        }
        return node;
    }


    /**
     * Imprimir
     * @author Isabela Espinoza
     * @return imprime
     */
    public String print() {
        if (!this.isEmpty()) {
            String printQueue = "";
            for (int i = 0; i < size; i++) {
                Node actual = head;
                dequeue();
                printQueue += actual.getElement() + ",";
                queue(actual);
            }
            return printQueue;
        }
        return null;
    }
}
