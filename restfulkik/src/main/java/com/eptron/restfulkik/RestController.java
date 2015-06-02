package com.eptron.restfulkik;


import android.content.Context;
import android.net.ConnectivityManager;

import com.eptron.restfulkik.model.MainEvent;
import com.eptron.restfulkik.model.RestCall;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/**
 * Controller class in charge of executing the specific requests
 * sent by the subClass implementing this class. Also keeps track of the
 * calls executed
 * The subClass will simply call on doRestCall with the proper parameters and
 * rest will be handled by the controller
 * Created by Claudiu on 19/05/2015.
 */
public abstract class RestController {

    public static String BASE_URL;

    private static RestController sInstance;
    protected static RestHandler.Builder sBuilder;
    private Context mContext;

    private int mErrors = 0;
    private int mSuccesses = 0;
    private ArrayList<RestCall> mCalls = new ArrayList<>();

    public void init(Context context){
        mContext = context;
    }

    /**
     * Execute rest call of the given type to the specified methodCall
     * with the given parameters
     * @param methodCall The API method name to be called to
     * @param params The parameters needed for the method name (@RequestParams)
     * @param type The type of HttpCall (GET,POST,PUT)
     * @return if the call was executed or not (lack of internet connection)
     */
    public boolean doRestCall(String methodCall,RequestParams params,int type){
        return doRestCall(methodCall,params,type,null);
    }

    /**
     * Execute rest call of the given type to the specified methodCall
     * with the given parameters
     * @param methodCall The API method name to be called to
     * @param params The parameters needed for the method name (@RequestParams)
     * @param type The type of HttpCall (GET,POST,PUT)
     * @param successfulEvent the event to be posted if methodCall is successful
     *                        if null is given it will create an empty (@MainEvent)
     *                        with the returned JSONObject/JSONArray by the call
     * @return if the call was executed or not (lack of internet connection)
     */
    public boolean doRestCall(String methodCall,RequestParams params,int type,MainEvent successfulEvent){
        return doRestCall(methodCall,params,type,successfulEvent,null);
    }

    /**
     * Builds and execute rest call of the given type to the specified methodCall
     * with the given parameters
     * @param methodCall The API method name to be called to
     * @param params The parameters needed for the method name (@RequestParams)
     * @param type The type of HttpCall (GET,POST,PUT)
     * @param successfulEvent the event to be posted if methodCall is successful
     *                        if null is given it will create a default event
     *                        of type MainEvent (@MainEvent) containing the
     *                        returned JSONObject/JSONArray by the call
     * @param failEvent The event to be posed if methodCall is unsuccessful
     *                  if null is given it will create a default event
     *                  of type MainEvent (@MainEvent) containing the
     *                  returned JSONObject/JSONArray by the call
     * @return if the call was executed or not (lack of internet connection)
     */
    public boolean doRestCall(String methodCall,RequestParams params,int type,MainEvent successfulEvent,MainEvent failEvent){
        if(hasInternet()) {
            sBuilder
                    .setMethodCall(methodCall)
                    .setRequestParams(params)
                    .setEventSuccess(successfulEvent)
                    .setEventFail(failEvent)
                    .setType(type)
                    .build(this);
            return true;
        }
        return false;
    }

    /**
     * Handles the response from the RestHandler and does some stuff
     * Still to be improved and added more logic
     * @param call The HttpCall request and response details
     */
    public void handleResponse(RestCall call){
        mCalls.add(call);
        if(call.isSuccessful()) mSuccesses++;
            else mErrors++;
    }

    /**
     * Getter
     * @return Returns all the calls handled by this controller
     */
    public ArrayList<RestCall> getCalls(){
        return mCalls;
    }

    /**
     * Getter
     * @return number of failed rest calls
     */
    public int getErrorCount() {
        return mErrors;
    }

    /**
     * Getter
     * @return number of successful rest calls
     */
    public int getSuccessCount(){
        return mSuccesses;
    }

    public boolean hasInternet(){
        try {
            final ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
