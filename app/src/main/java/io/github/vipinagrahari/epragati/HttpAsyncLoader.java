package io.github.vipinagrahari.epragati;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by vivek on 29/8/16.
 */

public class HttpAsyncLoader extends AsyncTaskLoader<JsonObject> {

    Call<JsonObject> networkCall;
    JsonObject responseObject;

    public HttpAsyncLoader(Context context, Call<JsonObject> networkCall) {
        super(context);
        this.networkCall = networkCall;

    }

    @Override
    public JsonObject loadInBackground() {
        networkCall = networkCall.clone();

        try {
            Response<JsonObject> response = networkCall.execute();
            if (null != response)
                responseObject = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseObject;
    }


    @Override
    protected void onStartLoading() {

        super.onStartLoading();

        if (responseObject != null) {

            deliverResult(responseObject);
        }

        if (takeContentChanged() || responseObject == null) {

            forceLoad();
        }
    }

    @Override
    protected void onReset() {

        super.onReset();

        responseObject = null;
    }
}
