package com.airdnb.clone.dummy.stay;

import com.airdnb.clone.dummy.RandomNumberGenerator;

public class DummyLatitudeGenerator {
    private static final double MIN_LATITUDE = 35.187030;
    private static final double MAX_LATITUDE = 37.73094;

    private static final double MIN_BUSAN_LATITUDE = 35.17261;
    private static final double MAX_BUSAN_LATITUDE = 35.18420;

    private static final double MIN_JEJU_LATITUDE = 33.262180;
    private static final double MAX_JEJU_LATITUDE = 33.552250;

    private static final double MIN_GANGWON_LONGITUDE = 37.363380;
    private static final double MAX_GANGWON_LONGITUDE = 38.160411;

    public static double generate() {
        double randomLatitude = RandomNumberGenerator.generateDouble(MIN_LATITUDE, MAX_LATITUDE);
        String format = String.format("%.6f", randomLatitude);
        return Double.parseDouble(format);
    }

    public static double generateBusan() {
        double randomLatitude = RandomNumberGenerator.generateDouble(MIN_BUSAN_LATITUDE, MAX_BUSAN_LATITUDE);
        String format = String.format("%.6f", randomLatitude);
        return Double.parseDouble(format);
    }

    public static double generateJeju() {
        double randomLatitude = RandomNumberGenerator.generateDouble(MIN_JEJU_LATITUDE, MAX_JEJU_LATITUDE);
        String format = String.format("%.6f", randomLatitude);
        return Double.parseDouble(format);
    }
    public static double generateGangwon() {
        double randomLatitude = RandomNumberGenerator.generateDouble(MIN_GANGWON_LONGITUDE, MAX_GANGWON_LONGITUDE);
        String format = String.format("%.6f", randomLatitude);
        return Double.parseDouble(format);
    }

}
