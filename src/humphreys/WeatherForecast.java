package humphreys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class WeatherForecast {
    ArrayList<WeatherForecastItem> list;
    City city;

    public void setList(ArrayList<WeatherForecastItem> list) {
        this.list = list;
    }

    public ArrayList<WeatherForecastItem> getList() {
        return list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    /**
     * Displays the Forecast data for this object
     */
    public void displayForecast() {
        System.out.printf("Forecast for %s:%n", this.getCity().getName());
        System.out.println("Date/Time            Temperature     Description         Wind Speed");

        this.list.forEach((w) -> System.out.printf("%-25s%-12.2f%-22s%.2f%n",w.getDt_txt(),
                w.getMeasurements().get("temp"), w.getWeather().get(0).getDescription(), w.getWind().get("speed")));
        System.out.println("===================================");
    }

}
