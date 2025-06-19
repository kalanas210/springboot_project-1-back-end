package org.example.service;

import org.example.dto.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order saveOrder(Order order);
}
