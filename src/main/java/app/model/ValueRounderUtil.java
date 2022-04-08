package app.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ValueRounderUtil {
    public static double roundValue(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
