package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemEntity {
    private String itemName;
    private String itemImage;
    private String itemDescription;
    private double itemPrice;
    private int itemQuantity;
    private String itemCategory;
    @Id
    private String itemCode;
    @ManyToOne
    @JoinColumn(name="supplierId",referencedColumnName = "supplierId")
    private SupplierEntity supplierEntity;
    private boolean isDisabled;

}
