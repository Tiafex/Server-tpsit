package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MioThread extends Thread {
    Socket s;
    Username u;
    public MioThread(Socket s, Username u) {
        this.s = s;
        this.u= u;
    }

    public void run(){
       
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                String username;
                String messaggio;
                String presenza;
                
                do{
                    username = in.readLine();
                    for(int i=0; i<username.length(); i++ )
                    {
                        if (username.charAt(i)=='@') {
                            
                            break;
                        }

                    }

                    presenza = u.Aggiungi(username);
                    if (presenza.equals("Nus")) {
                        messaggio = "no";
                        out.writeBytes(messaggio + "\n");
                    }
                    else
                    {
                      messaggio = "si";
                      out.writeBytes(messaggio + "\n");  
                      do{

                      }while(!messaggio.equals("EXIT"));
                    }
                }while(!messaggio.equals("si"));






            } catch (IOException e) {
                

                e.printStackTrace();
            }
    }
}
