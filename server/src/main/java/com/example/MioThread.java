package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class MioThread extends Thread {
    Socket s;
    ArrayList<MioThread> lista;
    String username;
    String messaggio;
    String presenza;
    BufferedReader in;
    DataOutputStream out;

    public MioThread(Socket s, ArrayList<MioThread> lista) {

        this.s = s;
        this.lista = lista;
        username = "";

    }

    public String getUsername() {
        return username;
    }

    public void run() {

        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new DataOutputStream(s.getOutputStream());
            boolean connessione = true;

            do {
                String linea = in.readLine();
                String[] msg = linea.split("@", 2);

                switch (msg[0]) {
                    case "USERNAME":
                        boolean exists = false;
                        for (int i = 0; i < lista.size(); i++) {
                            if (lista.get(i).getUsername().equals(msg[1])) {
                                exists = true;
                            }
                        }
                        if (exists == true) {
                            out.writeBytes("NUs" + "\n");
                        } else {
                            out.writeBytes("SUs" + "\n");
                            username = msg[1];
                            System.out.println(username);
                        }
                        break;
                    case "EXIT":

                        System.out.println(username + " si è disconnesso");
                        for (int i = 0; i < lista.size(); i++) {
                             if(lista.get(i).equals(this) == false)
                            lista.get(i).invia("EXIT@" + this.getUsername());
                        }
                        rimuovi(this);
                        connessione = false;
                        s.close();

                        break;
                    case "LIST":
                        out.writeBytes("L" + '\n');
                        for (int i = 0; i < lista.size(); i++) {
                            out.writeBytes(lista.get(i).getUsername() + "\n");
                        }
                        out.writeBytes("L" + '\n');
                        break;
                    case "PRIVATE":

                        String[] privato = msg[1].split("@", 2);
                        boolean esiste = false;
                        for (int i = 0; i < lista.size(); i++) {
                            if (lista.get(i).getUsername().equals(privato[0])) {
                                lista.get(i).invia("PRIVATE@" + this.getUsername() + ": " + privato[1]);
                                esiste = true;
                            }
                        }
                        if (esiste == false) {
                            out.writeBytes("NPr" + "\n");
                        }
                        break;

                        case "ALL":
                       
                       
                        for (int i = 0; i < lista.size(); i++) {
                            if(lista.get(i).equals(this) == false)
                            lista.get(i).invia("ALL@" + this.getUsername() + ": " + msg[1]);
                        }
                        
                        break;

                }

            } while (connessione);

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public void invia(String msg) throws IOException {
        this.getOut().writeBytes(msg + "\n");
    }

    public void rimuovi(MioThread t) {
        lista.remove(t);
    }

    public DataOutputStream getOut() {
        return out;
    }

}