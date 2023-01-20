package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private Boolean session = false;

    public boolean validateUser(String userid, String password) {

        this.session = userid.equalsIgnoreCase("test")
                && password.equalsIgnoreCase("1234");

        return this.session;
    }

    public boolean getSession(){
        return this.session;
    }

}
