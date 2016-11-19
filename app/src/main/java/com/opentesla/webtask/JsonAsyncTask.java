package com.opentesla.webtask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Nick on 10/20/2016.
 */

public abstract class JsonAsyncTask extends AsyncTask<Void, Integer, JSONObject> {
    protected String mUrl;
    protected String mToken;
    protected OnTaskDoneListener mOnTaskDoneListener;

    public JsonAsyncTask(String url, String token, OnTaskDoneListener onTaskDoneListener)
    {
        super();
        mUrl = url;
        mToken = token;
        mOnTaskDoneListener = onTaskDoneListener;
    }

    @Override
    protected abstract JSONObject doInBackground(Void... params);


    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        Log.d(this.toString(), result.toString());

        if(mOnTaskDoneListener != null) {
            if (result != null) {
                mOnTaskDoneListener.onTaskDone(result);
            }
            else
            {
                Log.e(this.toString(), "Web request returned NULL");
                mOnTaskDoneListener.onError("Web request returned NULL");
            }
        }
        else
        {
            Log.d(this.toString(), "mOnTaskDoneListener is null");
        }
    }

    protected JSONObject getRequest(String url, String oauthToken) {
        JSONObject jsonObj = new JSONObject();
        URL myURL;
        HttpURLConnection connection = null;
        try {
            myURL = new URL(url);
            connection = (HttpURLConnection) myURL.openConnection();
            String basicAuth = "Bearer " + oauthToken;
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setUseCaches(false);
            connection.setReadTimeout(15 * 1000);

            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp = "";

                while ((tmp = reader.readLine()) != null) {
                    json.append(tmp).append("\n");
                }

                reader.close();

                jsonObj = new JSONObject(json.toString());

            }
        }catch (ProtocolException e) {
            Log.e(this.toString(), e.getLocalizedMessage());
        } catch (MalformedURLException e) {
            Log.e(this.toString(), e.getLocalizedMessage());
        } catch (IOException e) {
            Log.e(this.toString(), e.getLocalizedMessage());
        } catch (JSONException e) {
            Log.e(this.toString(), e.getLocalizedMessage());
        } finally {
            connection.disconnect();
        }
        return jsonObj;
    }

    protected JSONObject postRequest(String url, String data, String oauthToken) {
        JSONObject jsonObj = new JSONObject();
        URL myURL;
        HttpURLConnection connection = null;
        try {
            myURL = new URL(url);
            connection = (HttpURLConnection) myURL.openConnection();
            String basicAuth = "Bearer " + oauthToken;
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");


            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer jsonStr = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                jsonStr.append(tmp).append("\n");
            reader.close();

            jsonObj = new JSONObject(jsonStr.toString());
        } catch (IOException e) {
            Log.e(this.toString(), e.getLocalizedMessage());
        } catch (JSONException e) {
            Log.e(this.toString(), e.getLocalizedMessage());
        } finally {
            connection.disconnect();
        }
        return jsonObj;
    }
}

