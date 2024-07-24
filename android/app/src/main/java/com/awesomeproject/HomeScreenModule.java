package com.awesomeproject;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class HomeScreenModule extends ReactContextBaseJavaModule {

    public HomeScreenModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "HomeScreenModule";
    }

    @ReactMethod
    public void someMethod() {
        // Implementation
    }
}
