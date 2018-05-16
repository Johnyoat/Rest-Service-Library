package com.litetech.libs.restservicelib;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressLint("NewApi")
@SuppressWarnings("all")
public class RestService extends AsyncTask<String, Void, String> {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();
    private CallBack callBack;
    private String apiType;

    public RestService(@NonNull CallBack callBack) {
        this.callBack = callBack;
    }

    public RestService() {
    }

    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }



    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = null;
        try {
            response = restCall(strings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    private String restCall(String... strings) throws IOException {
        String restResponse;
        if (strings.length == 2) {
            restResponse = post(strings[0], strings[1]);
        } else {
            restResponse = get(strings[0]);
        }
        return restResponse;
    }

    @Override
    protected void onPostExecute(String s) {
        callBack.onResult(s,this.apiType);
    }

    public interface CallBack {
        void onResult(String response, String apiType);
    }
}
