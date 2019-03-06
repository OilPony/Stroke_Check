package com.example.test_php2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test_php2.R;
import com.example.test_php2.utils.PreferenceUtils;

/**
 * Created by delaroy on 3/27/17.
 */
public class UsersActivity extends AppCompatActivity {

    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        textViewName = (TextView) findViewById(R.id.text1);
        Intent intent = getIntent();
        if (intent.hasExtra("EMAIL")){
            String nameFromIntent = getIntent().getStringExtra("EMAIL");
            textViewName.setText("Welcome " + nameFromIntent);
        }else{
            String email = PreferenceUtils.getName(this);
            textViewName.setText("Welcome " + email);

        }

        Button sm = findViewById(R.id.button);
        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsersActivity.this, com.example.test_php2.smile.class);
                startActivity(i);

            }
        });

        Button arm = findViewById(R.id.button2);
        arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsersActivity.this,arm.class);
                startActivity(i);

            }
        });

        Button record = findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsersActivity.this,record.class);
                startActivity(i);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.log_out:
                PreferenceUtils.savePassword(null, this);
                PreferenceUtils.saveName(null, this);
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
