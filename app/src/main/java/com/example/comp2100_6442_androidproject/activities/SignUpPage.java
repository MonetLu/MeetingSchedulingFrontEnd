package com.example.comp2100_6442_androidproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comp2100_6442_androidproject.R;
import com.example.comp2100_6442_androidproject.utils.ConnectionTemplate;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SignUpPage extends AppCompatActivity {
    //TAG for testing
    private static final String TAG = "SignUpPage";

    EditText emailAddressEditText;
    EditText verificationCodeEditText;
    EditText nameEditText;
    EditText passwordEditText;
    //save verification code to verify
    String correctVerificationCode;

    Boolean isMailExist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        emailAddressEditText = findViewById(R.id.signUpEmailAddress);
        verificationCodeEditText = findViewById(R.id.locaEdit);
        nameEditText = findViewById(R.id.AccountName);
        passwordEditText = findViewById(R.id.noteEdit);
    }

    //back to LogInPage
    public void backButton(View v) {
        finish();
    }

    // execute this part of code when click on Back Home
    public void BackHome(View v) {
        finish();
    }

    // execute this part of code when click on Send Verification Code
    public void SendVerificationCode(View v) {
        emailAddressEditText = findViewById(R.id.signUpEmailAddress);
        //create a random number as verification code
        int r = (int) (1000 + Math.random() * 8999);
        correctVerificationCode = r + "";
        //request parameter
        String parameter = "?email=" + emailAddressEditText.getText().toString().trim()
                + "&verificationCode=" + correctVerificationCode;

        Call task = ConnectionTemplate.getConnection("/sendVerificationCode", parameter);
        //asynchronous connection which does not need open a new thread
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }

            //get response
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                Log.d(TAG, "code: " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    ResponseBody body = response.body();
                    String s = body.string();
                    //get the result that whether the email is exist
                    isMailExist = s.trim().equals("1");
                    Log.d(TAG, "body: " + s);
                    Log.d(TAG, "onResponse: ismailexist" + isMailExist);
                }
            }
        });

        if (emailAddressEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "please fill in the blanks", Toast.LENGTH_SHORT).show();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "The verification code has been sent to your email, please check.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // execute this part of code when click on Sign up button in sign up page
    public void Sign_up_successful_button(View v) {

        String email = emailAddressEditText.getText().toString().trim();
        String verificationCode = verificationCodeEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        //check empty blanks
        if ("".equals(email) || "".equals(name) || "".equals(verificationCode) || "".equals(password)) {
            Toast.makeText(this, "please fill in the blanks", Toast.LENGTH_SHORT).show();
            //check verification code
        } else if (!verificationCode.equals(correctVerificationCode)) {
            Toast.makeText(this, "please input the correct verification code", Toast.LENGTH_SHORT).show();
            //check whether the email has been existed
        } else if (isMailExist) {
            Toast.makeText(this, "the email has existed", Toast.LENGTH_SHORT).show();
            //connect and save the user information
        } else {

            String parameter = "?email=" + email
                    + "&name=" + name
                    + "&password=" + password;

            Call task = ConnectionTemplate.getConnection("/signUp", parameter);
            task.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG, "onFailure: " + e.toString());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    int code = response.code();
                    Log.d(TAG, "code: " + code);
                    if (code == HttpURLConnection.HTTP_OK) {
                        ResponseBody body = response.body();
                        Log.d(TAG, "body: " + body.string());
                    }
                }
            });
            //toast and back
            Toast toast = Toast.makeText(getApplicationContext(), "you have already Sign up!", Toast.LENGTH_SHORT);
            toast.show();
            this.backButton(null);
        }

    }
}
