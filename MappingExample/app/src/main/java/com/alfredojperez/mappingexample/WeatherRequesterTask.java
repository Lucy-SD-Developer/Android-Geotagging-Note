package com.alfredojperez.mappingexample;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by csu on 7/19/2016.
 */

public class WeatherRequesterTask extends AsyncTask<Location, Void, String>
{
    Context context;
    public WeatherRequesterTask(Context con)
    {
        context = con;
    }
    @Override
    protected String doInBackground(Location... locations)
    {
        try
        {
            //return parseAndNotify(invokeWebService(locations[0]));
            return parseAndNotify(invokeWebServiceNoApache(locations[0]));

        } catch (URISyntaxException e)
        {
            Log.e("Error URI Syntax ", e.getMessage());
        } catch (IOException e)
        {
            Log.e("Error URI Syntax ", e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(String result)
    {
        if(result != null)
        {
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(result)
                    .setTitle("Weather Request");

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private String invokeWebService(Location loc) throws URISyntaxException, ClientProtocolException,IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();

        String lat = "" + loc.getLatitude();
        String lon = "" + loc.getLongitude();
        request.setURI(new URI("http://api.wunderground.com/api/b04bd95bc01bbd70/conditions/q/" + lat + "," + lon + ".json"));
        HttpResponse response = client.execute(request);


        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer sb = new StringBuffer("");
        String line ="";
        String NL = System.getProperty("line.separator");
        while((line = in.readLine())!= null){
            sb.append(line + NL);
        }
        in.close();
        return sb.toString();
    }

    private String invokeWebServiceNoApache(Location loc) throws URISyntaxException, IOException {

        String lat = "" + loc.getLatitude();
        String lon = "" + loc.getLongitude();

        URL url = new URL("http://api.wunderground.com/api/b04bd95bc01bbd70/conditions/q/" + lat + "," + lon + ".json");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuffer sb = new StringBuffer("");
        String line ="";
        String NL = System.getProperty("line.separator");
        while((line = in.readLine())!= null){
            sb.append(line + NL);
        }

        urlConnection.disconnect();
        return sb.toString();
    }

    private String parseAndNotify(String theResult){
        JSONTokener theTokener = new JSONTokener(theResult);
        JSONObject theWeatherResult;

        try{
            theWeatherResult = (JSONObject) theTokener.nextValue();
            if(theWeatherResult != null){
                JSONObject curWeather = theWeatherResult.getJSONObject("current_observation");
                JSONObject curCity = curWeather.getJSONObject("observation_location");

                String temperature = curWeather.getString("temperature_string");
                String weather = curWeather.getString("weather");
                String city = curCity.getString("full");
                return "The weather at " + city + " is " + weather + ", with " + temperature + " temperature";
            }
        }catch(JSONException e){
            Log.e("Error on AsynchTask ", e.getMessage());
        }
        return null;
    }
}
