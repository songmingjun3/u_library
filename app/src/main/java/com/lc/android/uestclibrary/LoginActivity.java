package com.lc.android.uestclibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    public EditText editTextName;
    public EditText editTextPassword;
    public Button buttonLogin;
    public Button buttonCancel;
    public String username,password;
    boolean quitLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName = (EditText)findViewById(R.id.editText_name);
        editTextPassword = (EditText)findViewById(R.id.editText_password);
        buttonLogin = (Button)findViewById(R.id.button_login);
        buttonCancel = (Button) findViewById(R.id.button_cancel);


//        username = editTextName.getText().toString();
//        password = editTextPassword.getText().toString();
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                username = editTextName.getText().toString();
                password = editTextPassword.getText().toString();
                AsyncLogin asyncLogin = new AsyncLogin(LoginActivity.this);
                asyncLogin.execute();

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              /*  username = null;
                password = null;
                quitLogin = true;
                AsyncLogin asyncLogin = new AsyncLogin(LoginActivity.this);
                asyncLogin.execute();*/
                Intent intent = new Intent(LoginActivity.this,SearchActivity.class);
                LoginActivity.this.startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(LoginActivity.this,SearchActivity.class);
        LoginActivity.this.startActivity(intent);
    }
}
