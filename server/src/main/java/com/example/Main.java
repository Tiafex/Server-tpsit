package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Server Avviato!");



        ServerSocket ss = new ServerSocket(3000);
            do{
                Socket s = ss.accept();
                Username u=new Username();
                System.out.println("chat avviata");
                MioThread t = new MioThread(s,u);
                t.start();
            }while(true);
        
        
    }
}