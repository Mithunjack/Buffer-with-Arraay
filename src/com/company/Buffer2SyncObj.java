package com.company;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public class Buffer2SyncObj<T>{
    Object read;
    Object write;
    T[] array;
    int availableSpace;
    int start,end;


    Buffer2SyncObj(int arraySize){
        this.read = new Object();
        this.write = new Object();
        this.array = (T[]) new Object[arraySize];
        this.availableSpace = arraySize;
        this.start=0;
        this.end=0;
    }
    public synchronized boolean isEmpty(){
        if (availableSpace==array.length){
            return true;
        }
        else return false;
    }
    public T read() throws InterruptedException{
        synchronized (read){
            while (isEmpty()){
                read.wait();
            }
            synchronized (write){
                T readValue = array[start];
                read.notify();
                System.out.println(readValue);
                System.out.println(Arrays.toString(array));
                return readValue;
            }
        }
    }

    public T take(long timeout) throws InterruptedException, TimeoutException {
        synchronized (read){
            while (isEmpty()){
                long time = System.nanoTime();
                read.wait(timeout);
                long totalTime = (System.nanoTime() - time ) / 1000000;
                if (totalTime>=timeout){
                    throw new TimeoutException();
                }
            }
            synchronized (write){
                T removedValue = array[start];
                array[start]= null;
                if (start==array.length-1){
                    start=0;
                }
                else {
                    start++;
                }
                availableSpace++;
                if (availableSpace==1){
                    write.notify();
                }
                System.out.println(removedValue);
                System.out.println(Arrays.toString(array));
                return removedValue;
            }

        }
    }

    public void overwrite(T content) throws InterruptedException{
        synchronized (write){
            synchronized (read){
                array[end]=content;
                if(end==array.length-1){
                    end=0;
                }
                else {
                    end++;
                }
                availableSpace--;
                if (availableSpace==array.length-1){
                    read.notify();
                }
                System.out.println(Arrays.toString(array));
            }
        }
    }
    public void put(T content) throws InterruptedException{
        synchronized (write){
            while (availableSpace==0){
                write.wait();
            }
            synchronized (read){
                array[end]=content;
                if(end==array.length-1){
                    end=0;
                }
                else {
                    end++;
                }
                availableSpace--;
                if (availableSpace==array.length-1){
                    read.notify();
                }
                System.out.println(Arrays.toString(array));
            }
        }
    }
    public boolean tryPut(T content) throws InterruptedException{
        synchronized (write){
            while (availableSpace==0){
                return false;
            }
            synchronized (read){
                array[end]=content;
                if(end==array.length-1){
                    end=0;
                }
                else {
                    end++;
                }
                availableSpace--;
                if (availableSpace==array.length-1){
                    read.notify();
                }
                System.out.println(Arrays.toString(array));
                return true;
            }
        }
    }

}
