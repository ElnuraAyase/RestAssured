package models;

import lombok.Data;

@Data

public class Payment {
    private String creditEntryId;
    private String customerId;
    private double amount;
}
