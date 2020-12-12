package com.example.groupproject_g3.weather.fragments;

import android.graphics.Bitmap;

import java.io.Serializable;

public class WeatherInformation implements Serializable {

    /** String Date to be used. */
    private String mDate;

    /** String Temperature to be used. */
    private String mTemperature;

    /** String Celsius to be used. */
    private String mCelsius;

    /** String Time to be used. */
    private String mTime;

    /** String Weather description to be used. */
    private String mWeather;

    /** String Day to be used. */
    private String mDay;

    /** String Feels Like Temp to be used. */
    private String mFeelsLike;

    /** String Min Temp to be used. */
    private String mTempMin;

    /** String Max Temp to be used. */
    private String mTempMax;

    /** String Pressure to be used. */
    private String mPressure;

    /** String Humidity to be used. */
    private String mHumidity;

    /** String Probability of Precipitation to be used. */
    private String mPop;

    /** String Location to be used. */
    private String mLocation;

    /** String Country to be used. */
    private String mCountry;

    /** String Latitude to be used. */
    private String mLat;

    /** String Longitude to be used. */
    private String mLon;

    /** String Sunrise to be used. */
    private String mSunrise;

    /** String Sunset to be used. */
    private String mSunset;

    /** String Wind Speed to be used. */
    private String mSpeed;

    /** String Wind Direction to be used. */
    private String mDirection;

    /** String Weather Icon to be used. */
    private String mIcon;

    /** Long Time Zone to be used. */
    private long mTimeZone;


    public static class Builder {

        /** String Date to be used. */
        private String mDate;

        /** String DatTemperature to be used. */
        private String mTemperature;

        /** String Celsius to be used. */
        private String mCelsius = "";

        /** String Time to be used. */
        private String mTime = "";

        /** String Weather Description to be used. */
        private String mWeather = "";

        /** String Day to be used. */
        private String mDay = "";

        /** String Feels Like Temp to be used. */
        private String mFeelsLike = "";

        /** String Min Temp to be used. */
        private String mTempMin = "";

        /** String Max Temp to be used. */
        private String mTempMax = "";

        /** String Pressure to be used. */
        private String mPressure = "";

        /** String Humidity to be used. */
        private String mHumidity = "";

        /** String Chance of Rain to be used. */
        private String mPop = "";

        /** String Location to be used. */
        private String mLocation = "";

        /** String Country to be used. */
        private String mCountry = "";

        /** String Latitude to be used. */
        private String mLat = "";

        /** String Longitude to be used. */
        private String mLon = "";

        /** String Sunrise to be used. */
        private String mSunrise = "";

        /** String Sunset to be used. */
        private String mSunset = "";

        /** String Wind Speed to be used. */
        private String mSpeed = "";

        /** String Wind Direction to be used. */
        private String mDirection = "";

        /** String Weather Icon to be used. */
        private String mIcon;

        /** Long Time Zone to be used. */
        private long mTimeZone;


        /**
         * Creates a Builder that takes in the date and temperature
         * and add additional information when needed.
         * @param theDate The date of the weather API call.
         * @param theTemperature The temperature of the weather API call.
         */
        public Builder(String theDate, String theTemperature) {
            mDate = theDate;
            mTemperature = theTemperature;
        }

        /**
         * Adder method for Celsius.
         * @param theCelsius The temperature of the call in celsius.
         */
        public Builder addCelsius(String theCelsius) {
            mCelsius = theCelsius;
            return this;
        }

        /**
         * Adder method for Time.
         * @param theTime The time of the call.
         */
        public Builder addTime(String theTime) {
            mTime = theTime;
            return this;
        }

        /**
         * Adder method for Description.
         * @param theWeather The weather description of the call.
         */
        public Builder addWeather(String theWeather) {
            mWeather = theWeather;
            return this;
        }

        /**
         * Adder method for Day.
         * @param theDay The day of the call.
         */
        public Builder addDay(String theDay) {
            mDay = theDay;
            return this;
        }

        /**
         * Adder method for Feels Like Temp.
         * @param theFeelsLike The Feels Like Temp of the call.
         */
        public Builder addFeelsLike(String theFeelsLike) {
            mFeelsLike = theFeelsLike;
            return this;
        }

        /**
         * Adder method for Minimum Temp.
         * @param theTempMin The Minimum Temp of the call.
         */
        public Builder addTempMin(String theTempMin) {
            mTempMin = theTempMin;
            return this;
        }

        /**
         * Adder method for Maximum Temp.
         * @param theTempMax The Maximum Temp of the call.
         */
        public Builder addTempMax(String theTempMax) {
            mTempMax = theTempMax;
            return this;
        }

        /**
         * Adder method for Pressure.
         * @param thePressure The Pressure of the call.
         */
        public Builder addPressure(String thePressure) {
            mPressure = thePressure;
            return this;
        }

        /**
         * Adder method for Humidity.
         * @param theHumidity The Humidity of the call.
         */
        public Builder addHumidity(String theHumidity) {
            mHumidity = theHumidity;
            return this;
        }

        /**
         * Adder method for Probability of Precipitation.
         * @param thePop The Chance of Rain of the call.
         */
        public Builder addPrecipitation(String thePop) {
            mPop = thePop;
            return this;
        }

        /**
         * Adder method for Location.
         * @param theLocation The Location of the call.
         */
        public Builder addLocation(String theLocation) {
            mLocation = theLocation;
            return this;
        }

