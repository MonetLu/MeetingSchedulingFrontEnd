package com.example.comp2100_6442_androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // execute this part of code when click on Sign up button in main page
    public void Sign_up_button(View v){
        Intent intent = new Intent(MainActivity.this, sign_up_page.class);
        startActivity(intent);
    }

    // execute this part of code when click on Log in button
    public void Log_in_button(View v){
        Toast toast = Toast.makeText(getApplicationContext(), "you have already Log in!", Toast.LENGTH_SHORT);
        toast.show();
    }

    // execute this part of code when click on Forget password
    public void Forget_password(View v){
        Toast toast = Toast.makeText(getApplicationContext(), "you have already clicked that!", Toast.LENGTH_SHORT);
        toast.show();
    }
}
