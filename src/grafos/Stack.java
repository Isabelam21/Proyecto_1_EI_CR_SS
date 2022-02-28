/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

/**
 * Clase Stack, clase pilas 
 * @author Isabela Espinoza
 */
public class Stack {

    //Atributos
    private Node top;
    private Node base;
    private int size;

    /**
     * Construir
     * @author Isabela Espinoza
     */
    public Stack() {
        this.top = this.base = null;
        this.size = 0;
    }


    /**
     * Si esta vacia
     * @author Isabela Espinoza
     * @return true or false
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Para vaciar pila
     * @author Isabela Espinoza
     */
    public void empty() {
        this.top = this.base = null;
        this.size = 0;
    }

    /**
     * Apilar
     * @author Isabela Espinoza
     * @param nuevo 
     */
    public void push(Node nuevo) {
        if (this.isEmpty()) {
            top = base = nuevo;
        } else {
            nuevo.setNext(top);
            top = nuevo;
        }
        size++;
    }

    /**
     * Desapilar
     * @author Isabela Espinoza
     * @return el nodo borrado
     */
    public Node pop() {
        Node node = null;
        if (this.isEmpty()) {
            System.out.println("La pila esta vacia.");
        } else if (size == 1) {
            node = new Node();
            System.out.println("top.getValue( "+top.getValue());
            node.setValue(top.getValue());
            this.empty();
        } else {
            node = top;
            top = top.getNext();
            size--;
        }
        return node;
    }

    //Imprimir
//    public String print(String printStack){
//    if (!this.isEmpty()) {
//    Nodo actual = top;
//    stack.pop();
//    printStack += actual.getElement() + ",";
//    printStack = print(printStack);
//    stack.push(actual);
//    }
//    return printStack;
//    }
    
    /**
     * Imprimir
     * @author Isabela Espinoza
     * @param printStack
     * @param contador
     * @return imprime la pila
     */
    public String print(String printStack, int contador) {
        if (!this.isEmpty()) {
            Node aux = top;
            this.pop();
            printStack += "Nodo" + contador + ": " + aux.getElement() + "\n";
            contador++;
            printStack = print(printStack, contador);
            this.push(aux);
        }
        return printStack;
    }

}
