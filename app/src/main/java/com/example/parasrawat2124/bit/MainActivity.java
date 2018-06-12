package com.example.parasrawat2124.bit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.loopj.android.http.*;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL="https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";
    TextView rate;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rate=findViewById(R.id.rate);
        spinner=findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.currency_array,R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Bitcoin", "onItemSelected: "+parent.getItemAtPosition(position));
                String finalurl=BASE_URL+parent.getItemAtPosition(position);
                Log.d("Bitcoin", "onItemSelected: Final BaseUrl is :" + finalurl);
                letsdosomenetworking(finalurl);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Hey", "onNothingSelected: Nothing is selected");

            }
        });


    }
    private void letsdosomenetworking(String url){

        AsyncHttpClient client=new AsyncHttpClient();
        client.get(url,new JsonHttpResponseHandler(){

            @Override
            public void onStart() {
                super.onStart();
                Log.d("Bitcoin", "onStart: ENTERED HERE");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Bitcoin", "onSuccess: Reached On sucess"+response.toString());
                try{

                    String price=response.getString("last");
                    rate.setText(price);

                }
                catch (JSONException e){
                    e.printStackTrace();

                }


            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Bitcoin", "onFailure:Request FAIL "+statusCode);
                Log.d("Bitcoin", "onFailure: Fail RESPONSE"+responseString);
                Log.e("Bitcoin", "onFailure: "+throwable.toString() );
            }
        });


    }
}
