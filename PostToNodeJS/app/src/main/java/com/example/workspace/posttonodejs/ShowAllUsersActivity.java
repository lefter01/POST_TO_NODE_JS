package com.example.workspace.posttonodejs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowAllUsersActivity extends AppCompatActivity   {

    BufferedReader reader = null;
    HttpURLConnection connection = null;
    TextView tvTest;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_users);
        SharedPreferences prefs = this.getSharedPreferences("MY_JWT_TOKEN", Context.MODE_PRIVATE);
        data = prefs.getString("token","token doesnt exist");
        System.out.print(data);
        tvTest = (TextView) findViewById(R.id.tvTest);
        new JSONTask().execute("http://83.212.114.205:2222/api/showAllUSers");

    }
    public class JSONTask extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... params) {

            try {

                URL url = new URL(params[0]);
                //ALREADY DONE YO
                //setrequestproperty for authorization header after the connection is opened always

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization",data);

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line;

                while ((line = reader.readLine())!= null)
                {
                    buffer.append(line);
                }
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null)
                    connection.disconnect();
                try {
                    if(reader!=null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject parentObject = new JSONObject(s);
                JSONArray parentArray = parentObject.getJSONArray("allusers");


                StringBuffer finalBufferedData = new StringBuffer();

                for(int i=0; i<parentArray.length();i++)
                {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    String email = finalObject.getString("email");
                    String name = finalObject.getString("name");
                    int age = finalObject.getInt("age");
                    finalBufferedData.append("email: "+email +" -name: " + name + " -age: " + age + " \n");
                }

                tvTest.setText(finalBufferedData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
