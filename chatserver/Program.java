/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.chatserver;

import com.nabin.chatserver.handler.ClientListener;
import com.nabin.chatserver.handler.RequestHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
            RequestHandler handler=new RequestHandler();
            ServerSocket server = new ServerSocket(9000);
            System.out.println("Server is running at 9000");

            while (true) {
                Socket client = server.accept();
                System.out.println("Connetion Request from:" + client.getInetAddress().getHostAddress());
                ClientListener listener=new ClientListener(client,handler);
                listener.start();
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

}
