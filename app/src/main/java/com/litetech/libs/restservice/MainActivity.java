package com.litetech.libs.restservice;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.litetech.libs.restservicelib.RestService;

public class MainActivity extends AppCompatActivity implements RestService.CallBack {

    private TextView restData;
    private RestService restService;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restData = findViewById(R.id.restData);
        dialog = new ProgressDialog(this);

        dialog.setMessage("Fetching Data...");
        dialog.setCancelable(false);

        Button fetchBtn = findViewById(R.id.fetchBtn);

        //Rest Url
        final String restUrl = "https://jsonplaceholder.typicode.com/posts";

        //Setting up Library
//        final RestService restService = new RestService(this);

        //Executing call,Note it's Async call
//        RestService restService = new RestService(this);

        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting Up Rest Service
                restService = new RestService(MainActivity.this);
                //Executing call,Note it's Async call
                restService.execute(restUrl);
                dialog.show();

            }
        });

    }


    //Response from the server
    @Override
    public void onResult(String response, String apiType) {
        if (response != null) {
            dialog.dismiss();
            restData.setText(response);
        } else {
            dialog.dismiss();
            Toast.makeText(this, "Check Network Connectivity", Toast.LENGTH_SHORT).show();
        }
    }
}
