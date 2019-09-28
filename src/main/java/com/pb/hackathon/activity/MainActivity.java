package com.pb.hackathon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.pb.hackathon.R;
import com.pb.hackathon.util.SharedPreferencesUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {


    private EditText edtUsername, edtPassword;

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        edtUsername  = (EditText) findViewById(R.id.edtUsername);
        edtPassword  = (EditText) findViewById(R.id.edtPassword);
        btnLogin  = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if(!username.isEmpty()) {
            SharedPreferencesUtil.saveUser(username);
            if (username.startsWith("shipper")) {
                Intent intent = new Intent(getApplicationContext(), ListVehicleActivity.class);
                startActivity(intent);
            } else {

            }
        }

    }
}
