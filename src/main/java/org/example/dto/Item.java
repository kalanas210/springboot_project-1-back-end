package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String itemName;
    private String itemImage;
    private String itemDescription;
    private double itemPrice;
    private int itemQuantity;
    private String itemCategory;
    private String itemCode;
    private String supplierId;
}
