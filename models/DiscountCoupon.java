package models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data


public class DiscountCoupon {
    private String code;
    private int discountPercent;
    private  LocalDate expiryDate;
    private int usageLimit;
    private double minCartValue;
    private int timesUsed = 0;

    public boolean isValid(double cartTotal) {
        if (LocalDate.now().isAfter(expiryDate)) {
            return false;
        }
        if (timesUsed >= usageLimit) {
            return false;
        }
        if (cartTotal < minCartValue) {
            return false;
        }
        return true;
    }

    public double applyDiscount(double cartTotal) {
        if (!isValid(cartTotal)) {
            throw new IllegalStateException(" coupon is not valid");
        }
        timesUsed++;
        double discount = cartTotal * (discountPercent / 100.0);
        return Math.round((cartTotal - discount) * 100.0) / 100.0;
    }
}
