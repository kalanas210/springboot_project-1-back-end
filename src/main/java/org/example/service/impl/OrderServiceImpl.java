package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.Order;
import org.example.entity.ItemEntity;
import org.example.entity.OrderDetailsEntity;
import org.example.entity.OrderEntity;
import org.example.entity.UsersEntity;
import org.example.repository.ItemRepository;
import org.example.repository.OrderDetailsRepository;
import org.example.repository.OrderRepository;
import org.example.repository.UsersRepository;
import org.example.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final UsersRepository usersRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public Order saveOrder(Order order) {
        try{
            Optional<UsersEntity> usersEntity = usersRepository.findById(order.getCustomerId());
            if(usersEntity.isEmpty()){
                throw new IllegalArgumentException("User can't found : "+order.getCustomerId());
            }
            OrderEntity mapped = modelMapper.map(order, OrderEntity.class);
            OrderEntity orderEntity = orderRepository.save(mapped);
            orderEntity.getOrderDetailsList().forEach(orderDetailsEntity -> {
                orderDetailsEntity.setOrder(orderEntity);
            });
            for (OrderDetailsEntity orderDetailsEntity : orderEntity.getOrderDetailsList()) {
                orderDetailsRepository.save(orderDetailsEntity);
            }

            return modelMapper.map(orderEntity,Order.class);
        } catch (Exception e) {
            throw new RuntimeException("Could not save order : "+e.getMessage());
        }

    }
}
