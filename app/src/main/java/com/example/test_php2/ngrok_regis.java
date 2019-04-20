package com.example.test_php2;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.test_php2.sql.DatabaseHelper;

public class ngrok_regis extends AppCompatActivity {
    private final AppCompatActivity activity = ngrok_regis.this;
    private TextInputEditText textInputEditTextNgrok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngrok_regis);
        call_ng();
    }

    DatabaseHelper db1 = new DatabaseHelper(activity);

    private void call_ng(){
//        textInputEditTextNgrok = (TextInputEditText) findViewById(R.id.textInputEditTextNgrok);
//        String ng = textInputEditTextNgrok.getText().toString().trim();
        Button st = findViewById(R.id.ngrokstart2);
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputEditTextNgrok = (TextInputEditText) findViewById(R.id.textInputEditTextNgrok2);
                String ng = textInputEditTextNgrok.getText().toString().trim();
                if(ng != null && ng.length() == 24){
                    db1.addNg(ng);
                    Intent i = new Intent(ngrok_regis.this,RegisterActivity.class);
                    startActivity(i);
                    //Toast.makeText(getBaseContext(), db1.getNg(), Toast.LENGTH_LONG).show();
                }
                else if(ng.length() == 0){
                    Toast.makeText(getBaseContext(), "กรุณาใส่ URL", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "กรุณาใส่ URL ให้ถูกต้อง", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


}


