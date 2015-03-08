package com.example.robert.family.util.httptasks;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.robert.family.login.LoginActivity;
import com.example.robert.family.main.MainActivity;
import com.example.robert.family.R;
import com.example.robert.family.util.Url;
import com.example.robert.family.util.HttpPoster;
import com.example.robert.family.login.UserToLoginJson;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.entity.StringEntity;

/**
 * Created by robert on 2015-03-06.
 */
public class Login extends AsyncTask<String, Void, String> {

    private final LoginActivity loginActivity;
    private final String email;
    private final String password;

    public Login(LoginActivity loginActivity, String email, String password) {
        this.loginActivity = loginActivity;
        this.email = email;
        this.password = password;
    }

    @Override
    protected String doInBackground(String... urls) {
        UserToLoginJson userToLogin = new UserToLoginJson();
        userToLogin.setEmail(email);
        userToLogin.setPassword(password);
        try {
            String json = new ObjectMapper().writeValueAsString(userToLogin);
            StringEntity entityToSend = new StringEntity(json);
            return HttpPoster.doHttpPost(Url.LOGIN_ATTEMPT_LOGIN, entityToSend);
        } catch (Exception e) { //JsonProcessingException or UnsupportedEncodingException
            e.printStackTrace();
        }
        return "CREATE USER";
    }

    @Override
    protected void onPostExecute(String result) {
        loginActivity.mAuthTask = null;
        loginActivity.showProgress(false);
        switch(result) {
            case "SUCCESS":
                loginActivity.finish();
                loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
                break;
            case "FAILURE": //TODO: Handler for failure.
            case "WRONG PASSWORD":
                loginActivity.passwordView.setError(loginActivity.getString(R.string.error_incorrect_password));
                loginActivity.passwordView.requestFocus();
                break;
            case "CREATE USER":
                new CreateUser(loginActivity, email, password).execute();
                break;
        }
    }
}
