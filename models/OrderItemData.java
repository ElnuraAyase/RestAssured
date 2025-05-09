package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemData {
    private String productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subTotal;
}
