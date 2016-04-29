/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.chatserver.dao.impl;

import com.nabin.chatserver.dao.UserDAO;
import com.nabin.chatserver.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Navin
 */
public class UserDAOImpl implements UserDAO{
private List<User> userList=new ArrayList<>();
    @Override
    public List<User> getAll() {
        if(userList.size()==0){
        userList.add(new User("sagar","sagar"));
        userList.add(new User("santosh","santosh"));
        userList.add(new User("bipin","bipin"));
        userList.add(new User("bibek","bibek"));
        }
        
        return userList;
    }

    @Override
    public User getByUserName(String userName) {
        for(User u:getAll()){
        if(u.getUserName().equalsIgnoreCase(userName)){
        return u;
        }
        }
         return null;
    }

    @Override
    public User login(String userName, String password) {
         for(User u:getAll()){
        if(u.getUserName().equals(userName) && u.getPassword().equals(password)){
        return u;
        }
        }
         return null;
    }
   
    
}
