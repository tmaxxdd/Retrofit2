package com.tkadziolka.retrofit.data;

import android.util.Log;

public class ErrorInterpreter {

    public static final String TAG = "NETWORK_ERROR";

    public static void parse(int errorCode) {
        switch (errorCode) {
            case 500:
                log("Server error");
                break;
            case 400:
                log("Bad parameter");
                break;
            case 404:
                log("Not found");
                break;
            default:
                log("Unknown error");
                break;
        }
    }

    private static void log(String message) {
        Log.e(TAG, message);
    }

}
