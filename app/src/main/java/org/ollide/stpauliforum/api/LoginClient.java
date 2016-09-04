package org.ollide.stpauliforum.api;

import org.ollide.stpauliforum.ForumApp;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;

public class LoginClient {

    @Inject
    LoginService loginService;

    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";

    public LoginClient() {
        ForumApp.component().inject(this);
    }

    private static Map<String, String> getLoginMap() {
        Map<String, String> map = new HashMap<>();
        map.put("autologin", "on");
        map.put("login", "Login");
        return map;
    }

    public Call<Void> login(String username, String password) {
        Map<String, String> map = getLoginMap();
        map.put(FIELD_USERNAME, username);
        map.put(FIELD_PASSWORD, password);

        return loginService.login(map);
    }
}
