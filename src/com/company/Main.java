package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // write your code here

        Buffer1<Integer> b1 = new Buffer1(5);

        Worker w1 = new Worker(b1);
        Worker w2 = new Worker(b1);

        w1.start();
        w2.start();

    }
}
