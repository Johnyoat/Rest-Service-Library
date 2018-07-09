package com.litetech.libs.restservicelib;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.BiConsumer;

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
    private HashMap<String, String> headers = new HashMap<>();

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
        final Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body);

        addHeaders(builder);


        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    private String put(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        final Request.Builder builder = new Request.Builder()
                .url(url)
                .put(body);

        addHeaders(builder);


        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    private void addHeaders(final Request.Builder builder) {
        if (getHeaders() != null & getHeaders().size() > 0) {
            headers.forEach(new BiConsumer<String, String>() {
                @Override
                public void accept(String s, String s2) {
                    builder.addHeader(s, s2);
                }
            });
        }
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
        String restResponse = "";
        if (strings.length == 3) {
            String key = strings[2];

            if (key.equalsIgnoreCase("put")) {
                restResponse = put(strings[0], strings[1]);
            } else if (key.equalsIgnoreCase("post")) {
                restResponse = post(strings[0], strings[1]);
            }
        } else {
            restResponse = get(strings[0]);
        }
        return restResponse;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    @Override
    protected void onPostExecute(String s) {
        callBack.onResult(s, this.apiType);
    }

    public interface CallBack {
        void onResult(String response, String apiType);
    }
}
