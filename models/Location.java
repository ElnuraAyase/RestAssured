package models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class Location {
    private String street;
    private String city;
    private String postalCode;
}
