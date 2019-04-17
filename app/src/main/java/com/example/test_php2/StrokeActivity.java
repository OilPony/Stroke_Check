package com.example.test_php2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class StrokeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroke);

        ImageView imageView_st = findViewById(R.id.st_detail);
        imageView_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StrokeActivity.this,Stroke_datail.class);
                startActivity(intent);
            }
        });

        ImageView imageView_fast = findViewById(R.id.fast_detail);
        imageView_fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(StrokeActivity.this,Fast_track.class);
                startActivity(intent2);
            }
        });

        ImageView back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(StrokeActivity.this,UsersActivity.class);
                startActivity(intent2);
            }
        });

    }
}
