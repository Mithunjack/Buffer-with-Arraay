package com.company;
import java.util.Arrays;
public class Buffer1<T>{
    int start;
    int end;
    T[] array;
    int[] newArray;
    int arrayAvailableSpace;

    Buffer1(int arraySize){
        this.start= 0;
        this.end = 0;
        this.array =  (T[]) new Object[arraySize];
        this.newArray = new int[arraySize];
        this.arrayAvailableSpace = arraySize;
    }

    public synchronized boolean isEmpty(){
        if (arrayAvailableSpace==array.length){
            return true;
        }
        else return false;
    }

    public synchronized T take() throws InterruptedException{
        while (isEmpty()){
            wait();
        }
        T removedValue = array[start];
        array[start]= null;
        if (start==array.length-1){
            start=0;
        }
        else {
            start++;
        }
        arrayAvailableSpace++;
        if (arrayAvailableSpace==1){
            notifyAll();
        }
        System.out.println(removedValue);
        System.out.println(Arrays.toString(array));
        return removedValue;
    }

    public synchronized void put(T elem) throws InterruptedException{
        while (arrayAvailableSpace==0){
            wait();
        }
        array[end]=elem;
        if(end==array.length-1){
            end=0;
        }
        else {
            end++;
        }
        arrayAvailableSpace--;
        if (arrayAvailableSpace==array.length-1){
            notifyAll();
        }
        System.out.println(Arrays.toString(array));

    }

}

