package com.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /* Scanner sc = new Scanner(System.in);
        String filepath = "archivo.txt";
        try {
            RandomAccessFile raf = new RandomAccessFile(filepath, "rw");
            System.out.println("Enter position: ");
            int position = sc.nextInt();
            raf.seek(position - 1);
            char cha = (char) raf.readByte();
            System.out.println("Character at position " + position + " is: " + cha);
            raf.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("I/O error");
            e.printStackTrace();
        }
        sc.close(); */

        /*  Scanner sc = new Scanner(System.in);
        String filepath = "archivo.txt";
        try {
            RandomAccessFile raf = new RandomAccessFile(filepath, "rw");
            System.out.println("Enter position: ");
            raf.seek(sc.nextInt() - 1);
            String text = raf.readLine();
            System.out.println(text);
            raf.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("I/O error");
            e.printStackTrace();
        }
        sc.close(); */

        Scanner sc = new Scanner(System.in);
        String filepath = "archivo.txt";

        try {
            RandomAccessFile raf = new RandomAccessFile(filepath, "rw");
            System.out.println("Enter position: ");
            long pos = sc.nextLong();
            raf.seek(pos - 1);
            System.out.println("Enter quantity of characters: ");
            int quantity = sc.nextInt();
            byte[] bytes = new byte[(int) quantity];
            raf.read(bytes, 0, quantity);
            String text = new String(bytes);
            System.out.println("Text:");
            System.out.println(text);
            raf.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("I/O error");
            e.printStackTrace();
        }
        sc.close();
    }
}
