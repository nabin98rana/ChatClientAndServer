/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nabin.chatserver.dao;

import com.nabin.chatserver.entity.User;
import java.util.List;

/**
 *
 * @author Navin
 */
public interface UserDAO {
    List<User>getAll();
    User getByUserName(String userName);
    User login(String userName,String password);;
    
}
