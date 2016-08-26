package io.github.vipinagrahari.epragati;

import android.app.Application;

import io.smooch.core.Smooch;

/**
 * Created by vivek on 26/8/16.
 */
public class AppClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Smooch.init(this, "7xqawou2mvinargvw6h1kirab");
    }
}
