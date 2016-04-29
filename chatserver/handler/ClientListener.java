/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.chatserver.handler;

import com.nabin.chatserver.dao.UserDAO;
import com.nabin.chatserver.dao.impl.UserDAOImpl;
import com.nabin.chatserver.entity.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Navin
 */
public class ClientListener extends Thread {

    private Socket client;
    private RequestHandler handler;
    private UserDAO userDAO = new UserDAOImpl();

    public ClientListener(Socket client, RequestHandler handler) {
        this.client = client;
        this.handler = handler;
    }

    private void sendMessage(Socket client, String message) throws IOException {
        PrintWriter writer = new PrintWriter(client.getOutputStream());
        writer.write(message+"\r\n");

        writer.flush();

    }

    private void broadcastMessage(String message) throws IOException {
        for (User u : handler.getUser()) {
            sendMessage(u.getSocket(), message);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!checkLogin()) {
                    break;
                }
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = "";
            User loggedin = handler.getUserBySocket(client);
            while (!(line = reader.readLine()).equalsIgnoreCase("exit")) {

                if (line.equalsIgnoreCase("/list")) {
                    StringBuilder builder = new StringBuilder();
                    for (User u : handler.getUser()) {
                        builder.append(u.getUserName() + "\r\n");
                    }
                    builder.append("total:" + handler.getUser().size());
                    sendMessage(client, builder.toString());
                } else if (line.startsWith("/pm")) {
                    String[] tokens = line.split(";");
                    if (tokens.length >= 3) {
                        User u = handler.getByUserName(tokens[1]);
                        if (u != null) {
                            sendMessage(u.getSocket(), loggedin.getUserName() + "says:>" + tokens[2]);
                        }
                    }
                } else {

                    broadcastMessage(loggedin.getUserName() + "says:>" + line);
                }
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private boolean checkLogin() throws IOException {
        sendMessage(client, " Enter User Name:");

        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String userName = reader.readLine();
        sendMessage(client, "Enter Your Password:");
        String password = reader.readLine();
        User user = userDAO.login(userName, password);
        if (user == null) {
            sendMessage(client, "Invalid User Name/Password");
            return true;
        } else {
            user.setSocket(client);
            handler.add(user);
            sendMessage(client, "You are logged in successful.");
            return false;
        }

    }

}
