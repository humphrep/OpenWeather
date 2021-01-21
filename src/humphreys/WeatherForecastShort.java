package humphreys;

public class WeatherForecastShort {
    private String city;
    private Float maxTemperature;
    private Float maxWindSpeed;

    public WeatherForecastShort (String city, Float maxTemperature, Float maxWindSpeed) {
        this.setCity(city);
        this.setMaxTemperature(maxTemperature);
        this.setMaxWindSpeed(maxWindSpeed);
    }
    public void setCity(String city) {
        this.city = city;
    }

    public void setMaxTemperature(Float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setMaxWindSpeed(Float maxWindSpeed) {
        this.maxWindSpeed = maxWindSpeed;
    }

    public Float getMaxTemperature() {
        return maxTemperature;
    }

    public Float getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public String getCity() {
        return city;
    }
}
