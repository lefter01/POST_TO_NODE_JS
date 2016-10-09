package com.example.workspace.posttonodejs;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WorkSpace on 10/9/2016.
 */

public class AuthenticationRequest extends StringRequest
{

    private final HashMap<String, String> headers;
    private static final String AUTHENTICATION_REQUEST_URL = "http://83.212.114.205:2222/api/dashboard";

    public AuthenticationRequest(String token, Response.Listener<String> listener) {
        super(Method.GET, AUTHENTICATION_REQUEST_URL, listener, null);
        headers = new HashMap<>();

        headers.put("Authorization", token);
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
