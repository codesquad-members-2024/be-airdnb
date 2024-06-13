package team07.airbnb.service.booking.price_policy;

public class RateCalculateUtil {

    private RateCalculateUtil() {

    }

    public static int calculate(double rate, int price) {
        double result = price * (rate / 100.0);

        return (int) result;
    }

}
