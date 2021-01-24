package humphreys;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherAPICalls {

    private static final String urlCurrentWeather = "https://api.openweathermap.org/data/2.5/weather";
    private static final String urlForecast = "https://api.openweathermap.org/data/2.5/forecast";
    private static final String apiKey = "";  //Need to supply the APIKEY
    private static final String units = "imperial";
    private static final String charset = StandardCharsets.UTF_8.name();

    /**
     * Returns the weather conditions in a WeatherConditions Object for
     *  the city provided
     * @param city City to get the weather conditions of
     * @return WeatherConditions
     */
    public static WeatherConditions getWeatherConditions(String city) {
        Gson gWeather = new Gson();
        String query;

        try {
            query = String.format("q=%s&units=%s&apiKey=%s",
                    URLEncoder.encode(city, charset),
                    URLEncoder.encode(units, charset),
                    URLEncoder.encode(apiKey, charset));

            //Connect to the API URL and collect the response
            URLConnection connectionWeather = new URL(urlCurrentWeather + "?" + query).openConnection();
            connectionWeather.setRequestProperty("Accept-Charset", charset);
            InputStream response = connectionWeather.getInputStream();

            BufferedReader readerWeather = new BufferedReader(new InputStreamReader(response));

            StringBuilder stringBuilderWeather = new StringBuilder();

            //Create the String with the JSON Response
            String lineWeather;
            while ((lineWeather = readerWeather.readLine()) != null) {
                stringBuilderWeather.append(lineWeather);
            }

            //Deserialize the JSON response into the appropriate object structure
            return gWeather.fromJson(stringBuilderWeather.toString(), WeatherConditions.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the 5 day weather forecast in a WeatherForecast Object for
     *  the city provided
     * @param city City to get the forecast for
     * @return WeatherForecast
     */
    public static WeatherForecast getCityForecast(String city) {
        Gson gsonForecast = new Gson();
        String query;

        try {
            query = String.format("q=%s&units=%s&apiKey=%s",
                    URLEncoder.encode(city, charset),
                    URLEncoder.encode(units, charset),
                    URLEncoder.encode(apiKey, charset));

            //Connect to the API URL and collect the response
            URLConnection connectionForecast = new URL(urlForecast + "?" + query).openConnection();
            connectionForecast.setRequestProperty("Accept-Charset", charset);
            InputStream responseForecast = connectionForecast.getInputStream();

            BufferedReader readerForecast = new BufferedReader(new InputStreamReader(responseForecast));

            StringBuilder stringBuilderForecast = new StringBuilder();

            //Create the String with the JSON Response
            String lineForecast;
            while ((lineForecast = readerForecast.readLine()) != null) {
                stringBuilderForecast.append(lineForecast);
            }

            //Deserialize the JSON response into the appropriate object structure
            return gsonForecast.fromJson(stringBuilderForecast.toString(), WeatherForecast.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
