package com.master.math.activity.service;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.master.math.activity.util.AppConstants;

import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


public class Service extends AsyncTask<Void, Void, Void> {
    ProgressDialog progressBar;
    private String url;
    private HashMap<String, String> data;
    private RequestParams rp;
    private Context context;
    private ServiceResponse sr;
    private JSONObject response;
    private byte[] blobResp;
    private String loadingTitle;
    private String method;

    public Service(String loadingTitle, Context context, ServiceResponse serviceResponse) {
        this.context = context;
        this.sr = serviceResponse;
        this.loadingTitle = loadingTitle;
    }

    public void setLoadingTitle(String loadingTitle) {
        this.loadingTitle = loadingTitle;
    }

    public void post(String url, RequestParams params) {
        this.url = url;
        this.rp = params;
        this.method = AppConstants.METHOD_POST;
    }

    public void get(String url, RequestParams params){
        this.url = url;
        this.rp = params;
        this.method = AppConstants.METHOD_GET;
    }

    @Override
    protected void onPreExecute() {
        progressBar = new ProgressDialog(this.context);
        progressBar.setCancelable(true);
        progressBar.setMessage(this.loadingTitle);
        progressBar.show();

    }

    @Override
    protected void onPostExecute(Void result) {
        if (response != null) {
            sr.postExecute(response);
        }
        try {
            progressBar.dismiss();
        } catch (Exception e) {
        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        SyncHttpClient client = new SyncHttpClient();

        if (this.method.equals(AppConstants.METHOD_POST)) {
            client.post(url, rp, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject resp) {
                    super.onSuccess(statusCode, headers, resp);
                    response = resp;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    response = errorResponse;
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String st, Throwable th){
                    String s = "";
                }
            });
        }else if(this.method.equals(AppConstants.METHOD_GET)){
            client.get(url, rp, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject resp) {
                    super.onSuccess(statusCode, headers, resp);
                    response = resp;
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    response = errorResponse;
                }
            });
        }
        return null;
    }
}

