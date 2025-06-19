package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "orderId",referencedColumnName = "orderId")
    @ToString.Exclude
    OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "itemCode",referencedColumnName = "itemCode")
    private ItemEntity itemCode;
    private Integer itemQty;
}
