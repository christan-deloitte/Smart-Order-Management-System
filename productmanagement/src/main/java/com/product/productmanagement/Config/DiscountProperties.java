package com.product.productmanagement.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "discount")
@Validated
public class DiscountProperties {

    private final Flat flat = new Flat();
    private final Percent percent = new Percent();

    public Flat getFlat() { return flat; }
    public Percent getPercent() { return percent; }

    public static class Flat {
        private boolean enabled = false;

        @NotNull
        @DecimalMin(value = "0.00")
        private BigDecimal amount = BigDecimal.ZERO;

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }

    public static class Percent {
        private boolean enabled = false;

        @NotNull
        @DecimalMin(value = "0.00")
        private BigDecimal rate = BigDecimal.ZERO; // e.g., 10 = 10%

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public BigDecimal getRate() { return rate; }
        public void setRate(BigDecimal rate) { this.rate = rate; }
    }
}
