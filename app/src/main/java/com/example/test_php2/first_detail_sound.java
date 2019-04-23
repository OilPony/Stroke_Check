package com.example.test_php2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;

public class first_detail_sound extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_detail_sound);
        Button st = findViewById(R.id.start);
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(first_detail_sound.this,first_sound.class);
                startActivity(intent);
            }
        });

        Button skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(first_detail_sound.this);
                dialog.setTitle("การบันทักค่า");
                dialog.setMessage("บันทึกค่าสำเร็จ");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(first_detail_sound.this, UsersActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                //dialog.setNegativeButton("Exit",null);
                dialog.setCancelable(false);
                dialog.show();
//                Intent intent = new Intent(first_detail_sound.this,UsersActivity.class);
//                startActivity(intent);
            }
        });
    }
}
