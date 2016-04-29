/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Navin
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        try {
            Socket client = new Socket("192.168.1.252", 9000);
            Scanner reader = new Scanner(client.getInputStream());
            String line = "";
            while (true) {
                doLogin(client);
                startChat(client);
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private static void sendMessage(Socket client, String message) throws IOException {
        PrintWriter writer = new PrintWriter(client.getOutputStream());
        writer.write(message+"\r\n" );

        writer.flush();

    }

    private static void doLogin(Socket client) throws IOException {
        BufferedReader reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
        Scanner input=new Scanner(System.in);
        boolean login=false;
        while(!login){
            System.out.println(reader.readLine());
            String userName=input.nextLine();
            sendMessage(client, userName);
           System.out.println(reader.readLine());
            String password=input.nextLine();
            sendMessage(client, password);
           String result=reader.readLine();
            System.out.println(result);
           if(!result.contains("Invalid")){
               System.out.println(result);
               login=true;
           }
        }
        
    }
    private static void startChat(Socket client) throws IOException{
    BufferedReader reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
        Scanner input=new Scanner(System.in);
       
        while(true){
            System.out.println(reader.readLine());
            sendMessage(client, input.nextLine());
           System.out.println(reader.readLine());
            String password=input.nextLine();
            sendMessage(client, password);
           String result=reader.readLine();
            System.out.println(result);
           if(!result.contains("Invalid")){
               System.out.println(result);
              
           }
        }
        
    }
}
