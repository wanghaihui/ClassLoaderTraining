package com.story.classloadertraining;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URLClassLoader;

import dalvik.system.DexClassLoader;

/**
 * 研究ClassLoader
 * http://blog.csdn.net/javazejian/article/details/73413292
 * 基于Java 8版本
 */

public class MainActivity extends AppCompatActivity {

    DexClassLoader dexClassLoader;
    URLClassLoader urlClassLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
