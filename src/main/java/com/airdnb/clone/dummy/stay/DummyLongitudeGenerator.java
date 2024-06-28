package com.airdnb.clone.dummy.stay;

import com.airdnb.clone.dummy.RandomNumberGenerator;

public class DummyLongitudeGenerator {
    private static final double MIN_LONGITUDE = 126.903500;
    private static final double MAX_LONGITUDE = 128.826900;

    private static final double MIN_BUSAN_LONGITUDE = 128.93300;
    private static final double MAX_BUSAN_LONGITUDE = 129.1119;

    private static final double MIN_JEJU_LONGITUDE = 126.294700;
    private static final double MAX_JEJU_LONGITUDE = 126.795800;

    private static final double MIN_GANGWON_LONGITUDE = 127.819100;
    private static final double MAX_GANGWON_LONGITUDE = 128.477900;

    public static double generate() {
        double randomLongitude = RandomNumberGenerator.generateDouble(MIN_LONGITUDE, MAX_LONGITUDE);
        String format = String.format("%.6f", randomLongitude);
        return Double.parseDouble(format);
    }

    public static double generateBusan() {
        double randomLongitude = RandomNumberGenerator.generateDouble(MIN_BUSAN_LONGITUDE, MAX_BUSAN_LONGITUDE);
        String format = String.format("%.6f", randomLongitude);
        return Double.parseDouble(format);
    }

    public static double generateJeju() {
        double randomLongitude = RandomNumberGenerator.generateDouble(MIN_JEJU_LONGITUDE, MAX_JEJU_LONGITUDE);
        String format = String.format("%.6f", randomLongitude);
        return Double.parseDouble(format);
    }

    public static double generateGangwon() {
        double randomLongitude = RandomNumberGenerator.generateDouble(MIN_GANGWON_LONGITUDE, MAX_GANGWON_LONGITUDE);
        String format = String.format("%.6f", randomLongitude);
        return Double.parseDouble(format);
    }

    // 제주, 부산같은 특정 위.경도를 만들 때 사용
    public static double generate(double min, double max) {
        double randomLongitude = RandomNumberGenerator.generateDouble(min, max);
        String format = String.format("%.6f", randomLongitude);
        return Double.parseDouble(format);
    }
}
