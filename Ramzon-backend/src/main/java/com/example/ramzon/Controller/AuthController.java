package com.example.ramzon.Controller;

import com.example.ramzon.Config.JwtProvider;
import com.example.ramzon.Exception.UserException;
import com.example.ramzon.Request.LoginRequest;
import com.example.ramzon.Model.User;
import com.example.ramzon.Repository.UserRepository;
import com.example.ramzon.Response.AuthResponse;
import com.example.ramzon.Service.CartService;
import com.example.ramzon.Service.CustomerUserImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomerUserImplementation customerUserImplementation;

    @Autowired
    CartService cartService;

    @PostMapping("/signup")
    public ResponseEntity <AuthResponse> createUserHandler(@RequestBody User user) throws  Exception {

        String email = user.getEmail();
        String password = user.getPassword();
        String firstName =user.getFirstName();
        String lastName  = user.getLastName();
        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist !=null){
            throw  new UserException("User already exist");
        }

        User newUser =  new User();
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        User saveNewUser = userRepository.save(newUser);
        cartService.createCart(saveNewUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(saveNewUser.getEmail(),saveNewUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse =  new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("SignUp Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>  loginUserHandler(@RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authentication(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

       AuthResponse authResponse =  new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("SignUp Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    private Authentication authentication(String email, String password) {

        UserDetails userDetails = customerUserImplementation.loadUserByUsername(email);

        if (userDetails == null){
            throw  new BadCredentialsException("Invalid Username");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw  new BadCredentialsException("Invalid Password");
        }

        return  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
