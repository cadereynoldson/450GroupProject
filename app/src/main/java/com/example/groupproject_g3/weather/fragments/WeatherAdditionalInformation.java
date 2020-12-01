package com.example.groupproject_g3.weather.fragments;

public class WeatherAdditionalInformation {

    private String feelsLike;
    private String tempMin;
    private String tempMax;
    private String pressure;
    private String humidity;

    public WeatherAdditionalInformation(String theFeelsLike, String theTempMin,
                                        String theTempMax, String thePressure,
                                        String theHumidity) {
        feelsLike = theFeelsLike;
        tempMin = theTempMin;
        tempMax = theTempMax;
        pressure = thePressure;
        humidity = theHumidity;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String toString() {
        return feelsLike + " " + tempMin + " " + tempMax + " " + pressure + " " + humidity;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WeatherAdditionalInformation))
            return false;
        else
            return ((WeatherAdditionalInformation) o).feelsLike.equals(feelsLike); //TODO: Update
    }
}
