package models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data


public class Products {

    private String name;
    private BigDecimal price;  //replaced double with Bigdecimal
    private boolean available;

}
