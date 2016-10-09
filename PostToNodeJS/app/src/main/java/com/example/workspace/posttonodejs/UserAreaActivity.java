package com.example.workspace.posttonodejs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAreaActivity extends AppCompatActivity {

    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        EditText etEmail = (EditText) findViewById(R.id.etEmail);

        EditText etAge = (EditText) findViewById(R.id.etAge);
        TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);
        EditText etToken = (EditText) findViewById(R.id.etToken);
        Button bAuthenticationTest = (Button) findViewById(R.id.bAuthenticationTest);
        Button bShowAllUsers = (Button) findViewById(R.id.bShowAllUsers);

        final Intent intent = getIntent();


        etToken.setText(intent.getStringExtra("token"));
        welcomeMessage.setText("Welcome : : "+intent.getStringExtra("name"));
        etAge.setText(intent.getStringExtra("age"));
        etEmail.setText(intent.getStringExtra("email"));

        SharedPreferences prefs = this.getSharedPreferences("MY_JWT_TOKEN", Context.MODE_PRIVATE);
        data = prefs.getString("token","token doesnt exist");



        bAuthenticationTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse.getBoolean("success"))
                            {
                                Intent intent = new Intent(UserAreaActivity.this,PassedAuthenticationActivity.class);                                ;
                                UserAreaActivity.this.startActivity(intent);
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };
                AuthenticationRequest authenticationRequest = new AuthenticationRequest(data, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
                queue.add(authenticationRequest);
            }
        });
        bShowAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UserAreaActivity.this,ShowAllUsersActivity.class);
                UserAreaActivity.this.startActivity(intent1);
            }
        });


    }
}
