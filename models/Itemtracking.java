package models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Itemtracking {
    private String productId;
    private int quantity;
    private String bookstoreId;
}
