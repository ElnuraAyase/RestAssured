package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // This will exclude null fields

public class Bookstore {
    private String name;

    @JsonProperty("isActive") // to read isActive
    private Boolean isActive;  // boolean - primitive one ,that never null, Boolean can be null for new test

}
