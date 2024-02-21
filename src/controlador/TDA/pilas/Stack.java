/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.pilas;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;


/**
 *
 * @author FA506ICB-HN114W
 */
class Stack<E> extends DynamicList<E> {
    private Integer tope;

    public Stack(Integer tope) {
        this.tope = tope;
    }
    
    
    
    public Boolean isFull(){
        return getLenght().intValue()>= tope.intValue();
    }
    
    public void push(E info) throws EmptyException, FulStackException {
        if (isFull()) {
            throw new FulStackException("Error. Stack vacia");
        } else {
            add(info,0);
        }
    }
    
    public E pop() throws EmptyException{
        E info = extractFirst();
        return info;
    }
}
