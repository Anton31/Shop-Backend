package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ItemDto;
import com.example.authserverresourceserversameapp.dto.OrderDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.Order;
import com.example.authserverresourceserversameapp.model.User;

import java.util.List;

public interface OrderService {
    long addItem(ItemDto dto, User user);

    long editItem(ItemDto dto);

    Cart getCartByUser(User user);

    List<Order> getOrders(User user);

    List<Order> addOrder(OrderDto dto, User user);

    long deleteItem(long itemId);

    long deleteOrder(long itemId);
}
