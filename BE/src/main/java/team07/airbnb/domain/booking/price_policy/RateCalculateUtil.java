package team07.airbnb.domain.booking.price_policy;

public class RateCalculateUtil {

    private RateCalculateUtil() {

    }

    public static long calculate(double rate, long price) {
        double result = price * (rate / 100.0);

        return (long) result;
    }

}
