

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jmach
 */
public class ArrayStack<T> {
    private T[] data = null;
    private int top = 0;
    Calculator cal = new Calculator();
    
    public ArrayStack() {
          data = (T[])new Object[10];
    }

    public void push(T input){
        data[top++] = input;
    }
    
    public T pop(T[] data){
        return data[--top];
    }
    
    public boolean isEmpty(T[] data){
        return top == 0;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }   
    
    @Override
    public String toString() {

        String stack = "";

        for (int i = 0; i < top; i++) {
            stack += data[i] + " ";
        }
        return stack;
    }

    public T[] getData() {
        return data;
    }
}


