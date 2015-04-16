package com.mvidalgarcia.pinmap.ws.impl;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mvidalgarcia.pinmap.model.Pin;
import com.mvidalgarcia.pinmap.ws.PinWS;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class PinREST implements PinWS {
    private static final String URL_BASE = "http://156.35.95.75:8888";

    /* Pins */

    private static final String GET_PINS = "/pins/";
    private static final String GET_PIN = "/pin/";
    private static final String INSERT_PIN = "/pin";


    public List<Pin> getPinsByGooglePlusId(String googlePlusId) throws JSONException, IOException {
        /* Get JSON array */
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL_BASE + GET_PINS + googlePlusId);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line, json="";
        while ((line = rd.readLine()) != null)
            json += line;

        /* Convert JSON string to Java Object */
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Type linesList = new TypeToken<List<Pin>>(){}.getType();
        List<Pin> result = gson.fromJson(json, linesList);
        return result == null ? null : result;
    }

    public Pin getPinById(int id) throws IOException {
        /* Get JSON object */
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL_BASE + GET_PIN + id);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line, json="";
        while ((line = rd.readLine()) != null)
            json += line;
        client.getConnectionManager().shutdown();

        /* Convert JSON string to Java Object */
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Pin result = gson.fromJson(json, Pin.class);
        return result == null ? null : result;
    }

    public void insertPin(Pin pin) throws IOException {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        String json = gson.toJson(pin);
        Log.d(getClass().getSimpleName(), "insertPin: json { " + json + " }");
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_BASE + INSERT_PIN);
        httpPost.setHeader("Content-Type", "application/json");
        byte[] contentBytes = json.getBytes("UTF-8");
        httpPost.setEntity((new ByteArrayEntity(contentBytes)));
        HttpResponse response = client.execute(httpPost);
        client.getConnectionManager().shutdown();
    }

    public void deletePin(int id) throws IOException {
        Log.d(getClass().getSimpleName(), "deletePin: id { " + id + " }");
        HttpClient client = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete(URL_BASE + GET_PIN + id);
        HttpResponse response = client.execute(httpDelete);
        client.getConnectionManager().shutdown();
    }

}
