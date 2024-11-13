package com.example;

import java.util.ArrayList;

public class Username {
    ArrayList array=new ArrayList<String>();


    public Username()
    {
        this.array= new ArrayList<String>();
    }

    public String Aggiungi(String Username)
    {
        if (array.contains(Username)) {
            return "Nus";
        }
        
        
            array.add(Username);
            return "Sus";
    }







}
