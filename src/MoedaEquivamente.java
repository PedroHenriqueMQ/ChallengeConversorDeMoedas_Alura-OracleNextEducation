import com.google.gson.annotations.SerializedName;

import java.util.Map;

public record MoedaEquivamente (
        @SerializedName("conversion_rates")
        Map<String, Double> conversionRates
    ) {
}
