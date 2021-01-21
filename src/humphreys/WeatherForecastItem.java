package humphreys;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeatherForecastItem {
    @SerializedName("main")
    Map<String, Float> measurements = new HashMap<>();
    ArrayList<Weather> weather;
    Map<String, Float> wind = new HashMap<>();
    String dt_txt;


    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public void setMeasurements(Map<String, Float> measurements) {
        this.measurements = measurements;
    }

    public void setWind(Map<String, Float> wind) {
        this.wind = wind;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public Map<String, Float> getMeasurements() {
        return measurements;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public Map<String, Float> getWind() {
        return wind;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
