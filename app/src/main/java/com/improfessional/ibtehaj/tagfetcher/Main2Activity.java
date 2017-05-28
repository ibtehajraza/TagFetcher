package com.improfessional.ibtehaj.tagfetcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {

    private WebView webV ;
    public ArrayList<String> tags;
    public ListView myTags;
    public String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        link=intent.getStringExtra("url");


        myTags = (ListView) findViewById(R.id.myTags);
        webV = (WebView) findViewById(R.id.webV);
        tags=new ArrayList<>();

        func();

    }



    public void func(){
        /* JavaScript must be enabled if you want it to work, obviously */
        webV.getSettings().setJavaScriptEnabled(true);

        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        webV.getSettings().setUserAgentString(newUA);

        /* Register a new JavaScript interface called HTMLOUT */
        webV.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

        /* WebViewClient must be set BEFORE calling loadUrl! */
        webV.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
        /* This call inject JavaScript into the page which just finished loading. */
                webV.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
        });



        /* load a web page */
        webV.loadUrl(link);

    }


    /* An instance of this class will be registered as a JavaScript interface */
    private class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html)
        {
            // process the html as needed by the app

            //Doing Matching in HTML
            Pattern p = Pattern.compile("<meta property=\"og:video:tag\" content=\"(.*?)\">");
            Matcher m = p.matcher(html);

            while (m.find()) {
                String str=m.group(1);
                tags.add(str);

            }

            runOnUiThread(new Runnable() {
                public void run() {
                    for (int i=0;i< tags.size();i++){
                        System.out.println("Tags:"+tags.get(i));
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, tags);
                    myTags.setAdapter(arrayAdapter);
                }
            });


        }
    }

}
