package com.example.test_php2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;

public class first_detail_smile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_detail_smile);

//        AlertDialog.Builder dialog = new AlertDialog.Builder(first_detail_smile.this);
//        dialog.setTitle("การสมัครสมาชิก");
//        dialog.setMessage("สมัครสมาชิกสำเร็จ");
//        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        //dialog.setNegativeButton("Exit",null);
//        dialog.setCancelable(false);
//        dialog.show();

        Button st = findViewById(R.id.start);
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(first_detail_smile.this,first_smile.class);
                startActivity(intent);
            }
        });


    }
}
