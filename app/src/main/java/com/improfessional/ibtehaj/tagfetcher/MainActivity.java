package com.improfessional.ibtehaj.tagfetcher;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public TextView textURL;
    public Button buttonFetch;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textURL = (TextView) findViewById(R.id.textURL);
        buttonFetch = (Button) findViewById(R.id.buttonFetch);
    }

    public void fetchUrl (View view){

        //Extracting URL from TextView(id:textURL)
        String url = textURL.getText().toString();

        //Check if URL contain  'https://m' Instead of  'https://www'
        if(url.contains("//m")){
            String temp = url.replace("//m","//www");
            url=temp;
        }

        //Running Second Intent
        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);

        //Passing URL onto next Activity
        intent.putExtra("url", url);
        startActivity(intent);

    }


}
