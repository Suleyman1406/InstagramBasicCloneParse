package com.dadashow.instagramcloneparse;

import android.app.Application;

import com.parse.Parse;

public class ParseInitializationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("5tK1U5G4LDBoTwbgGwhR0vTPCRcEsYz5Mcgcf4xF")
                .clientKey("bk1NLIvHczVOZTK5JABgY6eyBG3F5i5mDcd8Q03Q")
                .server("https://parseapi.back4app.com/")
                .build());
    }
}
