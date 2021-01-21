package humphreys;

import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //Setting up some constants to be used in the methods here
    private static final Scanner sc = new Scanner(System.in);
    private static final String urlCurrentWeather = "https://api.openweathermap.org/data/2.5/weather";
    private static final String urlForecast = "https://api.openweathermap.org/data/2.5/forecast";
    private static final String apiKey = "";  //Need to supply thr APIKEY
    private static final String units = "imperial";
    private static final String charset = StandardCharsets.UTF_8.name();

    /**
     * Returns the weather conditions in a WeatherConditions Object for
     *  the city provided
     * @param city
     * @return WeatherConditions
     */
    private static WeatherConditions getWeatherConditions(String city) {
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
     * @param city
     * @return WeatherForecast
     */
    private static WeatherForecast getCityForecast(String city) {
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

    /**
     * Prints the current weather conditions and the 5 day forecast for
     *  the city provided
     * @param city
     */
    private static void printCityWeather(String city) {

        WeatherConditions weather = getWeatherConditions(city);
        if (weather != null) {
            weather.displayWeather();
        }
        WeatherForecast weatherForecast = getCityForecast(city);
        if (weatherForecast != null) {
            weatherForecast.displayForecast();
        }

    }

    /**
     * Prints the maximum temperature and maximum wind speed for a given list of
     *  cities.  The results will print in order of the Max Temperature and then
     *  again in order of the Max Wind Speed
     * @param cities
     */
    private static void printCityComparison(ArrayList<String> cities) {
        ArrayList<WeatherForecast> citiesWeather = new ArrayList<>();
        for (String city: cities) {
            citiesWeather.add(getCityForecast(city));
        }

        ArrayList<WeatherForecastShort> citiesSummary = new ArrayList<>();

        for (WeatherForecast cityWeather: citiesWeather) {
            String city = cityWeather.getCity().getName();

            //Sort the List by the Max Wind Speed
            cityWeather.getList().sort((c1, c2) -> c2.getMeasurements().get("temp_max").
                    compareTo(c1.getMeasurements().get("temp_max")));
            Float maxTemperature = cityWeather.getList().get(0).getMeasurements().get("temp_max");

            //Sort the List by the Max Wind Speed
            cityWeather.getList().sort((c1, c2) -> c2.getWind().get("speed").compareTo(c1.getWind().get("speed")));
            Float maxWindSpeed = cityWeather.getList().get(0).getWind().get("speed");
            citiesSummary.add(new WeatherForecastShort(city, maxTemperature, maxWindSpeed));
        }
        citiesSummary.sort((c1, c2) -> c2.getMaxTemperature().compareTo(c1.getMaxTemperature()));
        System.out.println("Cities Sorted By Max Temperature:");
        System.out.println("City                Max Temperature         Max WindSpeed");
        for (WeatherForecastShort c: citiesSummary) {
            System.out.printf("%-25s%-25.2f%.2f%n", c.getCity(), c.getMaxTemperature(), c.getMaxWindSpeed());
        }
        System.out.println("===================================");
        citiesSummary.sort((c1, c2) -> c2.getMaxWindSpeed().compareTo(c1.getMaxWindSpeed()));
        System.out.println("Cities Sorted By Max Wind Speed:");
        System.out.println("City                Max Temperature         Max WindSpeed");
        for (WeatherForecastShort c: citiesSummary) {
            System.out.printf("%-25s%-25.2f%.2f%n", c.getCity(), c.getMaxTemperature(), c.getMaxWindSpeed());
        }
        System.out.println("===================================");
    }

    public static void main(String[] args) {

        String city;
        String choice;
        ArrayList<String> cities = new ArrayList<>();


        System.out.println("What would you like to do?");
        System.out.print("Enter 1 to check the Weather for one City or 2 to compare multiple cities: ");
        choice = sc.nextLine();
        boolean askCity = true;

        if (choice.equals("1")) {
            while (askCity) {
                System.out.print("Enter the name of the city to check the weather('quit' to quit): ");
                city = sc.nextLine();
                if (city.equals("quit")) {
                    askCity = false;
                } else {
                    printCityWeather(city);
                }
            }
        } else if (choice.equals("2")) {
            while (askCity) {
                System.out.print("Enter the name of one of the cities('quit' to quit): ");
                city = sc.nextLine();
                if (city.equals("quit")){
                    askCity = false;
                } else {
                    cities.add(city);
                }
            }
            printCityComparison(cities);
        }
    }
}

