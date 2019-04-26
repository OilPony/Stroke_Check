package com.example.test_php2;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;

import com.example.test_php2.R;
import com.example.test_php2.helper.InputValidation;
import com.example.test_php2.model.User;
import com.example.test_php2.sql.DatabaseHelper;
import com.example.test_php2.utils.PreferenceUtils;
import com.example.test_php2.sql.DatabaseHelper;
import com.example.test_php2.utils.PreferenceUtils;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = Main2Activity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private AppCompatTextView sever;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TextView sever = findViewById(R.id.sever);
//        sever.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Main2Activity.this,ngrok_login.class);
//                startActivity(intent);
//            }
//        });
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }
    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

        sever = (AppCompatTextView) findViewById(R.id.sever);

        PreferenceUtils utils = new PreferenceUtils();

        if (utils.getName(this) != null ){
            Intent intent = new Intent(Main2Activity.this, UsersActivity.class);
            startActivity(intent);
        }else{

        }
    }

    private void initListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
        sever.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.sever:
                Intent intentsever = new Intent(getApplicationContext(), ngrok_login.class);
                startActivity(intentsever);
                break;

        }
    }

    private void verifyFromSQLite(){
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutName, "กรุณาใส่ชื่อ")) {
            return;
        }
//        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
//            return;
//        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, "กรุณาใส่รหัสผ่าน")) {
            return;
        }
        String name = textInputEditTextEmail.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();

        if (databaseHelper.checkUser(name, password)) {
            PreferenceUtils.saveName(name, this);
            databaseHelper.add(name);
            PreferenceUtils.savePassword(password, this);
            Intent accountsIntent = new Intent(activity, UsersActivity.class);
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            //emptyInputEditText();

            startActivity(accountsIntent);
            finish();



        } else {
            Snackbar.make(nestedScrollView, "กรุณาใส่ชื่อและรหัสผ่านให้ถูกต้อง", Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
