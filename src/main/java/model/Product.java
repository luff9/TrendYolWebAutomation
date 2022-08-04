package model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class Product {
    String brandName;
    String description;
    String price;
    String dataID;
    Integer count;

}
