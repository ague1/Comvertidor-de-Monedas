package src;

import java.util.Map;

public record dataMoneda (String base_code , Map<String,Double> conversion_rates) {
}
