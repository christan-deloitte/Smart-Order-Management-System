package com.product.productmanagement.Service;

import com.product.productmanagement.Config.DiscountProperties;
import com.product.productmanagement.Entity.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PricingService {

    private final DiscountProperties discountProps;

    public PricingService(DiscountProperties discountProps) {
        this.discountProps = discountProps;
    }

    /**
     * Applies percentage or flat discount based on toggles.
     * - Percentage takes precedence if both enabled.
     * - Floors at zero.
     */
    public double finalPrice(Product product) {
        if (product.getPrice() == null) return 0.0;

        BigDecimal base = BigDecimal.valueOf(product.getPrice());
        BigDecimal result = base;

        if (discountProps.getPercent().isEnabled()) {
            BigDecimal percent = discountProps.getPercent().getRate();
            BigDecimal discount = base.multiply(percent)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            result = base.subtract(discount);
        } else if (discountProps.getFlat().isEnabled()) {
            BigDecimal discount = discountProps.getFlat().getAmount();
            result = base.subtract(discount);
        }

        if (result.compareTo(BigDecimal.ZERO) < 0) {
            result = BigDecimal.ZERO;
        }
        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
