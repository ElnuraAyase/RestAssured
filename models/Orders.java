package models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    private String customerId; // 1
    private String bookstoreId; //2
    private BigDecimal price; //3
    private List<OrderItemData> items; //list of order items //5
    private Location address; //6

}
