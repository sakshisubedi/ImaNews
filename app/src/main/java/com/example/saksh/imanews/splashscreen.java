package com.example.saksh.imanews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

public class splashscreen extends AppCompatActivity {

    FirebaseAuth auth;
    Boolean isLoggedin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        auth= FirebaseAuth.getInstance();
        Thread myThread=new Thread()
        {
            @Override
            public void run() {

                try {
                    sleep(3000);
                    Intent signOut=new Intent(getApplicationContext(),MainActivity.class);
                    Intent signIn=new Intent(getApplicationContext(),HomeActivity.class);
                    if(auth.getCurrentUser()==null)
                    {
                        startActivity(signOut);
                        finish();
                    }
                    else
                    {
                        startActivity(signIn);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
