/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.chatserver.handler;

import com.nabin.chatserver.entity.User;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Navin
 */
public class RequestHandler {

    private List<User> userList = new ArrayList<>();

    public void add(User u) {
        userList.add(u);
    }

    public List<User> getUser() {
        return userList;
    }

    public User getUserBySocket(Socket socket) {
        for (User u : userList) {
            if (u.getSocket().equals(socket)) {
                return u;
            }
        }
        return null;
    }
    public User getByUserName(String userName) {
        for(User u:userList){
        if(u.getUserName().equalsIgnoreCase(userName)){
        return u;
        }
        }
         return null;
    }

}
