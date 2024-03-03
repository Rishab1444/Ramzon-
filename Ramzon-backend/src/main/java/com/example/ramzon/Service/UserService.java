package com.example.ramzon.Service;

import com.example.ramzon.Exception.UserException;
import com.example.ramzon.Model.User;


public interface UserService {

    public User findById(Long userId) throws UserException;

    public User findByJwt(String  jwt) throws UserException;


}
