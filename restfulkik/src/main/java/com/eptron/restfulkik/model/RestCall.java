package com.eptron.restfulkik.model;

import com.loopj.android.http.RequestParams;

/**
 * Model class for storing RestCalls data
 * Created by Claudiu on 20/05/2015.
 */
public class RestCall {
    private int mResponseCode;
    private String mMethodCall;
    private int mExecutionTime;
    private boolean mSuccessful;
    private RequestParams mParams;

    /**
     * Simple constructor
     * @param statusCode The status code responded by the HttpCall
     * @param methodCall The API method call name
     * @param executionTime the time of execution that this restCall took
     * @param successful the time of execution that this restCall took
     */
    public RestCall(int statusCode,String methodCall,int executionTime,boolean successful,RequestParams params){
        mResponseCode = statusCode;
        mMethodCall = methodCall;
        mExecutionTime = executionTime;
        mSuccessful = successful;
        mParams = params;
    }

    /**
     * Getter
     * @return The API method call name
     */
    public String getMethodCall() {
        return mMethodCall;
    }

    /**
     * Getter
     * @return The status code responded by the HttpCall
     */
    public int getResponseCode() {
        return mResponseCode;
    }

    /**
     * Getter
     * @return the time of execution that this restCall took
     */
    public int getExecutionTime() {
        return mExecutionTime;
    }

    /**
     * Getter
     * @return if the restCall was successful of if it failed
     */
    public boolean isSuccessful() {
        return mSuccessful;
    }

    public RequestParams getParams() {
        return mParams;
    }
}
