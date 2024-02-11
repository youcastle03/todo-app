package com.springboot.myfirstwebapp.loginAuthenticationService;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public boolean authenticate(String username,String password){

        boolean isValidUserName = username.equalsIgnoreCase("admin");
        boolean isValidPassword = password.equalsIgnoreCase("0000");
        return isValidUserName&&isValidPassword;
    }
}
