package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class LoginResultSaver implements LoginFormObserver{

    private List<Boolean> loginResults;

    public LoginResultSaver() {
        loginResults = new ArrayList<>();
    }

    @Override
    public void onLoginResult(boolean loginSuccessful) {
        loginResults.add(loginSuccessful);
    }
}

