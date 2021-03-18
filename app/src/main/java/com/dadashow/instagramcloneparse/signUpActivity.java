package com.dadashow.instagramcloneparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class signUpActivity extends AppCompatActivity {
    EditText name;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.signUpActvNameText);
        password=findViewById(R.id.signUpActvPasswordText);
        ParseUser user=ParseUser.getCurrentUser();
        if (user!=null){
            Intent intent=new Intent(signUpActivity.this,FeedActivity.class);
            startActivity(intent);
        }
    }
    public void login(View view){
        ParseUser.logInInBackground(name.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e!=null){
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }else {

                    Intent intent=new Intent(signUpActivity.this,FeedActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    public void signUp(View view){
        ParseUser user=new ParseUser();
        user.setUsername(name.getText().toString());
        user.setPassword(password.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(signUpActivity.this,FeedActivity.class);
                    startActivity(intent);

                }
            }
        });
    }
}