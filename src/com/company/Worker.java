package com.company;

public class Worker extends Thread {
    Buffer1<Integer> b1;

    Worker(Buffer1<Integer> buffer){
        this.b1 = buffer;
    }

    public void run(){
        while (true){
            try {
                b1.put(10);
                b1.put(11);
                b1.put(12);
                b1.put(13);
                b1.put(14);
                b1.take();
                b1.take();
                b1.take();
                b1.take();
                b1.take();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }


}

