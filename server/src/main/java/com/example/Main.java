package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Server Avviato!");
        ArrayList<MioThread> lista= new ArrayList();


        ServerSocket ss = new ServerSocket(3000);
            do{
                Socket s = ss.accept();
                
                System.out.println("chat avviata");
                MioThread t = new MioThread(s,lista);
                lista.add(t);
                t.start();
            }while(true);
        
        
    }
}