        /**
         * Adder method for Country.
         * @param theCountry The Country of the call.
         */
        public Builder addCountry(String theCountry) {
            mCountry = theCountry;
            return this;
        }

        /**
         * Adder method for Latitude.
         * @param theLat The Latitude of the call.
         */
        public Builder addLat(String theLat) {
            mLat = theLat;
            return this;
        }

        /**
         * Adder method for Longitude.
         * @param theLon The Longitude of the call.
         */
        public Builder addLon(String theLon) {
            mLon = theLon;
            return this;
        }

        /**
         * Adder method for Sunrise.
         * @param theSunrise The Sunrise of the call.
         */
        public Builder addSunrise(String theSunrise) {
            mSunrise = theSunrise;
            return this;
        }

        /**
         * Adder method for Sunset.
         * @param theSunset The Sunset of the call.
         */
        public Builder addSunset(String theSunset) {
            mSunset = theSunset;
            return this;
        }

        /**
         * Adder method for Wind Speed.
         * @param theSpeed The Wind Speed of the call.
         */
        public Builder addSpeed(String theSpeed) {
            mSpeed = theSpeed;
            return this;
        }

        /**
         * Adder method for Wind Direction.
         * @param theDirection The Wind Direction of the call.
         */
        public Builder addDirection(String theDirection) {
            mDirection = theDirection;
            return this;
        }

        /**
         * Adder method for weather Icon.
         * @param theIcon The weather Icon of the call.
         */
        public Builder addIcon(String theIcon) {
            mIcon = theIcon;
            return this;
        }

        /**
         * Adder method for timezone.
         * @param timezone The timezone of the call.
         */
        public Builder addTimeZone(long timezone) {
            mTimeZone = timezone;
            return this;
        }

        /**
         * The Weather Information Builder method.
         */
        public WeatherInformation build() { return new WeatherInformation(this);}
    }

    /**
     * Constructor of WeatherInformation.
     * @param builder Accepts a builder that gets the necessary information.
     */
    private WeatherInformation(Builder builder) {
        mDate = builder.mDate;
        mTemperature = builder.mTemperature;
        mCelsius = builder.mCelsius;
        mTime = builder.mTime;
        mWeather = builder.mWeather;
        mDay = builder.mDay;
        mFeelsLike = builder.mFeelsLike;
        mTempMin = builder.mTempMin;
        mTempMax = builder.mTempMax;
        mPressure = builder.mPressure;
        mHumidity = builder.mHumidity;
        mPop = builder.mPop;
        mLocation = builder.mLocation;
        mCountry = builder.mCountry;
        mLat = builder.mLat;
        mLon = builder.mLon;
        mSunrise = builder.mSunrise;
        mSunset = builder.mSunset;
        mSpeed = builder.mSpeed;
        mDirection = builder.mDirection;
        mIcon = builder.mIcon;
        mTimeZone = builder.mTimeZone;
    }

    /**
     * Getter method for Date.
     */
    public String getmDate() {
        return mDate;
    }

    /**
     * Getter method for Temperature.
     */
    public String getmTemperature() {
        return mTemperature;
    }

    /**
     * Getter method for Celsius.
     */
    public String getmCelsius() {
        return mCelsius;
    }

    /**
     * Getter method for Time.
     */
    public String getmTime() {
        return mTime;
    }

    /**
     * Getter method for Weather.
     */
    public String getmWeather() {
        return mWeather;
    }

    /**
     * Getter method for Day.
     */
    public String getmDay() {
        return mDay;
    }

    /**
     * Getter method for FeelsLike.
     */
    public String getmFeelsLike() {
        return mFeelsLike;
    }

    /**
     * Getter method for TempMin.
     */
    public String getmTempMin() {
        return mTempMin;
    }

    /**
     * Getter method for TempMax.
     */
    public String getmTempMax() {
        return mTempMax;
    }

    /**
     * Getter method for Pressure.
     */
    public String getmPressure() {
        return mPressure;
    }

    /**
     * Getter method for Humidity.
     */
    public String getmHumidity() {
        return mHumidity;
    }

    /**
     * Getter method for Pop.
     */
    public String getmPop() {
        return mPop;
    }

    /**
     * Getter method for Location.
     */
    public String getmLocation() {
        return mLocation;
    }

    /**
     * Getter method for Country.
     */
    public String getmCountry() {
        return mCountry;
    }

    /**
     * Getter method for Lat.
     */
    public String getmLat() {
        return mLat;
    }

    /**
     * Getter method for Lon.
     */
    public String getmLon() {
        return mLon;
    }

    /**
     * Getter method for Sunrise.
     */
    public String getmSunrise() { return mSunrise; }

    /**
     * Getter method for Sunset.
     */
    public String getmSunset() { return mSunset; }

    /**
     * Getter method for Speed.
     */
    public String getmSpeed() { return mSpeed; }

    /**
     * Getter method for Direction.
     */
    public String getmDirection() { return mDirection; }

    /**
     * Getter method for Icon.
     */
    public String getmIcon() { return mIcon; }

    /**
     * Getter method for timezone.
     */
    public long getmTimeZone() { return mTimeZone; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WeatherInformation))
            return false;
        else
            return (((WeatherInformation) o).mDate.equals(mDate) && ((WeatherInformation) o).mTime.equals(mTime));
    }
}
