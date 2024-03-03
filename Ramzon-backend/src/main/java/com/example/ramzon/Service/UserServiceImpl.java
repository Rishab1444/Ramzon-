package com.example.ramzon.Service;

import com.example.ramzon.Config.JwtProvider;
import com.example.ramzon.Exception.UserException;
import com.example.ramzon.Model.User;
import com.example.ramzon.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;


    @Override
    public User findById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            return user.get();
        }
        throw new UserException("User not found");
    }

    @Override
    public User findByJwt(String jwt) throws UserException {

        String  email = jwtProvider.getEmailfromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user != null){
            return user;
        }
        throw new UserException("User not found");
    }
}
