package com.eptron.restfulkik.model;

/**
 * Created by eptron on 13/05/2015.
 */
public class FailedRequest extends MainEvent{
    private String mResponse;

    public FailedRequest(){
    }

    public String getMethodCall(){
        return getRestCall().getMethodCall();
    }

    @Override
    public void parseObject() {
        mResponse = mJSONObject.toString();
    }
}
