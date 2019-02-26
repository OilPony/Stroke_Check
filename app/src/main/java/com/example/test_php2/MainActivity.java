package com.example.test_php2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.google.gson.Gson;
import org.json.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                php();
            }
        });
    }
    public void php(){
        Ion.with(this)
                .load("http://bfe63f9c.ngrok.io/pro-android/upload/test.php")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
                    }
                });
//        Ion.with(this)
//                .load("http://bfe63f9c.ngrok.io/pro-android/upload/smile/test.php")
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        //show_pic_smile sm = new show_pic_smile();
//                        //JsonObject jsonObject = (JsonObject)result;
//
//                        //String str = result.get("str").getAsString();
//                        //String str = result.get("result").getAsString();
//
//                        //Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
//
//                        Toast.makeText(getBaseContext(), result.getAsShort(), Toast.LENGTH_LONG).show();
//
//
//                    }
//                });

    }
}
