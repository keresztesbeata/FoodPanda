package app.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ValueRounderUtil {
    /**
     * Round the double value to exactly 2 decimal precision.
     *
     * @param value the real value to be rounded
     * @return the rounded value
     */
    public static double roundValue(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
