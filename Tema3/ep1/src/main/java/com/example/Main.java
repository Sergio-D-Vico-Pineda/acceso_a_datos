package com.example;

import java.lang.Thread;

public class Main {
    public static void main(String[] args) {
        Database.crearConexion();

        Thread TransA = new Thread(new TransactionA());
        Thread TransB = new Thread(new TransactionB());
        Thread TransC = new Thread(new TransactionC());

        TransA.start();
        TransB.start();
        TransC.start();

        try {
            TransA.join();
            TransB.join();
            TransC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
