package com.siziksu.layers.common.manager;

import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

public final class ThrowableManager {

    public static final int CONNECTION_EXCEPTION = 0;
    public static final int TIME_OUT_EXCEPTION = 1;
    public static final int UNAUTHORIZED_EXCEPTION = 2;
    public static final int SERVER_EXCEPTION = 3;
    public static final int GENERIC_EXCEPTION = 4;

    private static final String TAG = "ThrowableManager";

    private ThrowableManager() {}

    public static int handleException(Throwable throwable) {
        Log.e(TAG, throwable.getMessage(), throwable);
        int type;
        if (throwable instanceof HttpException) {
            Log.e(TAG, "HttpException");
            type = handleHttpException(throwable);
        } else if (throwable instanceof ConnectException) {
            Log.e(TAG, "ConnectException");
            type = CONNECTION_EXCEPTION;
        } else if (throwable instanceof SocketTimeoutException) {
            Log.e(TAG, "SocketTimeoutException");
            type = TIME_OUT_EXCEPTION;
        } else {
            Log.e(TAG, "GenericException");
            type = GENERIC_EXCEPTION;
        }
        return type;
    }

    private static int handleHttpException(Throwable throwable) {
        int code = ((HttpException) throwable).response().code();
        if (code >= 500) {
            return SERVER_EXCEPTION;
        } else if (code == 401) {
            return UNAUTHORIZED_EXCEPTION;
        }
        return GENERIC_EXCEPTION;
    }
}
