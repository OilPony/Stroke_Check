package com.example.test_php2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ngrok_regis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngrok_regis);

        Button st = findViewById(R.id.ngrokregisstart);
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ngrok_regis.this,first_detail_smile.class);
                startActivity(i);

            }
        });
    }
}
