package com.example.test_php2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Norisk_smile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_norisk_smile);Button back = findViewById(R.id.Back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Norisk_smile.this,UsersActivity.class);
                startActivity(intent);
            }
        });

        Button sm = findViewById(R.id.smile_bt);
        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Norisk_smile.this,arm.class);
                startActivity(intent);
            }
        });
        Button sound = findViewById(R.id.sound_bt);
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Norisk_smile.this,record.class);
                startActivity(intent);
            }
        });


    }
}
