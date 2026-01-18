package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ItemDto;
import com.example.authserverresourceserversameapp.dto.OrderDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.Order;
import com.example.authserverresourceserversameapp.model.User;

public interface OrderService {
    Cart addItem(ItemDto dto, User user);

    Cart editItem(ItemDto dto);

    Cart getCartByUser(User user);

    Order getOrders(User user);

    Order addOrder(OrderDto dto, User user);

    long deleteItem(long itemId);

//    long deleteOrder(long orderId);
}
